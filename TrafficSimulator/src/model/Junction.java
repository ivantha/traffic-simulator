package model;

    public class Junction {
        private final Road nRoad;
        private final Road eRoad;
        private final Road sRoad;
        private final Road wRoad;

        public Junction() {
            nRoad = new Road(1);
            eRoad = new Road(2);
            sRoad = new Road(3);
            wRoad = new Road(4);
        }

        public Road getnRoad() {
            return nRoad;
        }
        public Road geteRoad() {
            return eRoad;
        }
        public Road getsRoad() {
            return sRoad;
        }
        public Road getwRoad() {
            return wRoad;
        }

        public Road getRoad(int roadId){
            switch (roadId){
                case 1:
                    return nRoad;
                case 2:
                    return eRoad;
                case 3:
                    return sRoad;
                case 4:
                    return wRoad;
                default:
                    return null;
            }
        }
    }
