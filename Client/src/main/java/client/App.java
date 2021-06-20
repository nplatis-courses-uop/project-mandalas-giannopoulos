package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.javatuples.Pair;

/**
 * Implements the functionality of the client
 */
public class App {
    /**
     * Sends information to the server
     */
    public static void send(String plate, ArrayList<String> services) {
        try (
            var socket = new Socket("localhost", 5555);
            var objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ) {
            objectOutputStream.writeObject(Pair.with(plate, services));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Checks whether the inputted plate is valid
     * @param plate
     * @return
     */
    public static boolean plateValidation(String plate) {
        return plate.length() > 2;
    }
}