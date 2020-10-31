package sample.com;

import sample.com.server.Output;
import sample.com.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class User extends Server {
    private String name;
    private  Socket socket;
    private String lastMessage;
    private boolean admin;
    private Scanner scanner;
    TimerTask readMessagesUser;

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public User(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
        this.lastMessage = "";
        admin = false;
        try {
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGiveMessages(){
        readMessagesUser = new TimerTask(){
            @Override
            public void run() {
                giveMessages();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(readMessagesUser, 0, 1000);
    }

    public void read() {
        try {
            scanner = new Scanner(socket.getInputStream());
            lastMessage = scanner.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void giveMessages(){
        read();
        if (!lastMessage.equals("")) {
            try {
                messages.put(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        Output.print("Server", "Пользователь " + name + " вышел из чата");
        scanner.close();
        readMessagesUser.cancel();
    }
}
