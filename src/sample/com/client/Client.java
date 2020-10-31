package sample.com.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Client {
    protected static Socket serverSocket;
    protected static String name;
    protected static String password;

    public void connect(){
        try {
            serverSocket = new Socket("localhost", 8189);
        } catch (IOException e) {
            e.printStackTrace();
        }
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