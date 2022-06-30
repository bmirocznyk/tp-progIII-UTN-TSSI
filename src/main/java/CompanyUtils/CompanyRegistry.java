package CompanyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompanyRegistry {
    ArrayList<ClientOrders> clientsAgenda = new ArrayList<ClientOrders>();
    Integer numberOfSimplex = 0;
    Integer numberOfComplex = 0;


    public ArrayList<ClientOrders> getClientsAgenda() {
        return clientsAgenda;
    }

    public void setClientsAgeda(ArrayList<ClientOrders> clientsAgenda) {
        this.clientsAgenda = clientsAgenda;
    }

    public Integer getNumberOfSimplex() {
        return numberOfSimplex;
    }

    public void setNumberOfSimplex(Integer numberOfSimplex) {
        this.numberOfSimplex = numberOfSimplex;
    }

    public Integer getNumberOfComplex() {
        return numberOfComplex;
    }

    public void increaseNumberOfComplex() {
        this.numberOfComplex++;
    }

    public void increasNumberOfSimplex() {
        this.numberOfSimplex++;
    }

    public void setNumberOfComplex(Integer numberOfComplex) {
        this.numberOfComplex = numberOfComplex;
    }

    public float getTotalIterating( ) {

        float summatory = 0f;
        summatory = this.getClientsAgenda().stream().map(this::getTotalPriceOfClient).reduce(0f, Float::sum);
        return summatory;
    }

    public Map getListOfUsersSpendings( ) {
        Map<Integer, Float> map = new HashMap<Integer, Float>();

        this.getClientsAgenda().forEach((clientOrders -> map.put((clientOrders.getClient().getDni()), getTotalPriceOfClient(clientOrders))));
        return map;
    }


    protected float getTotalPriceOfClient(ClientOrders clientOrders) {
        float summatory = 0f;
        summatory = clientOrders.getClientOrders().stream().map(order -> order.getOrderPrice()).reduce(0f, Float::sum);
        return summatory;
    }
}