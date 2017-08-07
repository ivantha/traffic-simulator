package model;

public class Vehicle {
    private int id;
    private int length;
    private int width;
    private double baseSpeed;
    private int origin;
    private int destination;
    private double location;

    public Vehicle(int id, int origin, int destination) {
        this.id = id;
        this.length = 10;
        this.width = 10;
        this.baseSpeed = 0.3;
        this.origin = origin;
        this.destination = destination;
        this.location = 0.0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(double baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public double getLocation() {
        return location;
    }

    public void setLocation(double location) {
        this.location = location;
    }
}