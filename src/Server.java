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
                System.out.println("hello");


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
        messageList.add(message);

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
                    System.out.print(messageList.get(i));
                }

                // for every client
                for (int i = 0; i < clientList.size(); i++) {
                    // send message to client
                    clientList.get(i).sendMessageToOther(messageList.get(messageList.size() - 1));
                }




                // clear the list of messages
                messageList.clear();
            }
        });

        t.start();
        System.out.println(t.isAlive());

    }

}


//
//
//            // Try to open a server socket on port 6666.
//            ServerSocket socket_server = new ServerSocket(6666);
//
//            // Wait a request of connection from a Client.
//            System.out.println("Waiting a request of connection from a Client...");
//            // Accept it and get the socket linked to it.
//            Socket socket_client = socket_server.accept();
//
//            // Open Input stream.
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket_client.getInputStream()));
//            // Open Output stream.
//            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket_client.getOutputStream()));
//
//            // Read data from Client and display it.
//            String line = in.readLine();
//            System.out.println("Data received from Client : '" + line + "'.");
//
//            // Write data to Client.
//            out.write("I am the Server");
//            out.newLine();
//            out.flush();
//
//            // Close streams and sockets.
//            in.close();
//            out.close();
//            socket_client.close();
//            socket_server.close();
//        } catch (IOException e) {
//            // Catch the exception error and display it.
//            System.out.println(e);
//        }
//    }
//}