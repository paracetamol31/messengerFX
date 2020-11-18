package sample.com.server;

import java.io.IOException;

public class MessengerCommands {
    Data data;

    public MessengerCommands(Data data){
        this.data = data;
    }

    public void callingCommandByUser(FormatMessages messages) {
        int indexSpace = messages.getMessageText().indexOf(" ");
        if(indexSpace == -1){
            System.out.println("Вызвана некоректная команда");
            return;
        }
        String command = messages.getMessageText().substring(1, indexSpace);
        String subject = messages.getMessageText().substring(++indexSpace);
        if(callCommand(command, subject, messages.getNameAuthor())){
            Output output =new Output(data);
            output.sendToOneUser(data.getUserByName(messages.getNameAuthor()),
                    new FormatMessages("Server","Некоректная команда"));
        }
    }

    public void callingCommandByServer(String message) {
        int indexSpace = message.indexOf(" ");
        if(indexSpace == -1){
            System.out.println("Вызвана некоректная команда");
            return;
        }
        String command = message.substring(1, indexSpace);
        String subject = message.substring(++indexSpace);
        if(callCommand(command, subject, "Server")){
            System.out.println("Вызвана некоректная команда");
        }
    }

    private boolean callCommand(String command, String subject, String nameAuthor){
        if (command.equals(Commands.BAN.toString())) {
            banUser(subject, nameAuthor);
            return false;
        } else if (command.equals(Commands.GIVEADMIN.toString())) {
            giveAdmin(subject, nameAuthor);
            return false;
        } else {
            return true;
        }
    }

    private void banUser(String subject, String nameAuthor){
        User user = data.getUserByName(subject);
            try {
                Output output = new Output(data);
                output.printForAllUsers(new FormatMessages(nameAuthor, "Пользователь " + user.getName() + " получил бан"));
                user.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private void giveAdmin(String subject, String nameAuthor){
        User user = data.getUserByName(subject);
            user.setAdmin(true);
            Output output = new Output(data);
            output.printForAllUsers(new FormatMessages(nameAuthor,
                    "Даю пользователю " + user.getName() + " права администратора"));
    }
}
