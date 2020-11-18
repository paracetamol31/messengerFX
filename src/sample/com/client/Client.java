package sample.com.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Client {
    protected static Socket serverSocket;
    protected static String name;
    protected static String password;
    protected final String PATH_TO_SCENE_ONE = "scenes/sceneOne.fxml";
    protected final String PATH_TO_SCENE_MESSENGER = "scenes/sceneMessenger.fxml";
    protected final String PATH_TO_REGISTRATION_SCENE = "scenes/registrationScene.fxml";
    protected final String PATH_TO_IDENTIFICATION_SCENE = "scenes/identificationScene.fxml";

    public boolean connect(String ip, String port){
        try {
            serverSocket = new Socket(ip, Integer.parseInt(port));
        } catch (Exception e) {
            return false;
        }
        return serverSocket.isConnected();
    }

    protected void crateNewWindow(String pathToFXML){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(pathToFXML));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 1000, 800));
        stage.show();
    }
}