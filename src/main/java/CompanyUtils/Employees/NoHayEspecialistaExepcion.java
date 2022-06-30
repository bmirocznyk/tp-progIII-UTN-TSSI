package CompanyUtils.Employees;

public class NoHayEspecialistaExepcion extends Exception{

    private final String message;

    public NoHayEspecialistaExepcion(String message) {
        this.message = message;
    }
}
