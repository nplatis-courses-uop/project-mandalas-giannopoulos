package server;

import java.io.IOException;
import java.net.ServerSocket;

public class OrderServer implements Runnable {
    @Override
    public void run() {
        try (var serverSocket = new ServerSocket(5555)) {
            while (true) {
                var clientSocket = serverSocket.accept();
                new Thread(new OrderProcessor(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
