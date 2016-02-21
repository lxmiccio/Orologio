package socket.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class Server {

    private ArrayList<ClientHandler> clients;
    private ServerSocket serverSocket;
    private int port;

    public Server(int port) {
        this.clients = new ArrayList<>();
        this.port = port;
    }

    public void start() throws IOException {
        System.out.println("Starting the server on localhost:" + this.port + "...");
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("Waiting for client on localhost:" + this.serverSocket.getLocalPort() + "...");
        while (true) {
            Socket server = this.serverSocket.accept();
            System.out.println("\n" + "Client " + server.getInetAddress().getCanonicalHostName() + " has connected");

            ClientHandler clientHandler = new ClientHandler(this, server);
            new Thread(clientHandler).start();
            this.clients.add(clientHandler);
        }
    }
    
    public void writeOnClient(String message) throws IOException {
        System.out.println(message);
        for(ClientHandler clientHandler : this.clients) {
            clientHandler.outToClient.writeUTF(message);
            clientHandler.outToClient.flush();
        }
    }

    public static void main(String[] args) {
        Server serverSocket = null;
        try {
            serverSocket = new Server(9999);
            serverSocket.start();
        } catch (IOException exception) {
            System.err.println("Cannot start the server on localhost:" + serverSocket.port);
            exception.printStackTrace();
        }
    }
}
