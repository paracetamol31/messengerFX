package sample.com.client;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;

public class ControllerSceneOne extends Client {
    public Button registrationButton;
    public Button authenticationButton;
    public TextField ip;
    public TextField port;
    public Label error;

    public void registrationScene() {
          if(connectToServer()) {
              try {
                  PrintWriter printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
                  printWriter.println("registration");
              } catch (IOException e) {
                  e.printStackTrace();
              }
              registrationButton.getScene().getWindow().hide();
              crateNewWindow("scenes/registrationScene.fxml");
          }
    }

    public void authenticationScene() {
           if(connectToServer()) {
               try {
                   PrintWriter printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
                   printWriter.println("authentication");
               } catch (IOException e) {
                   e.printStackTrace();
               }
               Stage stage = (Stage) (authenticationButton.getScene().getWindow());
               stage.close();
               crateNewWindow("scenes/authenticationScene.fxml");
           }
    }

    private boolean connectToServer(){
        if (!connect(ip.getText(), port.getText())){
            ip.clear();
            port.clear();
            error.setVisible(true);
            return false;
        }
        return true;
    }
}
