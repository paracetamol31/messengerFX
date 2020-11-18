package sample.com.server;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class User {
    private String name;
    private Socket socket;
    private String lastMessage;
    private boolean admin;
    private Scanner scanner;

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

    public void giveMessages(Data data) {
        Thread giveMsg = new Thread(() -> {
            boolean flag = true;
            while (flag) {
                try {
                    readMessagesFromUser();
                    if (!lastMessage.equals("")) data.putMessageInListMessages(new FormatMessages(name, lastMessage, admin));
                }catch (NoSuchElementException e){
                    data.deleteUserFromListUser(this);
                    scanner.close();
                    try {
                        socket.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    flag = false;
                }
            }
        });
        giveMsg.start();
    }

    public void readMessagesFromUser() throws NoSuchElementException {
            lastMessage = scanner.nextLine();
    }
}

