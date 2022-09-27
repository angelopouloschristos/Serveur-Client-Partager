import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {

    public String name;
    protected Socket socket;

    protected int id;



    String message = "";

    // set id



    public void sendMessageToOther(String line) {

        try {
            DataOutputStream cout = new DataOutputStream(socket.getOutputStream());
            cout.writeBytes(line + "\n\r");
            cout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EchoThread(Socket clientSocket, int idClient) {
        this.socket = clientSocket;
        this.id = idClient;
        // ask the name of the client
        // create a thread
        System.out.println("New client connected: " + idClient);

    }

    public long getId(){
        return id;
    }

    public void run() {
        // function get name of thread
        name = Thread.currentThread().getName();
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



                    cout.writeBytes(line + "\n\r");
                    cout.flush();


                    try {
                        FileWriter fw = new FileWriter("src\\chat.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw);
//                        out.println(socket + " = " + line);
                        out.close();
                    } catch (IOException e) {
                        System.out.println("Error writing to file");
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
                Server.clientList.remove(this);
                return;
            }
        }
    }
}