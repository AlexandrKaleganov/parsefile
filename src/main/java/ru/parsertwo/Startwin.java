package ru.parsertwo;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * @author Alexander Kaleganov
 * @since 09.09.2018
 * @version parser_1/0
 * этот класс отвечает за виуальную часть программы
 * я не силён в javaFX по этому написал топорным способом по простому
 *
 */

public class Startwin extends Application {
    //нашь класс который будет читать файл
    private RidFile ridFile;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //скрол панель была закоментированная она мне не пригодилась
        /*
        ScrollPane scrollPane = new ScrollPane();
        // Always show vertical scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Horizontal scroll bar is only displayed when needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        */
        //элемент отвечает за поиск файла
        JFileChooser fileChooser = new JFileChooser();
        //все сотальные элементы
        Pane pane = new Pane();
        Button buttonWay = new Button("Обзор");
        Button buttonPars = new Button("Читать");
        Text text = new Text(5, 20, "выберете файл     *.csv");
        Text way = new Text(50, 20, "___________________");
        text.setFont(new Font(20));
        BorderPane borderPane = new BorderPane();
        BorderPane borderPaneTop = new BorderPane();
        BorderPane borderPaneTopCentre = new BorderPane();
        TableView<ObservableList<String>> tableView = new TableView();

        /**
         * размещение элементов не знал как по другому их ещё разместить
         * опыта в javaFX маловато
         * добавим таблицу в скрол панель
         */

        //scrollPane.setContent(tableView);
        borderPaneTopCentre.setCenter(text);
        borderPaneTop.setTop(borderPaneTopCentre);
        borderPaneTop.setCenter(way);
        borderPaneTop.setLeft(buttonWay);
        borderPaneTop.setRight(buttonPars);
        borderPane.setTop(borderPaneTop);
        borderPane.setCenter(tableView);

        //название формы
        primaryStage.setTitle("Parser");
        primaryStage.setScene(new Scene(borderPane, 600, 400));
        //действие при нажатие на кнопку
        buttonWay.setOnMouseClicked(event -> {
            int ret = fileChooser.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                //запишем нашь путь в лейбл
                way.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        buttonPars.setOnMouseClicked(event -> {
            String exe = null;
            //ищем расширение в название файла , всякое бывает решил искать сплитом последнняя фраза после точки и будет расширением
            for (String temp : way.getText().split("\\.")) {
                exe = temp;
            }
            if ("csv".contains(exe)) {
                ridFile = new RidFile(tableView);
                ridFile.send(way.getText());
            }
        });
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
