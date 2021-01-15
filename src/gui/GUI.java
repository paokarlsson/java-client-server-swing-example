package gui;

import car.Car;
import car.CarFactory;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private Car car;
    JMenuBar menu = new JMenuBar();
    JMenu regnr = new JMenu("Regnr");
    JMenuItem search = new JMenuItem("Search...");

    JPanel body = new JPanel();

    JPanel up = new JPanel();
    JPanel center = new JPanel();

    JTextField regnrText = new JTextField();
    JTextField brandText = new JTextField();
    JTextField modelText = new JTextField();
    JTextField yearText = new JTextField();


    public GUI(Car car) {
        this.car = car;
        this.regnrText.setText(car.getRegnr());
        this.brandText.setText(car.getBrand());
        this.modelText.setText(car.getModel());
        this.yearText.setText((car.getYear() == -1)?"":Integer.toString(car.getYear()));

        setLayout(new BorderLayout());
        setSize(600, 200);
        setTitle("Car info");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        search.addActionListener(a -> {
            String searchTerm = JOptionPane.showInputDialog("Please enter the registration number to search for:");
            if (searchTerm != null && searchTerm.length() == 6) {
                this.car = new Car(CarFactory.create(searchTerm));
                System.out.println(this.car.toString());
            } else if(searchTerm.length() != 6) {
                JOptionPane.showMessageDialog(null,"The registration number can only be the length of 6");
            }
            this.regnrText.setText(this.car.getRegnr());
            this.brandText.setText(this.car.getBrand());
            this.modelText.setText(this.car.getModel());
            this.yearText.setText(Integer.toString(this.car.getYear()));
        });
        regnr.add(search);
        menu.add(regnr);
        add(menu, BorderLayout.NORTH);

        up.setLayout(new BoxLayout(up, BoxLayout.PAGE_AXIS));

        body.setLayout(new BorderLayout());
        body.add(up, BorderLayout.NORTH);
        body.add(center, BorderLayout.CENTER);

        up.add(labelAndFieldFactory(regnrText, "Regnr: "));
        up.add(labelAndFieldFactory(brandText, "Brand: "));
        up.add(labelAndFieldFactory(modelText, "Model: "));
        up.add(labelAndFieldFactory(yearText, "Year: "));

        add(body, BorderLayout.CENTER);
    }

    private JPanel labelAndFieldFactory(JTextField textField, String label) {
        JPanel panel = new JPanel();
        textField.setEditable(false);
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.LINE_START);

        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }
}
