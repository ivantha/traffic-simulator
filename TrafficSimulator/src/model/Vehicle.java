package model;

public class Vehicle {
    private int id;
    private int length;
    private int width = 10;

    private int origin;
    private int destination;
    private double location = 0.0;
    private int time = 0;
    private double velocity = 0;

    private double desiredVelocity;
    private double maxAcceleration = 0.5;
    private double minimumSpacing = 20;
    private double breakingDecleration = 20;

    public Vehicle(int id, int origin, int destination) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;

        this.length = (int) (Math.random() * 10) + 15;
        this.desiredVelocity = Math.random() * 2;
        this.velocity = this.desiredVelocity / 4;
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

    public double getDesiredVelocity() {
        return desiredVelocity;
    }

    public void setDesiredVelocity(double desiredVelocity) {
        this.desiredVelocity = desiredVelocity;
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

    public double getMinimumSpacing() {
        return minimumSpacing;
    }

    public void setMinimumSpacing(double minimumSpacing) {
        this.minimumSpacing = minimumSpacing;
    }

    public double getBreakingDecleration() {
        return breakingDecleration;
    }

    public void setBreakingDecleration(double breakingDecleration) {
        this.breakingDecleration = breakingDecleration;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }
}