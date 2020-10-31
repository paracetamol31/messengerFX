package sample;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.com.client.Client;

import java.io.IOException;
import java.io.PrintWriter;

public class ControllerSceneOne extends Client {
    public Button registrationButton;
    public Button authenticationButton;

    public void registrationScene(ActionEvent actionEvent) {
        connect();
        try {
            PrintWriter printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
            printWriter.println("registration");
        } catch (IOException e) {
            e.printStackTrace();
        }
        registrationButton.getScene().getWindow().hide();
        crateNewWindow("registrationScene.fxml");
    }

    public void authenticationScene(ActionEvent actionEvent) {
        connect();
        try {
            PrintWriter printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
            printWriter.println("authentication");
        } catch (IOException e) {
            e.printStackTrace();
        }
        authenticationButton.getScene().getWindow().hide();
        crateNewWindow("authenticationScene.fxml");
    }

}
