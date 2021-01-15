package server;

import car.Car;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        System.out.println("Starting server at port 10000");
        try (ServerSocket server = new ServerSocket(10000)) {
            while (true) {
        System.out.println("Waiting for client to connect...");
                Socket socket = server.accept();
        System.out.println("New client is connected");
                try (
                        ObjectOutputStream objectWriter = new ObjectOutputStream(socket.getOutputStream());
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ) {
                    String text = reader.readLine();
                    System.out.println("Client sends command: "+ text);
                    if (text.equals("regnr")) {
                        String regnr = reader.readLine();
                    System.out.println("Client sends regnr: "+ regnr);
                        Car car = getCarFromWeb(regnr);
                        if (car != null) {
                            writer.write("car");
                            writer.newLine();
                            writer.flush();
                            System.out.println("Responding with: " + car.toString());
                            objectWriter.writeObject(car);
                        } else {
                            writer.write("error");
                            writer.newLine();
                            writer.flush();
                        }
                    }
                    System.out.println("Client disconnects");
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }

    private static Car getCarFromWeb(String regnr) {
        String url = "https://dt062g.programvaruteknik.nu/carinfo/get.php?regnr=";
        URL urlObject;
        try {
            urlObject = new URL(url + regnr);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlObject.openStream()))) {
                String returned = reader.readLine();
                return new Car(
                        findSubStringBetween(returned, "regnr: ", "<br>"),
                        findSubStringBetween(returned, "brand: ", "<br>"),
                        findSubStringBetween(returned, "model: ", "<br>"),
                        Integer.parseInt(findSubStringBetween(returned, "year: ", "<br>"))
                );
            }
        } catch (IOException e) {
            System.err.println("Failed to create a url object. Error message: " + e.getMessage());
        }
        return null;
    }

    private static String findSubStringBetween(String from, String start, String stop) {
        String temp = from.substring(from.indexOf(start) + start.length());
        return temp.substring(0, temp.indexOf(stop));
    }

}
