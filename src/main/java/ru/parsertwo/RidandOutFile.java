package ru.parsertwo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class RidandOutFile {
    private final BlockingDeque<String[]> data = new LinkedBlockingDeque<>();
    private BufferedReader bufer;

    public void send(String way) {
        System.out.println("вася");
        Thread out = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    for (String cell : data.take()
                            ) {
                        System.out.print(" [" + cell + "] ");

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("  new Stroka");
            }
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
