package Robots;

public class K311Ya extends Robot implements Polisher,RoomOrganizer {

    public K311Ya()
    {
        super(5000);
        surfaces.add(Surface.PISOS);
        surfaces.add(Surface.MUEBLES);
    }

    @Override
    public void polish(Integer surface) {
        System.out.println("Estoy puliendo");
    }

    @Override
    public void order(Integer surface) {
        System.out.println("Estoy ordenando");
    }

    public String toString(){
        return "K311Y-a";
    }
}