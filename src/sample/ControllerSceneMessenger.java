package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import sample.com.client.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ControllerSceneMessenger extends Client {
    public Button send;
    public TextArea textArea;
    public VBox vBox;
    public ScrollPane scroll;

    public ControllerSceneMessenger(){
        outputMessages();
    }

    private void outputMessages(){
        ThreadPoolExecutor threadPoolExecutorForUsers = new ThreadPoolExecutor(5, 5,
                50, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));
        Runnable runnableForPrint = new Runnable() {
            @Override
            public void run() {
                try {
                    Scanner scanner = new Scanner(serverSocket.getInputStream());
                    while (true) {
                        String str = scanner.nextLine();
                        Text text = new Text();
                        if(isYour(str)){
                            text.setFill(Paint.valueOf("#2e71ec"));
                            str = changeTheLine(str);
                        }
                        text.setStyle("-fx-font: 18 arial;");
                        text.setWrappingWidth(600);
                        text.setText(str);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                vBox.getChildren().addAll(text);
                                scroll.vvalueProperty().bind(vBox.heightProperty());
                            }
                        });
                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
        threadPoolExecutorForUsers.submit(runnableForPrint);
    }

    public void sendToServer(ActionEvent actionEvent) {
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
