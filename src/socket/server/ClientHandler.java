package socket.server;

import java.io.*;
import java.net.*;

/**
 *
 * @author Alex
 */
class ClientHandler implements Runnable {

    public BufferedReader bufferedReader;
    public DataInputStream inFromClient;
    public DataOutputStream outToClient;
    private Server server;
    private Socket client;
    private String request;

    public ClientHandler(Server server, Socket client) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.server = server;
        this.client = client;
        try {
            this.inFromClient = new DataInputStream(this.client.getInputStream());
            this.outToClient = new DataOutputStream(this.client.getOutputStream());
        } catch (IOException exception) {
            System.err.println("IOException");
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.request = this.inFromClient.readUTF();
                this.server.writeOnClient(this.request);
            }
        } catch (IOException exception) {
            System.err.println("IOException");
            exception.printStackTrace();
        }
    }
}
