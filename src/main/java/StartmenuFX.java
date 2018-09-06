import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StartmenuFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane panel = FXMLLoader.load(getClass().getResource("startmenuFX.fxml"));
        Scene scene = new Scene(panel, 400, 400);
        primaryStage.setTitle("test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
