import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    static ArrayList<EchoThread> clientList = new ArrayList<EchoThread>();
    //message list
    static ArrayList<String> messageList = new ArrayList<String>();

    public static void main(String[] args) {

        System.out.println("SERVER SIDE:");
        try {
            ServerSocket serverSocket = null;
            Socket socket = null;

            try {
                serverSocket = new ServerSocket(6666);
            } catch (IOException e) {
                e.printStackTrace();

            }
            while (true) {
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }

                // new thread for a client
                clientList.add(new EchoThread(socket, socket.hashCode()));
                clientList.get(clientList.size() - 1).start();


                // if new message receive, call run
                for (int i = 0; i < clientList.size(); i++) {
                    if (!clientList.get(i).isAlive()) {
                        clientList.remove(i);
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public static void addMessage(String message){
//        messageList.add(message);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                // for each client add message to list
                for (int i = 0; i < clientList.size(); i++) {
                    // if message is not empty
                    if (!clientList.get(i).message.equals("")) {
                        // add message to list
                        messageList.add(clientList.get(i).message);
                    }
                }

                // print the list of all messages in one line
                for (int i = 0; i < messageList.size(); i++) {
                    System.out.print(messageList.get(i) + " ");
                }

                // for every client
                for (EchoThread clientList: clientList) {
                    // send message to client
                    //transform all the message in one string
                    StringBuilder message = new StringBuilder();
                    for (int i = 0; i < messageList.size(); i++) {
                        message.append("last message from : "+ clientList.getId() +" " + messageList.get(i)).append(" ");
                    }

                    clientList.sendMessageToOther(message.toString());

                }
                messageList.clear();

                // clear the list of messages

            }
        });

        t.start();


    }

}


