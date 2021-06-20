package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.javatuples.Pair;

/**
 * Job which connects to the database and writes the received cleaning order to
 * the UI table and to the database
 */
public class OrderProcessor implements Runnable {
    private final Socket clientSocket;

    public OrderProcessor(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public void run() {
        try (var ois = new ObjectInputStream(clientSocket.getInputStream())) {
            var order = (Pair<String, ArrayList<String>>) ois.readObject();
            var plate = order.getValue0();
            var services = order.getValue1();
            var arrivalTime = LocalDateTime.now();
            var entry = new Order(plate, services, arrivalTime);
            Database.create(entry);
            Server.table.getItems().add(entry);
        } catch (IOException|ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
