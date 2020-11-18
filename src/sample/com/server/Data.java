package sample.com.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class Data {
    private CopyOnWriteArrayList<User> listUsers = new CopyOnWriteArrayList<>();
    private LinkedBlockingQueue<FormatMessages> listMessages = new LinkedBlockingQueue<>(20);
    final private String PATH_TO_THE_LIST_OF_USERS = "src/sample/resources/users.txt";
    final private String PATH_TO_THE_LIST_MESSAGES = "src/sample/resources/messages.txt";

    public String getPathToTheListOfUsers() {
        return PATH_TO_THE_LIST_OF_USERS;
    }

    public String getPathToTheListMessages(){
        return PATH_TO_THE_LIST_MESSAGES;
    }

    public void addInListUsers(User newUser){
        listUsers.add(newUser);
    }

    public void deleteUserFromListUser(User user){
        listUsers = listUsers.stream().filter(y -> y != user)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        Output output = new Output(this);
        output.printForAllUsers(new FormatMessages("Server", "Пользователь " + user.getName() + " вышел из чата"));
    }

    public void putMessageInListMessages(FormatMessages newMessages){
        try {
            listMessages.put(newMessages);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public FormatMessages takeMessageFromListMessages(){
        try {
            return listMessages.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByName(String nameUser){
        for(User it : listUsers){
            if(it.getName().equals(nameUser))return it;
        }
        return null;
    }

    public void sendMessageAllUsers(FormatMessages message){
        for (User it : listUsers) {
            try {
                PrintWriter printWriter = new PrintWriter(it.getSocket().getOutputStream(), true);
                printWriter.println(message.getNameAuthor() + ": " + message.getMessageText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
