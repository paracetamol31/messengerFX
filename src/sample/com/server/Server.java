package sample.com.server;


import sample.com.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Server {
    private static ServerSocket serverSocket;
    protected static CopyOnWriteArrayList<User> listUsers = new CopyOnWriteArrayList<>();
    protected static LinkedBlockingQueue<User> messages = new LinkedBlockingQueue<>(20);
    final static protected String PATH_TO_THE_LIST_OF_USERS = "src/sample/resources/users.txt";
    final static protected String PATH_TO_THE_LIST_MESSAGES = "src/sample/resources/messages.txt";
    final static private int PORT = 8189;

    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        Timer timer = new Timer();
        TimerTask waitUsers = new TimerTask() {
            @Override
            public void run() {
                try {
                    Socket finalClientSocket;
                    finalClientSocket = serverSocket.accept();
                    User newUser = null;
                    while (newUser == null) {
                        newUser = InitializationOfUsers.initialization(finalClientSocket);
                    }
                    User finalNewUser = newUser;
                    Output.storyOutput(finalNewUser);
                    Output.print("Server", "Пользователь " + finalNewUser.getName() + " Вошел в чат");
                    newUser.startGiveMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(waitUsers, 0, 100);

        TimerTask threadForOutput = new TimerTask(){

            @Override
            public void run() {
                    try {
                        User user = messages.take();
                        if (user.isAdmin() && user.getLastMessage().charAt(0) == '/') {
                            MessengerCommands.callingCommandByUser(user);
                        } else Output.print(user.getName(), user.getLastMessage());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        };
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(threadForOutput, 0, 100);

            TimerTask printMessageForServer = new TimerTask(){
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                String str = scanner.nextLine();
                if (str.charAt(0) == '/') {
                    MessengerCommands.callingCommandByServer(str);
                }
                else Output.print("Server", str)    ;
            }
        };
            Timer timer3 = new Timer();
            timer3.scheduleAtFixedRate(printMessageForServer, 0,100);


       TimerTask checkConnectUsers = new TimerTask() {
            @Override
            public void run() {
                listUsers.stream().filter(y -> !y.getSocket().isConnected()).forEach(Server::deleteUser);
            }
        };
        Timer timer4 = new Timer();
        timer4.scheduleAtFixedRate(checkConnectUsers, 0, 1000);
    }



    protected static void deleteUser(User user){
        user.stop();
        listUsers = listUsers.stream().filter(y -> y != user)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }
}

