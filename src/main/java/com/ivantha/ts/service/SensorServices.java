package com.ivantha.ts.service;

import com.ivantha.ts.common.Global;
import com.ivantha.ts.common.Session;
import com.ivantha.ts.model.Vehicle;

import java.util.ArrayList;
import java.util.Iterator;

public class SensorServices {
    public static ArrayList<Boolean> getN1InTraffic() {
        ArrayList<Boolean> result = new ArrayList<>();
        Iterator<Vehicle> vehicleIterator = Session.getnRoad().getLane(1).getVehicleArrayList().iterator();
        for (int i = 0; i < 10; i++){
            if(vehicleIterator.hasNext()){
                Vehicle vehicle = vehicleIterator.next();
                int position = 10 - (int)(Global.ROAD_LENGTH / vehicle.getTrajectory().getLocation());
                if(position == i){

                }else if(position > i){

                }else{
                    System.out.println("This is not supposed to happen");
                    System.exit(1);
                }
            }else{
                result.add(false);
            }
        }

        return result;
    }

    public static ArrayList<Boolean> getN2InTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getN3InTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getN1OutTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getN2OutTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getN3OutTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getE1InTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getE2InTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getE3InTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getE1OutTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getE2OutTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getE3OutTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getS1InTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getS2InTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getS3InTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getS1OutTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getS2OutTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getS3OutTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getW1InTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getW2InTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getW3InTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getW1OutTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getW2OutTraffic() {
        return new ArrayList<>();
    }

    public static ArrayList<Boolean> getW3OutTraffic() {
        return new ArrayList<>();
    }
}
