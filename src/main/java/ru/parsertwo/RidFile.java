package ru.parsertwo;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class RidFile {
    private final BlockingDeque<String[]> data = new LinkedBlockingDeque<>();
    @SuppressWarnings("SpellCheckingInspection")
    private BufferedReader bufer;
    private final TableView tableView;

    public RidFile(TableView<String[]> tableView) {
        this.tableView = tableView;
    }

    public void send(String way) {
        Thread out = new Thread(() -> {
            boolean stroki = false;
            while (!Thread.currentThread().isInterrupted()) {

                try {
                    for (String cell : data.take()
                            ) {
                        if (!stroki) {
                            tableView.getColumns().add(new TableColumn(cell));
                        } else {
                            System.out.print("[" + cell + "]");                        }
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("  new Stroka");
            }
            System.out.println("завершился");
        });

        Thread read = new Thread(() -> {
            try {
                bufer = new BufferedReader(new InputStreamReader(new FileInputStream(way), StandardCharsets.UTF_16));

                String line;

                while (null != (line = bufer.readLine())) {
                    data.offer(line.split("\\s*,\\s*"));
                }
            } catch (IOException e) {
                out.interrupt();
            }
            out.interrupt();
        });
        out.start();
        read.start();
        try {
            out.join();
            read.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
