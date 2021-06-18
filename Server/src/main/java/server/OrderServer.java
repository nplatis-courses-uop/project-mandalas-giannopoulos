package server;

import java.io.IOException;
import java.net.ServerSocket;

public class OrderServer implements Runnable {
    private ServerSocket serverSocket;

    @Override
    public void run() {
        Database.init();
        try {
            serverSocket = new ServerSocket(5555);
            while (Server.isRunning) {
                var clientSocket = serverSocket.accept();
                new Thread(new OrderProcessor(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void shutdown() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}