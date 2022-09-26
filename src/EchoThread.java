import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {
    protected Socket socket;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
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
                if ((line == null) || line.equals("QUIT")) {
                    socket.close();
                    return;
                } else {
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