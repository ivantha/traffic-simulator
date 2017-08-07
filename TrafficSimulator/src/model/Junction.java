package model;

public class Junction {
    private Road nRoad;
    private Road eRoad;
    private Road sRoad;
    private Road wRoad;

    public Junction() {
        nRoad = new Road(1);
        eRoad = new Road(2);
        sRoad = new Road(3);
        wRoad = new Road(4);
    }

    public Road getnRoad() {
        return nRoad;
    }

    public void setnRoad(Road nRoad) {
        this.nRoad = nRoad;
    }

    public Road geteRoad() {
        return eRoad;
    }

    public void seteRoad(Road eRoad) {
        this.eRoad = eRoad;
    }

    public Road getsRoad() {
        return sRoad;
    }

    public void setsRoad(Road sRoad) {
        this.sRoad = sRoad;
    }

    public Road getwRoad() {
        return wRoad;
    }

    public void setwRoad(Road wRoad) {
        this.wRoad = wRoad;
    }
}
