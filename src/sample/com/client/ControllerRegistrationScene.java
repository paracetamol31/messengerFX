package sample.com.client;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ControllerRegistrationScene extends Client {
    public TextField tfLogin;
    public TextField tfPassword;
    public Button send;
    public Button prev;
    public Label error;

    public void sendToServer() {
        try {
                PrintWriter printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
                Scanner scanner = new Scanner(serverSocket.getInputStream());
                printWriter.println("next");
                String str = tfLogin.getText() + '\n' + tfPassword.getText();
                printWriter.println(str);
                String msg = scanner.nextLine();
                if (msg.equals("true")){
                    name = tfLogin.getText();
                    password = tfPassword.getText();
                    send.getScene().getWindow().hide();
                    crateNewWindow(PATH_TO_SCENE_MESSENGER);
                }
                else{
                    error.setVisible(true);
                    tfLogin.setText("");
                    tfPassword.setText("");
                }
        } catch (IOException e) {
            e.printStackTrace();
            }

    }

    public void back() throws IOException {
        PrintWriter printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
        printWriter.println("prev");
        prev.getScene().getWindow().hide();
        crateNewWindow(PATH_TO_SCENE_ONE);
    }
}
