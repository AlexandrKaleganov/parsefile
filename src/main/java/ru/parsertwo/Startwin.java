package ru.parsertwo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class Startwin extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button buttonWay = new Button("Обзор");
        Button startPars = new Button("Читать");
        Text text = new Text(5, 20, "выберете файл     *.csv");
        Text way = new Text(50, 20, "___________________");
        text.setFont(new Font(20));
        BorderPane borderPane = new BorderPane();
        BorderPane borderPaneTop = new BorderPane();
        BorderPane borderPaneTopCentre = new BorderPane();
        TableView tableView = new TableView();
        borderPaneTopCentre.setCenter(text);
        borderPaneTop.setTop(borderPaneTopCentre);
        borderPaneTop.setCenter(way);
        borderPaneTop.setLeft(buttonWay);
        borderPaneTop.setRight(startPars);
        borderPane.setTop(borderPaneTop);
        borderPane.setCenter(tableView);
        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setTitle("Parser");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
