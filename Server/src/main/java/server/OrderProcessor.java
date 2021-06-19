package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.javatuples.Pair;

import common.Services;

public class OrderProcessor implements Runnable {
    private final Socket clientSocket;

    public OrderProcessor(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public void run() {
        try (var ois = new ObjectInputStream(clientSocket.getInputStream())) {
            var order = (Pair<String, ArrayList<String>>) ois.readObject();
            var cost = Services.calculateCost(order.getValue1());
            var plate = order.getValue0();
            var services = order.getValue1();
            var arrivalTime = LocalDateTime.now();
            Server.table.getItems().add(new BookEntry(plate, services, arrivalTime));
        } catch (IOException|ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
