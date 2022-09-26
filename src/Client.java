import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        System.out.println("CLIENT SIDE:");
        try {
            // Try to connect to the Server "localhost" on port 6666.
            Socket socket = new Socket("localhost", 6666);

            // Open Input stream.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Open Output stream.
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while(true) {
                // scanner with client input
                Scanner sc = new Scanner(System.in);
                String message = sc.nextLine();

                // Write data to Server.
                out.write(message);
                out.newLine();
                out.flush();

                // Read data from Server and display it.
                String line = in.readLine();
                System.out.println("Data received from Server : '" + line + "'.");

                if (message.equals("QUIT")) {
                    break;
                }

            }

            // Close streams and sockets.
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            // Catch the exception error and display it.
            System.out.println(e);
        }
    }
}