package gui;

import car.Car;
import client.Client;

import javax.swing.*;

public class UserMain {
    public static void main(String[] args) {
        Client client = new Client();
        Car car = client.getCarFromServer("MUG200");
        System.out.println(car.toString());
//        SwingUtilities.invokeLater(() -> new GUI(car).setVisible(true));
    }
}
