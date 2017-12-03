package com.ivantha.ts.service;

import com.ivantha.ts.common.Session;
import com.ivantha.ts.model.RoadMap;
import com.ivantha.ts.util.CustomTimerTask;

import java.util.Timer;

import static com.ivantha.ts.common.Global.REFRESH_INTERVAL;

public class AppServices {
    public static void startTraffic() {
        AppServices.stopTraffic();

        Session.setMainTimer(new Timer());
        Session.setMainTimerTask(new CustomTimerTask());
        Session.getMainTimer().schedule(Session.getMainTimerTask(), 0, REFRESH_INTERVAL);
        Session.getUiUpdater().play();

        Session.setStarted(true);
    }

    public static void stopTraffic() {
        if (null != Session.getMainTimer()) {
            Session.getMainTimer().cancel();
            Session.getMainTimer().purge();
        }
        Session.getUiUpdater().stop();

        Session.setStarted(false);
    }

    public static void resetTraffic() {
        Session.setRoadMap(new RoadMap());

        Session.getRoadMap().setNorthEnabled(false);
        Session.getRoadMap().setEastEnabled(false);
        Session.getRoadMap().setSouthEnabled(false);
        Session.getRoadMap().setWestEnabled(false);
    }
}
