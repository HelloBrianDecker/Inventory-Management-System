package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.StartAppController;

public class StartApp extends Application {
    Stage stage;

    public void initStartApp() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApp.class.getResource("/view/StartApp.fxml"));
            AnchorPane startAppView = loader.load();
            Scene scene = new Scene(startAppView);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startApp() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApp.class.getResource("/view/StartApp.fxml"));
            AnchorPane startAppView = loader.load();
            StartAppController controller = loader.getController();
            controller.setStartApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        initStartApp();
        startApp();
    }

    public static void main(String[] args) {
        launch(args);
    }
}