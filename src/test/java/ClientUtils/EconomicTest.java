package ClientUtils;

import static org.junit.jupiter.api.Assertions.*;

import Client.Client;
import CompanyUtils.Company;
import CompanyUtils.OrderVerifyer;
import CompanyUtils.OrderVerifyerExceptions.CouldNotVerifyOrderException;
import Services.Economic;
import Services.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Order.Order;

class EconomicTest {
    Client client;
    Service economic =new Economic();
    Order order =new Order();
    Company company;
    OrderVerifyer orderVerifyer =new OrderVerifyer();
    @BeforeEach
    void setUp() {
        client =new Client(5,economic,null);

    }


    @Test
    void economicOrderPolishCleaning(){

        order.setClient(client);
        order.setWantsOrder(true);
        assertThrows(CouldNotVerifyOrderException.ServiceNotIncludedExeption.class,() -> orderVerifyer.verifyOrder(order));
    }

    @Test
    void economicOr(){

        order.setClient(client);
        order.setWantsOrder(true);
        assertThrows(CouldNotVerifyOrderException.ServiceNotIncludedExeption.class,() -> orderVerifyer.verifyOrder(order));
    }


    @Test
    void economicOrderSimple(){
        order.setClient(client);
        order.setWantsOrder(false);

        assertDoesNotThrow(()-> orderVerifyer.verifyOrder(order));
    }


/*
    @Test
    void economicOrderCleaning(){
        this.economic= new ServiceEconomic();
        this.requestOrder = new RequestOrder(true,false);
        this.clienteEconomic = new ClienteEconomic(21001,false,economic ,requestOrder);
        assertDoesNotThrow(clienteEconomic.requireOrder(Company));

    }

    @Test
    void economicDefaulter {
        this.economic= new ServiceEconomic();
        this.requestOrder = new RequestOrder(false,false);
        this.clienteEconomic = new ClienteEconomic(21001,true,economic ,requestOrder);
        assertDoesNotThrow(clienteEconomic.requireOrder(Company));


    }

     */
}