package CompanyUtils;

import Client.Client;

import Order.Order;
import Services.Economic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyRegistryTest {
    public Client client;
    public Client client2;
    public Client client3;
    public Client client4;
    public Client client5;
    public Company company;
    public Order order = new Order();
    public Order order2= new Order();

    Order order3 =new Order();
    Order order4 =new Order();
    @BeforeEach
    void setUp() {
        company = new Company(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        client = new Client(1, new Economic(), null);
        Order order = new Order();
        client2 = new Client(2, new Economic(), null);
        client3 = new Client(3, new Economic(), null);
        client4 = new Client(4, new Economic(), null);
        client5 = new Client(5, new Economic(), null);
    }

    @Test
    void isUserInDatabaseOk() {
        ClientOrders clientOrdersSeek = new ClientOrders();
        ClientOrders clientOrders=new ClientOrders(client,null);
        order.setClient(client);
        company.getCompanyRegistry().getClientsAgenda().add(clientOrders);
        clientOrdersSeek= company.findClient(order);
        assertNotNull(clientOrdersSeek);
    }
    @Test
    void isUserInDatabaseFailNotRegistered() {
        Order order2 =new Order();
        order2.setClient(client2);
        ClientOrders clientOrdersSeek = new ClientOrders();
        ClientOrders client1Orders=new ClientOrders(client,null);
        company.getCompanyRegistry().getClientsAgenda().add(client1Orders);
        order.setClient(client);
        clientOrdersSeek= company.findClient(order2);
        assertNull(clientOrdersSeek);
    }

    @Test
    void createOrUpdateCreateCreateOk() {
        ClientOrders clientOrdersSeek = new ClientOrders();
        ClientOrders clientOrders=new ClientOrders(client,null);
        order.setClient(client);
        company.createOrUpdateClient(order);
        company.getCompanyRegistry().getClientsAgenda().size();
        assertEquals(1, company.getCompanyRegistry().getClientsAgenda().size());
    }
    @Test
    void createOrUpdateCreateAddUpdateOk() {
        ClientOrders clientOrdersSeek = new ClientOrders();
        ClientOrders clientOrders=new ClientOrders(client,null);
        order.setClient(client);
        company.createOrUpdateClient(order);
        company.createOrUpdateClient(order);
        assertEquals(2, company.getCompanyRegistry().getClientsAgenda().get(0).getClientOrders().size());
    }
    @Test
    void createOrUpdateCreate2RegistersForClientOk() {
        ClientOrders clientOrdersSeek = new ClientOrders();
        ClientOrders clientOrders=new ClientOrders(client,null);
        order.setClient(client);
        company.createOrUpdateClient(order);
        company.createOrUpdateClient(order);
        assertEquals(2, company.getCompanyRegistry().getClientsAgenda().get(0).getClientOrders().size());
    }
    @Test
    void create2UsersAdd2EachOk() {
        ClientOrders clientOrdersSeek = new ClientOrders();
        ClientOrders clientOrders=new ClientOrders(client,null);

        order.setClient(client);
        order2.setClient(client2);

        company.createOrUpdateClient(order);
        company.createOrUpdateClient(order2);
        company.createOrUpdateClient(order2);
        company.createOrUpdateClient(order);
        assertTrue(2 == company.getCompanyRegistry().getClientsAgenda().get(0).getClientOrders().size() && 2 == company.getCompanyRegistry().getClientsAgenda().get(1).getClientOrders().size());
    }

    @Test
    void getUserTotalSpendingOk() {
        order.setClient(client);
        order.setOrderPrice(1f);
        order2.setClient(client);
        order2.setOrderPrice(2f);
        order3.setClient(client);
        order3.setOrderPrice(4f);
        company.createOrUpdateClient(order3);
        company.createOrUpdateClient(order2);
        company.createOrUpdateClient(order);
        company.getCompanyRegistry().getTotalPriceOfClient(company.getCompanyRegistry().getClientsAgenda().get(0));
        assertEquals(7,company.getCompanyRegistry().getTotalPriceOfClient(company.getCompanyRegistry().getClientsAgenda().get(0)));
    }

    @Test
    void getTotalIterating(){
        order.setClient(client);
        order.setOrderPrice(1f);
        order2.setClient(client2);
        order2.setOrderPrice(2f);
        order3.setClient(client2);
        order3.setOrderPrice(4f);
        company.createOrUpdateClient(order3);
        company.createOrUpdateClient(order2);
        company.createOrUpdateClient(order);
        company.getCompanyRegistry().getTotalPriceOfClient(company.getCompanyRegistry().getClientsAgenda().get(0));
        assertEquals(7,company.getCompanyRegistry().getTotalIterating());
    }
    @Test
    void printTest(){

        order.setClient(client);
        order2.setClient(client2);
        order3.setClient(client3);
        order4.setClient(client4);

        order.setOrderPrice(6f);
        order2.setOrderPrice(5f);
        order3.setOrderPrice(7f);
        order4.setOrderPrice(1f);

        company.createOrUpdateClient(order);
        company.createOrUpdateClient(order2);
        company.createOrUpdateClient(order3);
        company.createOrUpdateClient(order4);

        order.setClient(client2);
        order2.setClient(client);
        order3.setClient(client4);
        order4.setClient(client3);

        order.setOrderPrice(100f);
        order2.setOrderPrice(100f);
        order3.setOrderPrice(100f);
        order4.setOrderPrice(100f);

        company.createOrUpdateClient(order);
        company.createOrUpdateClient(order2);
        company.createOrUpdateClient(order3);
        company.createOrUpdateClient(order4);


        order.setClient(client4);
        order2.setClient(client3);
        order3.setClient(client2);
        order4.setClient(client);

        order.setOrderPrice(1);
        order2.setOrderPrice(3);
        order3.setOrderPrice(4);
        order4.setOrderPrice(1020f);

        company.createOrUpdateClient(order);
        company.createOrUpdateClient(order2);
        company.createOrUpdateClient(order3);
        company.createOrUpdateClient(order4);


        order.setClient(client);
        order2.setClient(client2);
        order3.setClient(client4);
        order4.setClient(client3);

        order.setOrderPrice(100);
        order2.setOrderPrice(19f);
        order3.setOrderPrice(110f);
        order4.setOrderPrice(101f);

        company.createOrUpdateClient(order);
        company.createOrUpdateClient(order2);
        company.createOrUpdateClient(order3);
        company.createOrUpdateClient(order4);




        company.getRegistryPrinter().printUserAndSpent();
    }
}
