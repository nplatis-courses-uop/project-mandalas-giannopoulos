package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;
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
            var entry = new ArrayList<String>();
            entry.add(order.getValue0());
            entry.add(Double.toString(Services.calculateCost(order.getValue1())));
            entry.add(dtf.format(LocalDateTime.now()));
            Server.table.getItems().add(entry);
        } catch (IOException|ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
