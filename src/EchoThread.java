import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class EchoThread extends Thread {


    protected Socket socket;
    protected int id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message = "";


    public void sendMessageToOther(String line) {

        try {
            DataOutputStream cout = new DataOutputStream(socket.getOutputStream());
            cout.writeBytes(line + "\n\r");
            cout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // if new message receive, notify server


    public EchoThread(Socket clientSocket, int idClient) {
        this.socket = clientSocket;
        this.id = idClient;
        System.out.println("New client connected: " + idClient);

    }

    public void run() {
        // function get name of thread
        String name = Thread.currentThread().getName();
        System.out.println("Thread " + name + " is running" + " id du client : " + id);

        InputStream inp = null;
        BufferedReader cin = null;
        DataOutputStream cout = null;
        try {
            inp = socket.getInputStream();
            cin = new BufferedReader(new InputStreamReader(inp));
            cout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = cin.readLine();
                this.message = line;
                Server.addMessage(line);
                if ((line == null) || line.equals("QUIT")) {
                    socket.close();
                } else {

                    sendMessageToOther(line);

                    System.out.println(line);

                    cout.writeBytes(line + "\n\r");
                    cout.flush();


                    //add the line to the file
                    try {
                        FileWriter fw = new FileWriter("src\\chat.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw);
                        out.println(socket + " = " + line);
                        out.close();
                    } catch (IOException e) {
                        System.out.println("Error writing to file");
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
    }




}