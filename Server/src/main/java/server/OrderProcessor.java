package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;

public class OrderProcessor implements Runnable {
    private final Socket clientSocket;

    public OrderProcessor(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public void run() {
        try (var ois = new ObjectInputStream(clientSocket.getInputStream())) {
            var order = (Pair<String, List<String>>) ois.readObject();
            var entry = new ArrayList<String>();
            Server.table.getItems().add(entry);
        } catch (IOException|ClassNotFoundException e) {
            System.err.println(e);
        }
    }
}
