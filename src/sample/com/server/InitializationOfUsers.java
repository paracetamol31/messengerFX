package sample.com.server;



import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class InitializationOfUsers extends Server {
    public static User initialization(Socket clientSocket) {
        Scanner scanner;
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

