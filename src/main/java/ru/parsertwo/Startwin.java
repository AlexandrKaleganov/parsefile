package ru.parsertwo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class Startwin extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button("Обзор");
        Text text = new Text(10, 20, "выберете файл *.csv");
        text.setFont(new Font(40));
        BorderPane borderPane = new BorderPane();
        BorderPane borderPane1 = new BorderPane();
        borderPane.setTop(borderPane1);
        borderPane1.setTop(text);
        borderPane1.setCenter(button);
        Scene scene = new Scene(borderPane, 400, 400);
        primaryStage.setTitle("Parser");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
