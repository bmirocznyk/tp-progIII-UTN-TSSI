package Client;

import java.util.ArrayList;
import java.util.Collection;

import CompanyUtils.Company;
import Order.*;
import Services.Service;


public class Client {
    private int dni;
    private Service service;
    private Collection locations = new ArrayList<Location>();


    public Client(int dni, Service service, Collection locations){
        this.dni=dni;
        this.service=service;
        this.locations = locations;
    };

    public void sendOrder(Company company, Order order) throws Exception
    {
        company.recieveOrder(order);
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getServiceName()
    {
        return service.getServiceName();
    }
}
