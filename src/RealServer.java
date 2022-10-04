import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class RealServer {

    public static ArrayList<User> clientList = new ArrayList<User>();
    ArrayList<String> users = new ArrayList<String>();
    ArrayList<String> messageList = new ArrayList<String>();


    public void main(String[] args) {


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
                    // if the "in" message starts with 00
                    // it's the name of the client
                    // else it's a message
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = in.readLine();

                    if (line.startsWith("00")) {
                        // remove the 00 from line
                        line = line.substring(2);
                        // add line to user List
                        users.add(line);

                        clientList.add(new User(line, socket, this));
                        System.out.println("Current users: " + clientList.size());
                    }

                    // if new message receive, call run
                    for (int i = 0; i < clientList.size(); i++) {
                        if (!clientList.get(i).thread.isAlive()) {
                            clientList.remove(i);
                            users.remove(i);
                        }
                    }


                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }



    public void addMessage(String message, String nomDeThread) {
//        messageList.add(message);


        Thread monthread = new Thread(new Runnable() {
            @Override
            public void run() {
                String t = nomDeThread;


                // for each client add message to list
                for (int i = 0; i < clientList.size(); i++) {
                    // if message is not empty
                    if (!clientList.get(i).thread.message.equals("")) {
                        // add message to list
                        messageList.add(clientList.get(i).getUsername() + " : " + clientList.get(i).thread.message);
                    }
                }

//                // print the list of all messages in one line
//                for (int i = 0; i < messageList.size(); i++) {
//                    System.out.print(messageList.get(i) + " ");
//                }



                // for every client
                for (User user : clientList)

//                    user.thread.sendMessageToOther(message.toString());

//                    if( user.getSocketuser() != null) {
                        try {
//                            DataOutputStream cout = new DataOutputStream(user.getSocketuser().getOutputStream());
//

                            // for each users except myself send the line message

                            if (!user.getUsername().equals(t)) {
                                DataOutputStream cout = new DataOutputStream(user.thread.socket.getOutputStream());

                                cout.writeBytes(message + "  =>  " + "\n\r");
                                cout.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                    }

                messageList.clear();

            }
        });

        monthread.start();
    }
}