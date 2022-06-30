package Robots;


public class P011H extends Robot implements Polisher{

    public P011H()
    {
        super(1500);
        surfaces.add(Surface.PISOS);
    }

    @Override
    public void polish(Integer surface) {

    }

    public String toString(){
        return "P011H";
    }
    
}