import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {

    public String name;
    protected Socket socket;
    RealServer server;
    String message = "";
    String username = "";


    public EchoThread(Socket clientSocket, String name, RealServer
            s) {
        this.socket = clientSocket;
        this.username = name;
        this.server = s;
        System.out.println("New client connected: " + name);

    }

    public void run() {

        name = Thread.currentThread().getName();

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
                server.addMessage(currentThread().getName() + " => " + line , currentThread().getName());
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