package Robots;


public class S031RTY extends Robot implements RoomOrganizer{

    public S031RTY()
    {
        super(2700);
        surfaces.add(Surface.ANY);
    }

    @Override
    public void order(Integer surface) {
        System.out.println("Soy S031RTY y estoy ordenando");
    }

    public String toString(){
        return "S031RTY";
    }
    
}