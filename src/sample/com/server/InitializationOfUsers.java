package sample.com.server;



import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class InitializationOfUsers {
    private final Data data;

    public InitializationOfUsers(Data data){
        this.data = data;
    }

    public User initialize(Socket clientSocket) {
        Scanner scanner;
        String str = "";
        try {
            scanner = new Scanner(clientSocket.getInputStream());
            str = scanner.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (str.equals("registration")) {
            RegistrationOfUsers registrationOfUsers = new RegistrationOfUsers(data);
            return registrationOfUsers.registerTheUser(clientSocket);
        }
        else if(str.equals("authentication")){
            IdentificationOfUsers authenticationOfUsers = new IdentificationOfUsers(data);
            return authenticationOfUsers.identifyTheUser(clientSocket);
        }
        return null;
    }
}

