package gui;

import car.Car;

import javax.swing.*;

public class UserMain {
    public static void main(String[] args) {
        Car car = new Car();
        SwingUtilities.invokeLater(() -> new GUI(car).setVisible(true));
    }
}
