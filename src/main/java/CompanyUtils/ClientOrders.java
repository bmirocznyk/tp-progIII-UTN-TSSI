package CompanyUtils;

import Client.Client;
import Order.Order;

import java.util.ArrayList;

public class ClientOrders {
    Client client;
    ArrayList<Order> clientOrders= new ArrayList<Order>();

    public ClientOrders(){


    }

    public ClientOrders(Client client, ArrayList<Order> clientOrders) {
        this.client = client;
        this.clientOrders = clientOrders;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ArrayList<Order> getClientOrders() {
        return clientOrders;
    }
    public ClientOrders getRegistry(){
        return this;
    }

    public void setClientOrders(ArrayList<Order> clientOrders) {
        this.clientOrders = clientOrders;
    }
}
