package com.ivantha.ts.service;

import com.ivantha.ts.common.Session;

public class SensorServices {
    public static boolean[] getN1InTraffic() {
        return Session.getnRoad().getLane(1).getSensorArray();
    }

    public static boolean[] getN2InTraffic() {
        return Session.getnRoad().getLane(2).getSensorArray();
    }

    public static boolean[] getN3InTraffic() {
        return Session.getnRoad().getLane(3).getSensorArray();
    }

    public static boolean[] getN1OutTraffic() {
        return Session.getnRoad().getLane(4).getSensorArray();
    }

    public static boolean[] getN2OutTraffic() {
        return Session.getnRoad().getLane(5).getSensorArray();
    }

    public static boolean[] getN3OutTraffic() {
        return Session.getnRoad().getLane(6).getSensorArray();
    }

    public static boolean[] getE1InTraffic() {
        return Session.geteRoad().getLane(1).getSensorArray();
    }

    public static boolean[] getE2InTraffic() {
        return Session.geteRoad().getLane(2).getSensorArray();
    }

    public static boolean[] getE3InTraffic() {
        return Session.geteRoad().getLane(3).getSensorArray();
    }

    public static boolean[] getE1OutTraffic() {
        return Session.geteRoad().getLane(4).getSensorArray();
    }

    public static boolean[] getE2OutTraffic() {
        return Session.geteRoad().getLane(5).getSensorArray();
    }

    public static boolean[] getE3OutTraffic() {
        return Session.geteRoad().getLane(6).getSensorArray();
    }

    public static boolean[] getS1InTraffic() {
        return Session.getsRoad().getLane(1).getSensorArray();
    }

    public static boolean[] getS2InTraffic() {
        return Session.getsRoad().getLane(2).getSensorArray();
    }

    public static boolean[] getS3InTraffic() {
        return Session.getsRoad().getLane(3).getSensorArray();
    }

    public static boolean[] getS1OutTraffic() {
        return Session.getsRoad().getLane(4).getSensorArray();
    }

    public static boolean[] getS2OutTraffic() {
        return Session.getsRoad().getLane(5).getSensorArray();
    }

    public static boolean[] getS3OutTraffic() {
        return Session.getsRoad().getLane(6).getSensorArray();
    }

    public static boolean[] getW1InTraffic() {
        return Session.getwRoad().getLane(1).getSensorArray();
    }

    public static boolean[] getW2InTraffic() {
        return Session.getwRoad().getLane(2).getSensorArray();
    }

    public static boolean[] getW3InTraffic() {
        return Session.getwRoad().getLane(3).getSensorArray();
    }

    public static boolean[] getW1OutTraffic() {
        return Session.getwRoad().getLane(4).getSensorArray();
    }

    public static boolean[] getW2OutTraffic() {
        return Session.getwRoad().getLane(5).getSensorArray();
    }

    public static boolean[] getW3OutTraffic() {
        return Session.getwRoad().getLane(6).getSensorArray();
    }
}

