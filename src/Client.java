import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client extends Thread {

    public static void main(String[] args) {
        System.out.println("CLIENT SIDE:");

        // scanner for user input
        Scanner scanner = new Scanner(System.in);
        String name = "";
        // let the user wirte his name
        System.out.println("Enter your name: ");
        name = scanner.nextLine();


        try {
            // Try to connect to the Server "localhost" on port 6666.
            Socket socket = new Socket("localhost", 6666);

            // Open Input stream.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Open Output stream.
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // send the name to the server
            out.write("00" + name + "\n");
            out.flush();


            // create a thread
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String line = in.readLine();
                            System.out.println("Data received from Server : '" + line + "'.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            t.start();




            // Read data from Server and display it in thread


            while(true) {
                // scanner with client input
                Scanner sc = new Scanner(System.in);
                String message = sc.nextLine();

                // Write data to Server.
                if (message != null) {
                    out.write(message);
                    out.newLine();
                    out.flush();
                }

//                // Read data from Server and display it.
//                String line = in.readLine();
//                System.out.println("Data received from Server : '" + line + "'.");


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