import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {

    public String name;
    protected Socket socket;
    RealServer server;
    String message = "";
    String username = "";


//    public void sendMessageToOther(String line) {
//
//        try {
//            DataOutputStream cout = new DataOutputStream(socket.getOutputStream());
//
//
//            // for each users except myself send the line message
//            for (User user : RealServer.clientList)
//                if (!user.getUsername().equals(username)) {
//                    cout = new DataOutputStream(user.thread.socket.getOutputStream());
//
//                    cout.writeBytes(this.username + " "+ line + "\n\r");
//                    cout.flush();
//                }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public EchoThread(Socket clientSocket, String name, RealServer
            s) {
        this.socket = clientSocket;
        this.username = name;
        this.server = s;
        System.out.println("New client connected: " + name);

    }

    public void run() {
        // function get name of thread
        name = Thread.currentThread().getName();
        //System.out.println("Thread " + name + " is running" + " id du client : " + id);

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
                server.addMessage(currentThread().getName() + " " + line , currentThread().getName());
                if ((line == null) || line.equals("QUIT")) {
                    socket.close();
                    return;

                } else {

                    cout.writeBytes(line + "\n\r");
                    cout.flush();

                }
            } catch (IOException e) {

                return;
            }
        }
    }
}