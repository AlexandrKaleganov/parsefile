package ru.parsertwo;
/**
 * рограмма парсер csv  файлов
 * читает файл и выводит в таблицу на javaFX TableView
 * сама логика программы в классе содержится один метод, который состоит из двух потоков
 * первый поток будет пытаться получить из блокирующей очереди строку
 * второй поток будет читать файл по одной строке, и добавлять прочитанную строчку в блокирующую очередь
 **/