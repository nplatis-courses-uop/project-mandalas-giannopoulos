package server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * A server which waits for incoming connections for cleaning orders
 */
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
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}