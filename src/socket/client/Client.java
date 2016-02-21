package socket.client;

import java.io.*;
import java.net.*;

/**
 *
 * @author Alex
 */
public class Client {

    private BufferedReader bufferedReader;
    private DataInputStream inFromServer;
    private DataOutputStream outToServer;
    private Socket server;
    private String request;
    private String response;
    private String serverAddress;
    private int port;

    public Client(String serverAddress, int port) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void connect() throws IOException {
        System.out.println("Attempting to connect to server " + this.serverAddress + ":" + this.port + "...");
        this.server = new Socket(this.serverAddress, this.port);
        System.out.println("Connection to server " + this.serverAddress + ":" + this.port + " established" + "\n");
        this.inFromServer = new DataInputStream(this.server.getInputStream());
        this.outToServer = new DataOutputStream(this.server.getOutputStream());
    }

    public void ask() throws IOException {
        while (true) {
            this.request = this.bufferedReader.readLine();
            this.outToServer.writeUTF(this.request);
            this.response = this.inFromServer.readUTF();
            System.out.println(this.response);
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 9999);
        try {
            client.connect();
            client.ask();
        } catch (UnknownHostException exception) {
            System.err.println("UnknownHostException");
            exception.printStackTrace();
        } catch (IOException exception) {
            System.err.println("IOException");
            exception.printStackTrace();
        }
    }
}