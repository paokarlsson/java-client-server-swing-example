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
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }



}
