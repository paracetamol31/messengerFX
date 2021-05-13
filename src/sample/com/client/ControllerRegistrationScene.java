package sample.com.client;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerRegistrationScene extends Client {
    public TextField tfLogin;
    public TextField tfPassword;
    public Button send;
    public Button prev;
    public Label error;

    public void sendToServer() {
        try {
                String str = tfLogin.getText() + '\n' + tfPassword.getText();
                if(!checkValid(str)){
                   resetSettings();
                   return;
                }
                    PrintWriter printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
                    Scanner scanner = new Scanner(serverSocket.getInputStream());
                    printWriter.println("next");
                    printWriter.println(str);
                    String msg = scanner.nextLine();
                    if (msg.equals("true")) {
                        name = tfLogin.getText();
                        password = tfPassword.getText();
                        send.getScene().getWindow().hide();
                        crateNewWindow(PATH_TO_SCENE_MESSENGER);
                    } else {
                        resetSettings();
                    }
        } catch (IOException e) {
            e.printStackTrace();
            }

    }

    private void resetSettings(){
        error.setVisible(true);
        tfLogin.setText("");
        tfPassword.setText("");
    }

    private  boolean checkValid(String passwprd){
        String pattern = "\n([A-Z]|[a-z])+[0-9]+([A-Z]|[a-z])+";
        Pattern reg = Pattern.compile(pattern);
        Matcher tmp = reg.matcher(passwprd);
        return tmp.find();
    }

    public void back() throws IOException {
        PrintWriter printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
        printWriter.println("prev");
        prev.getScene().getWindow().hide();
        crateNewWindow(PATH_TO_SCENE_ONE);
    }
}
