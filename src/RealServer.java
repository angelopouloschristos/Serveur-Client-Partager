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
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = in.readLine();

                    if (line.startsWith("00")) {
                        line = line.substring(2);
                        users.add(line);

                        clientList.add(new User(line, socket, this));
                        System.out.println("Current users: " + clientList.size());
                    }

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

                for (User user : clientList) {
                    try {
                        if (!user.getUsername().equals(t)) {
                            DataOutputStream cout = new DataOutputStream(user.thread.socket.getOutputStream());

                            cout.writeBytes(message + "\n");
                            cout.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                try {

                    FileWriter fw = new FileWriter("chat.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw);

                    out.write( message + "\r");



//                    out.println(message);
                    out.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                messageList.clear();
            }
        });

        monthread.start();
    }
}