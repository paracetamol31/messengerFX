package sample.com.server;


import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {
    private final ServerSocket serverSocket;
    Data data;

    public Server(){
        data = new Data();
        serverSocket = createServerSocket();
    }

    public void start() {
        Output output = new Output(data);
        output.outputStoryForServer(new File(data.getPathToTheListMessages()));
        Thread connectWithNewClients = new Thread(() -> {
            while (true) {
                try {
                    Socket finalClientSocket = serverSocket.accept();
                    InitializationOfUsers initializationOfUsers = new InitializationOfUsers(data);
                    User newUser = initializationOfUsers.initialize(finalClientSocket);
                    if(newUser == null) {
                        finalClientSocket.close();
                        continue;
                    }
                    output.outputStoryForUser(newUser);
                    output.printForAllUsers(new FormatMessages("Server",
                            "Пользователь " + newUser.getName() + " вошел в чат"));
                    newUser.giveMessages(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        connectWithNewClients.start();

        Thread sendsMessagesToUsers = new Thread(() -> {
            while (true) {
                    FormatMessages newMessage = data.takeMessageFromListMessages();
                    if (newMessage.isMessageFromAdministrator() && newMessage.getMessageText().charAt(0) == '/') {
                        MessengerCommands messengerCommands = new MessengerCommands(data);
                        messengerCommands.callingCommandByUser(newMessage);
                    } else output.printForAllUsers(newMessage);
            }
        });
        sendsMessagesToUsers.start();

        Thread readingMessagesFromTheServer = new Thread(() -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String str = scanner.nextLine();
                if (str.charAt(0) == '/') {
                    MessengerCommands messengerCommands = new MessengerCommands(data);
                    messengerCommands.callingCommandByServer(str);
                } else output.printForAllUsers(new FormatMessages("Server", str));
            }
        });
        readingMessagesFromTheServer.start();
    }


    private ServerSocket createServerSocket(){
        System.out.println("Введите порт: ");
        Scanner scanner = new Scanner(System.in);
        ServerSocket serverSocket = null;
        boolean flag = true;
        while (flag){
            String port = scanner.next();
            try{
                serverSocket = new ServerSocket(Integer.parseInt(port));
                flag = false;
            } catch (Exception e) {
                flag = true;
                System.out.println("Введите коректный порт: ");
            }
        }
        return serverSocket;
    }
}

