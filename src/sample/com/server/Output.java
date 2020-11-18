package sample.com.server;


import java.io.*;
import java.util.Scanner;

public class Output{
    Data data;

    public Output(Data data){
     this.data = data;
    }

    public  void printForAllUsers(FormatMessages message) {
            data.sendMessageAllUsers(message);
            System.out.println(message.getNameAuthor() + ": " + message.getMessageText());
            addToStory(message.getNameAuthor(), message.getMessageText());
    }

    public  void sendToOneUser(User user, FormatMessages messages){
        try {
            PrintWriter printWriter = new PrintWriter(user.getSocket().getOutputStream(), true);
            printWriter.println(messages.getNameAuthor() + ": " + messages.getMessageText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void outputStoryForUser(User user){
        File file = new File(data.getPathToTheListMessages());
        try ( Scanner readingFromFile = new Scanner(file)){
            while (readingFromFile.hasNextLine()) {
                String nameAuthor = readingFromFile.nextLine();
                String msg = "";
                if(readingFromFile.hasNextLine()){
                    msg = readingFromFile.nextLine();
                }
                sendToOneUser(user, new FormatMessages(nameAuthor, msg));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public  void outputStoryForServer(File file){
        try ( Scanner readingFromFile = new Scanner(file)){
            while (readingFromFile.hasNextLine()) {
                String nameAuthor = readingFromFile.nextLine();
                String msg = "";
                if(readingFromFile.hasNextLine()){
                    msg = readingFromFile.nextLine();
                }
                System.out.println(nameAuthor + ": " + msg);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private  void addToStory(String nameAuthor, String text){
        try(OutputStreamWriter writer =
                    new OutputStreamWriter(new FileOutputStream(data.getPathToTheListMessages(), true))) {
            writer.append(nameAuthor).append(" ").append(String.valueOf('\n'))
                    .append(text).append(String.valueOf('\n'));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
