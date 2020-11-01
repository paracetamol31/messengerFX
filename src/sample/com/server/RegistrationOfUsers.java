package sample.com.server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class RegistrationOfUsers extends InitializationOfUsers {
    public static User registration(Socket clientSocket) {
        String login;
        String password;
        password = " ";
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


        addingUserToDatabase(login, password);

        User newUser = new User(login, clientSocket);
        listUsers.add(newUser);
        return newUser;
    }

    private static void addingUserToDatabase(String name, String password){
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(PATH_TO_THE_LIST_OF_USERS, true));
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

    private static boolean isUserExists(String name){
        File file = new File(PATH_TO_THE_LIST_OF_USERS);
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
