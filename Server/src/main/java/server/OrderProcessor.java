package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;

import common.Services;

public class OrderProcessor implements Runnable {
    private final Socket clientSocket;
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public OrderProcessor(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public void run() {
        try (var ois = new ObjectInputStream(clientSocket.getInputStream())) {
            var order = (Pair<String, List<String>>) ois.readObject();
            var cost = Services.calculateCost(order.getValue1());
            var plate = order.getValue0();
            var services = order.getValue1();
            var arrivalTime = LocalDateTime.now();
            var entry = new ArrayList<String>();
            entry.add(plate);
            entry.add(String.format("%.2f", cost));
            entry.add(dtf.format(arrivalTime));
            Server.table.getItems().add(entry);
        } catch (IOException|ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
