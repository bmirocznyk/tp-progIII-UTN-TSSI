package Robots;

public class K311Y_fu extends Robot  implements Polisher{

    public K311Y_fu()
    {
        super(500);
        surfaces.add(Surface.MUEBLES);
    }

    @Override
    public void polish(Integer surface) {
        System.out.println("Estoy puliendo");
    }

    public String toString(){
        return "K311Y-fu";
    }
    
}