package ru.parsertwo;

import org.junit.Test;

import java.nio.file.Path;

import static java.nio.file.Paths.get;
import static org.junit.Assert.*;

/**
 * полное тестирование чтение и вывод файла, настоящий программист стирает 200 строк каждый день
 */
public class RidandOutFileTest {


    @Test
    public void send() {
        RidandOutFile ridandOutFile = new RidandOutFile();
        Path path = get("file//google.csv");
        ridandOutFile.send(path.toString());
    }
}