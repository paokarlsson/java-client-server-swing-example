package gui;

import car.Car;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Arrays;

public class GUI extends JFrame {
    private Car car;
    private String regnrString;
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
        this.yearText.setText((car.getYear() == -1) ? "" : Integer.toString(car.getYear()));

        setLayout(new BorderLayout());
        setSize(600, 200);
        setTitle("Car info");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        search.addActionListener(a -> {
            regnrString = JOptionPane.showInputDialog("Please enter the registration number to search for:");
            if (regnrString != null && regnrString.length() == 6) {
                try {
                    Worker worker = new Worker();
                    worker.execute();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            } else if (regnrString.length() != 6) {
                JOptionPane.showMessageDialog(null, "The registration number can only be the length of 6");
            }

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

    public class Worker extends SwingWorker<Car, Void> {
        @Override
        protected Car doInBackground() throws ConnectException {
            try (
                    Socket socket = new Socket("localhost", 10000);
                    ObjectInputStream objectReader = new ObjectInputStream(socket.getInputStream());
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) {
                socket.setSoTimeout(10000);
                writer.write("regnr");
                writer.newLine();
                writer.flush();
                writer.write(regnrString);
                writer.newLine();
                writer.flush();
                if (reader.readLine().equals("car")) {
                    return (Car) objectReader.readObject();
                }

            } catch (ConnectException e) {
                throw new ConnectException("Unable to connect to server");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }
            return null;
        }

        @Override
        protected void done() {
            try {
                Car content = get();
                car = new Car(content);
                regnrText.setText(car.getRegnr());
                brandText.setText(car.getBrand());
                modelText.setText(car.getModel());
                yearText.setText((car.getYear() == -1) ? "" : Integer.toString(car.getYear()));
            } catch (Exception e) {
                if (e.getMessage().contains("Unable to connect to server")) {
                    JOptionPane.showMessageDialog(null, e.getMessage().substring(27));
                }
                System.err.println(e.getMessage());
            }
        }

    }
}
