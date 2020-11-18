package sample.com.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ControllerSceneMessenger extends Client {
    public Button send;
    public TextArea textArea;
    public VBox vBox;
    public ScrollPane scroll;

    public ControllerSceneMessenger(){
        outputMessages();
    }

    private void outputMessages(){
        Thread printMessage = new Thread(() -> {
            try {
                Scanner scanner = new Scanner(serverSocket.getInputStream());
                while (true) {
                    try {
                        String str = scanner.nextLine();
                        if(isYour(str)){
                            addTextToWindow(changeTheLine(str),"#2e71ec","-fx-font: 18 arial;");
                        }
                        else {
                            addTextToWindow(str,"#000", "-fx-font: 18 arial;");
                        }
                    }catch (NoSuchElementException e){
                       addTextToWindow("Потеряно соединение с сервером!", "#f91707", "-fx-font: 18 arial;");
                       serverSocket.close();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        Platform.runLater(() -> {
                            send.getScene().getWindow().hide();
                            crateNewWindow(PATH_TO_SCENE_ONE);
                        });
                        break;
                    }
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        printMessage.setDaemon(true);
        printMessage.start();
    }

    private void addTextToWindow(String msg, String color, String style){
        Text text = new Text();
        text.setStyle(style);
        text.setWrappingWidth(600);
        text.setFill(Paint.valueOf(color));
        text.setText(msg);
        Platform.runLater(() -> {
            vBox.getChildren().addAll(text);
            scroll.vvalueProperty().bind(vBox.heightProperty());
        });

    }

    public void sendToServer() {
        try {
            PrintWriter printWriter = new PrintWriter(serverSocket.getOutputStream(), true);
            String str = textArea.getText().replace('\n', ' ');
            printWriter.println(str);
            textArea.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isYour(String str){
        int indexSpace = str.indexOf(" ");
        String substring = str.substring(0, --indexSpace);
        return substring.equals(name);
    }

    private String changeTheLine(String str){
        int indexSpace = str.indexOf(" ");
        return "You:" + str.substring(indexSpace);
    }
}
