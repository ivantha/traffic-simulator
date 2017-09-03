package model;

import java.util.HashMap;

public class Junction {
        private final HashMap<Integer, Road> roadHashMap = new HashMap<>();

        public Junction() {
            roadHashMap.put(1, new Road(1));        //North
            roadHashMap.put(2, new Road(2));        //East
            roadHashMap.put(3, new Road(3));        //South
            roadHashMap.put(4, new Road(4));        //West
        }

        public Road getRoad(int roadId){
            return roadHashMap.get(roadId);
        }
    }
