import java.net.Socket;

public class User {
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public EchoThread getThread() {
        return thread;
    }

    public void setThread(EchoThread thread) {
        this.thread = thread;
    }

    public RealServer getServer() {
        return server;
    }

    public void setServer(RealServer server) {
        this.server = server;
    }

    public Socket getSocketuser() {
        return socketuser;
    }

    public void setSocketuser(Socket socketuser) {
        this.socketuser = socketuser;
    }

    public EchoThread thread;
    public RealServer server;

    public Socket socketuser;

    User(String u, Socket s, RealServer server2){
        this.username=u;
        System.out.println(u);
        this.server = server2;
        thread = new EchoThread(s, u,this.server);
        thread.start();
        thread.setName(u);
    }


    public String getUsername(){
        return  username;
    }

}
