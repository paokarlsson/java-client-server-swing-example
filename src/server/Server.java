package server;

import java.io.*;
import java.net.*;

public class Server {

    private static ServerThread serverThread;

    public static void main(String[] args) {

        // See the nested class below this main method.
        serverThread = new ServerThread();
        serverThread.start();

        waitForExitCall();
    }

    private static void waitForExitCall() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                if (reader.readLine().equalsIgnoreCase("q")) {
                    serverThread.closeServer();
                    System.out.println("The server is down");
                    break;
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    static class ServerThread extends Thread {
        // Placing the declaration here to be able to close the connection from the method closeServer().
        ServerSocket server;
        // To make the exit more pretty.
        boolean terminate = false;

        @Override
        public void run() {
            System.out.println("---------------------------------------");
            System.out.println("Starting the server at port 10000");
            System.out.println("Type Q/q + [Enter] to terminate server");
            System.out.println("---------------------------------------");
            try {
                server = new ServerSocket(10000);
                while (true) {
                    System.out.println("Waiting for client to connect...");
                    Socket socket = server.accept();
                    System.out.println("New client is connected");
                    new ClientHandler(socket).start();
                }
            } catch (IOException e) {
                if (!terminate)
                    System.err.println("Error: " + e.getMessage());
            } finally {
                try {
                    server.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        public void closeServer() {
            try {
                terminate = true;
                this.server.close();
            } catch (IOException e) {
                System.err.println("Failed to close server. Error: " + e.getMessage());
            }
        }
    }
}
