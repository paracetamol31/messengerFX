package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.com.client.Client;

public class Main2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Client client = new Client();
        Parent root = FXMLLoader.load(getClass().getResource("sceneOne.fxml"));
        primaryStage.setTitle("messenger");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
