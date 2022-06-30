package ClientUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import CompanyUtils.OrderVerifyerExceptions.CouldNotVerifyOrderException;
import Order.FactoryCleanType.CleanData;
import Robots.Surface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Client.Client;
import CompanyUtils.Company;
import Order.Order;
import Services.Economic;

import java.util.ArrayList;
import java.util.HashSet;


public class ClientTest {

    Company company;

    @BeforeEach
    void setUp()
    {
        company = new Company(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void EconomicClientRequestsCleaningAndOrder(){
        var client = new Client(111, new Economic(), null);

        Order order = new Order(client, new CleanData("2022-06-01", new HashSet<>(), 1),
                null, true, false, Surface.PISOS);

        assertThrows(CouldNotVerifyOrderException.ServiceNotIncludedExeption.class, ()->{
                     client.sendOrder(company, order);
        });
    }

}
