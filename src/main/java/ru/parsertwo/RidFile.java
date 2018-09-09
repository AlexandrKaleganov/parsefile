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
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class RidFile {
    private final BlockingDeque<ArrayList<String>> data = new LinkedBlockingDeque<>();
    private final TableView<ObservableList<String>> tableView;

    public RidFile(TableView<ObservableList<String>> tableView) {
        this.tableView = tableView;
    }

    public void send(String way) {
        //поток, который выводит данные в таблицу
        Thread out = new Thread(() -> {
            boolean stroki = false;     //пока первый массив это заголовок
            while (!Thread.currentThread().isInterrupted() || !this.data.isEmpty()) {

                try {
                    ArrayList<String> temp = data.take();
                    if (!stroki) {
                        for (int i = 0; i < temp.size(); i++) {
                            int finalI = i;
                            TableColumn<ObservableList<String>, String> col = new TableColumn<>(temp.get(finalI));
                            col.setCellValueFactory(
                                    param -> new SimpleStringProperty(param.getValue().get(finalI)));
                            Platform.runLater(() -> tableView.getColumns().add(col));
                        }

                    } else {
                        ObservableList<String> a = FXCollections.observableArrayList();
                        a.addAll(temp);
                        tableView.getItems().add(a);
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
                stroki = true;   //после того как добавилизаголовок, заполняем строки
            }
            System.out.println("поток завершён");
        });
        //поток который читает файл
        Thread read = new Thread(() -> {
            try {
                BufferedReader bufer = new BufferedReader(new InputStreamReader(new FileInputStream(way)));
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
