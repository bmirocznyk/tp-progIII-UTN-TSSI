package Client;

public class Location {

    private String provincia;
    private String barrio;
    private String calle;

    public Location(String provincia, String barrio, String calle) {
        this.provincia = provincia;
        this.barrio = barrio;
        this.calle = calle;
    }

    public String getProvincia(){
        return this.provincia;
    }

    public String getBarrio(){
        return this.barrio;
    }
    
    public String getCalle(){
        return this.calle;
    }
}
