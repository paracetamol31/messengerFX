package sample.com.server;



import sample.com.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class InitializationOfUsers extends Server {
    public static User initialization(Socket clientSocket) {
        Scanner scanner = null;
        String str = "";
        try {
            scanner = new Scanner(clientSocket.getInputStream());
            str = scanner.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (str.equals("registration")) {
            return RegistrationOfUsers.registration(clientSocket);
        }
        else if(str.equals("authentication")){
            return AuthenticationOfUsers.authentication(clientSocket);
        }
        return null;
    }
}

