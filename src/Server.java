import java.io.*;
import java.net.*;

public class Server {

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
                new EchoThread(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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