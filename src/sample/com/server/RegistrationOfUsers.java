package sample.com.server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class RegistrationOfUsers{
    private final Data data;

    public RegistrationOfUsers(Data data){
        this.data = data;
    }

    public User registerTheUser(Socket clientSocket) {
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
                    if (!login.equals("") && !password.equals("") && !isUserExists(login) &&
                            !Validator.validateThereIsSpace(login) && !Validator.validateThereIsSpace(password)) {
                        flag = true;
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

        addUserToDatabase(login, password);

        User newUser = new User(login, clientSocket);
        data.addInListUsers(newUser);
        return newUser;
    }

    private void addUserToDatabase(String name, String password){
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(data.getPathToTheListOfUsers(), true));
            writer.append(name).append(" ").append(password).append(String.valueOf('\n'));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUserExists(String name){
        File file = new File(data.getPathToTheListOfUsers());
        try {
            Scanner readingFromFile = new Scanner(file);
            while (readingFromFile.hasNext()) {
                String str = readingFromFile.next();
                if(name.equals(str)){
                    return true;
                }
                readingFromFile.next();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
