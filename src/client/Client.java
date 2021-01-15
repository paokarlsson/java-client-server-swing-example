package client;

import car.Car;

import java.io.*;
import java.net.Socket;

public class Client {
    private final String address;
    private final int port;

    public static final String DEFAULT_ADDRESS = "localhost";
    public static final int DEFAULT_PORT = 10000;

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public Client() {
        this(Client.DEFAULT_ADDRESS, Client.DEFAULT_PORT);
    }

    public Car getCarFromServer(String regnr) {
        try (
                Socket socket = new Socket(address, port);
                ObjectInputStream objectReader = new ObjectInputStream(socket.getInputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        ) {
            writer.write("regnr");
            writer.newLine();
            writer.flush();
            writer.write(regnr);
            writer.newLine();
            writer.flush();
            if (reader.readLine().equals("car")) {
                return (Car) objectReader.readObject();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        return null;

    }
}
