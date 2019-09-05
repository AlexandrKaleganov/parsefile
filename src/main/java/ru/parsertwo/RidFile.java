package ru.parsertwo;


import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Alexander Kaleganov
 * @since 09.09.2018
 * @version parser_1/0
 * этот класс сама логика программы в классе содержится один метод, который состоит из двух потоков
 * первый поток будет пытаться получить из блокирующей очереди строку
 * второй поток будет читать файл по одной строке, и добавлять прочитанную строчку в блокирующую очередь
 *
 */

public class RidFile {
    private final BlockingDeque<ArrayList<String>> data = new LinkedBlockingDeque<>();
    private final TableView<ObservableList<String>> tableView;


    public RidFile(TableView<ObservableList<String>> tableView) {
        this.tableView = tableView;
    }

    public void send(String way) {
        //поток, который выводит данные в таблицу по одной строке
        Thread out = new Thread(() -> {
            boolean stroki = false;     //пока первый массив это заголовок
            int dataLenght = 0;       //тут сохраним длину первого массива ЗАГОЛОВКА
            while (!Thread.currentThread().isInterrupted() || !this.data.isEmpty()) {

                try {
                    ArrayList<String> temp = this.data.take();
                    if (!stroki) {
                        for (int i = 0; i < temp.size(); i++) {
                            int finalI = i;
                            dataLenght = temp.size();
                            TableColumn<ObservableList<String>, String> col = new TableColumn<>(temp.get(finalI));
                            col.setCellValueFactory(
                                    param -> new SimpleStringProperty(param.getValue().get(finalI)));
                            Platform.runLater(() -> tableView.getColumns().add(col));
                        }

                    } else {
                        //тут проверили если наша входящая строка меньше загловка то заполним недостающее пустыми данными
                        for (int i = temp.size(); i < dataLenght; i++) {
                            temp.add("");
                        }
                        ObservableList<String> a = FXCollections.observableArrayList();
                        a.addAll(temp);
                        tableView.getItems().add(a);
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
                stroki = true;   //после того как добавилизаголовок, заполняем строки
            }
        });

        //поток который читает файл по одной строке
        Thread read = new Thread(() -> {
            try {
                BufferedReader bufer = new BufferedReader(new InputStreamReader(new FileInputStream(way), StandardCharsets.UTF_8));
                String line;
                while (null != (line = bufer.readLine())) {
                    data.offer(new ArrayList<>(Arrays.asList(line.split("\\s*,\\s*"))));
                }
            } catch (IOException e) {
                out.interrupt();
            }
            out.interrupt();
        });

        out.start();
        read.start();
    }
}
