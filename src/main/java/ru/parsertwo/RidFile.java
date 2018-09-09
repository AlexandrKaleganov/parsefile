package ru.parsertwo;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class RidFile {
    private final BlockingDeque<List<String>> data = new LinkedBlockingDeque<>();
    private final TableView tableView;


    public RidFile(TableView tableView) {
        this.tableView = tableView;
    }

    public void send(String way) {

        Thread out = new Thread(() -> {
            boolean stroki = false;
            while (!Thread.currentThread().isInterrupted()) {

                try {
                    List<String> temp = data.take();

                    if (!stroki) {
                        for (int i = 0; i < temp.size(); i++) {
                            int finalI = i;
                            TableColumn<ObservableList<String>, String> col = new TableColumn<>(temp.get(finalI));
                            col.setCellValueFactory(
                                    param -> new SimpleStringProperty(param.getValue().get(finalI)));
                            tableView.getColumns().add(col);
                        }

                        stroki = true;
                    } else {
                        ObservableList<String> a = FXCollections.observableArrayList();
                        a.addAll(temp);
                        tableView.getItems().add(a);
                    }

                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread read = new Thread(() -> {
            try {
                BufferedReader bufer = new BufferedReader(new InputStreamReader(new FileInputStream(way), StandardCharsets.UTF_16));
                String line;
                while (null != (line = bufer.readLine())) {
                    data.offer(Arrays.asList(line.split("\\s*,\\s*")));
                }
            } catch (IOException e) {
                System.out.println(e);
                out.interrupt();
            }
            out.interrupt();
        });


        out.start();
        read.start();
    }
}
