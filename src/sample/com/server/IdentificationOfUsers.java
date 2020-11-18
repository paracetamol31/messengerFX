package sample.com.server;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class IdentificationOfUsers {
    Data data;

    public IdentificationOfUsers(Data data){
        this.data = data;
    }

    public User identifyTheUser(Socket clientSocket){
        String login;
        String password;
        while (true) {
            try {
                Scanner scanner = new Scanner(clientSocket.getInputStream());
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                String key = scanner.nextLine();
                if(key.equals("next")) {
                    login = scanner.nextLine();
                    password = scanner.nextLine();
                    boolean flag = false;
                    if (!login.equals("") && !password.equals("") &&
                            !Validator.validateThereIsSpace(login) && !Validator.validateThereIsSpace(password)) {
                        flag = !isTheUserRegistered(login + " " + password);
                    }
                    printWriter.println(flag);
                    if (flag) break;
                }
                else if(key.equals("prev")){
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        User newUser = new User(login, clientSocket);
        data.addInListUsers(newUser);
        return newUser;
    }

    private boolean isTheUserRegistered(String nameUser){
        File file = new File(data.getPathToTheListOfUsers());
        Scanner readingFromFile;
        String str;
        try {
            readingFromFile = new Scanner(file);
            while (readingFromFile.hasNextLine()) {
                str = readingFromFile.nextLine();
                if(nameUser.equals(str)){
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
}
