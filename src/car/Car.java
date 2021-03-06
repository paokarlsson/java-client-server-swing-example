package car;

import java.io.Serializable;

public class Car implements Serializable {
    private String regnr;
    private String brand;
    private String model;
    private int year;

    public Car() {
        this.regnr = "";
        this.brand = "";
        this.model = "";
        this.year = -1;
    }
    public Car(Car car) {
        this.regnr = car.getRegnr();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.year = car.getYear();
    }

    public Car(String regnr, String brand, String model, int year) {
        this.regnr = regnr;
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public String getRegnr() {
        return regnr;
    }

    public void setRegnr(String regnr) {
        this.regnr = regnr;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "regnr='" + regnr + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}
