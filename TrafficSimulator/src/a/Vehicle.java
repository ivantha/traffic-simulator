package a;

import main.Global;

public class Vehicle {
    private int length = (int) (15 + (Math.random() * 10));
    private int width = 10;

    private double velocity = 0;
    private double desiredVelocity = Math.random();
    private double maxAcceleration = 0.05;
    private double breakingDeceleration = 0.15;

    private double timeHeadway = 0.1;
    private double minimumSpacing = 3.0;

    private Trajectory trajectory;

    public Vehicle(int origin, int destination) {
        this.trajectory = new Trajectory(origin, destination);
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
    public double getTimeHeadway() {
        return timeHeadway;
    }
    public void setTimeHeadway(double timeHeadway) {
        this.timeHeadway = timeHeadway;
    }
    public Trajectory getTrajectory() {
        return trajectory;
    }
    public void setTrajectory(Trajectory trajectory) {
        this.trajectory = trajectory;
    }
    public double getDesiredVelocity() {
        return desiredVelocity;
    }
    public void setDesiredVelocity(double desiredVelocity) {
        this.desiredVelocity = desiredVelocity;
    }
    public double getMinimumSpacing() {
        return minimumSpacing;
    }
    public void setMinimumSpacing(double minimumSpacing) {
        this.minimumSpacing = minimumSpacing;
    }
    public double getBreakingDeceleration() {
        return breakingDeceleration;
    }
    public void setBreakingDeceleration(double breakingDeceleration) {
        this.breakingDeceleration = breakingDeceleration;
    }
    public double getVelocity() {
        return velocity;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }


    public double getAcceleration() {
        double distanceToNextCar = Global.CANVAS_RADIUS;
        if(!getTrajectory().isAtFront()){
            Vehicle frontVehicle = trajectory.getFrontVehicle();
            distanceToNextCar = frontVehicle.getTrajectory().getLocation() - getTrajectory().getLocation() - frontVehicle.getLength();
        }

        double a = getMaxAcceleration();
        double b = getBreakingDeceleration();

        double deltaSpeed = getDeltaSpeed();

        double speed = getVelocity();
        double maxSpeed = getDesiredVelocity();
        double freeRoadCoeff = Math.pow(speed / maxSpeed, 4);

        double distanceGap = minimumSpacing;
        double timeGap = speed * timeHeadway;
        double breakGap = (speed * deltaSpeed) / (2 * Math.sqrt(a * b));
        double safeDistance = distanceGap + timeGap + breakGap;

        double busyRoadCoeff = Math.pow(safeDistance / distanceToNextCar, 2);
        double safeIntersectionDistance = 1 + timeGap + (Math.pow(speed, 2) / (2 * b));
        double intersectionCoeff = Math.pow(safeIntersectionDistance / getTrajectory().getDistanceToStopLine(), 2);
        double coeff = 1 - freeRoadCoeff - busyRoadCoeff - intersectionCoeff;

        //Print ----------------------------------------------------------------------------------------
        System.out.println("________________________________________");
        System.out.println("laneIndex               :" + trajectory.getLaneIndex());
        System.out.println("speed                   :" + speed);
        System.out.println("freeRoadCoeff           :" + freeRoadCoeff);
        System.out.println("timeGap                 :" + timeGap);
        System.out.println("breakGap                :" + breakGap);
        System.out.println("safeDistance            :" + safeDistance);
        System.out.println("busyRoadCoeff           :" + busyRoadCoeff);
        System.out.println("safeIntersectionDistance:" + safeIntersectionDistance);
        System.out.println("intersectionCoeff       :" + intersectionCoeff);
        System.out.println("coeff                   :" + coeff);

        return maxAcceleration * coeff;
    }

    public double getDeltaSpeed(){
        if(getTrajectory().isAtFront()){
            return getVelocity();
        }else{
            return getVelocity() - getTrajectory().getFrontVehicle().getVelocity();
        }
    }

    public void move() {
        double delta = 1;
        double acceleration = getAcceleration();

        double speed = getVelocity();
        speed += acceleration * delta;
        double step = (speed * delta) + (0.5 * acceleration * Math.pow(delta, 2));

        setVelocity(speed);
        getTrajectory().setLocation(getTrajectory().getLocation() + step);

        //Print ----------------------------------------------------------------------------------------
        System.out.println("");
        System.out.println("speed2                  :" + speed);
        System.out.println("step                    :" + step);

        for(int i = 0; i < trajectory.getLocation() / 5; i++){
            System.out.print(".");
        }
        System.out.println(" : " + trajectory.getLocation());

//        if(step < 0){
//            System.out.println(">>>> step is less than 0");
//            System.out.println(speed * delta);
//            System.out.println(0.5 * acceleration * Math.pow(delta, 2));
//            System.exit(0);
//        }

//        if(!trajectory.isAtFront() &&
//                step > getTrajectory().getFrontVehicle().getTrajectory().getLocation()
//                        - getTrajectory().getLocation() - getTrajectory().getFrontVehicle().getLength()){
//            System.out.println(">>>> Overlapping cars");
//            System.exit(0);
//        }

    }
}