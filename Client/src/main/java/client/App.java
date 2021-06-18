package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import org.javatuples.Pair;

public class App {
    public static void send(String plate, List<String> services) {
        try (
            var socket = new Socket("localhost", 5555);
            var objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ) {
            objectOutputStream.writeObject(Pair.with(plate, services));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static boolean plateValidation(String plate) {
        return plate.length() > 2;
    }
}