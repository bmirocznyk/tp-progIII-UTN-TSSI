package CompanyUtils;


import CompanyUtils.OrderVerifyerExceptions.CouldNotVerifyOrderException;
import Order.*;
import Client.*;
import Order.FactoryCleanType.CleanData;
import Order.FactoryCleanType.SimpleClean;
import Robots.Surface;
import Services.Classic;
import Services.Economic;
import Services.Platinum;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class OrderVerifyerTest {


    protected OrderVerifyer orderVerifyer=new OrderVerifyer();
    public Location location = new Location("Buenos aires","Olivos","Maipu 3500");;
    public Client client = new Client(111, new Economic(), null);
    public CleanData cleanData = new CleanData("2022-06-01", new HashSet<>(), 1);
    public Order order = new Order(client, cleanData,location, false, false, Surface.PISOS);

    @BeforeEach
    void setUp() {

    }
    @Test
    void economicOrdersOrderingFail(){
        order.setClient(client);
        order.setWantsOrder(true);
        assertThrows(CouldNotVerifyOrderException.ServiceNotIncludedExeption.class,() -> orderVerifyer.verifyOrder(order));
    }

    @Test
    void economicOrderPolishCleaningOk(){
        order.setClient(client);
        order.setWantsOrder(false);
        order.setWantsPolish(true);
        assertDoesNotThrow(() -> orderVerifyer.verifyOrder(order));
    }

    @Test
    void validateServiceEconomicSimpleWithCreditsOk() throws CouldNotVerifyOrderException.ServiceNotIncludedExeption {
        assertTrue( order.getClient().getService().validateService(order));
    }



    @Test
    void validateServicePlatinumOK() throws CouldNotVerifyOrderException.HasNoCreditsExeption { //nunca va a tirar exepción.
        client.setService(new Platinum());

        order.setWantsOrder(true);
        order.setWantsPolish(true);
        client.getService().setCleaningQuantity(-1);
        client.getService().setOrderingQuantity(-1);
        assertTrue(order.getClient().getService().validateClientCredits(order));
    }
    @Test
    void EconomicNoOrderingCreditsFail()  {
        client = new Client(111, new Economic(), null);
        order.setClient(client);
        client.getService().setCleaningQuantity(0);
        assertThrows(CouldNotVerifyOrderException.HasNoCreditsExeption.class, () -> {
            client.getService().validateClientCredits(order);
        });
    }

    @Test
    void EconomicOrderOkCreditsReduce() throws CouldNotVerifyOrderException.HasNoCreditsExeption {
        client = new Client(111, new Economic(), null);
        order.setClient(client);
        client.getService().setCleaningQuantity(1);
        client.getService().validateClientCredits(order);
        assertEquals(0,client.getService().getCleaningQuantity());
    }

    @Test
    void classicUnlimitedCleaningsOk() throws CouldNotVerifyOrderException.HasNoCreditsExeption {
        client = new Client(111, new Classic(), null);
        order.setClient(client);
        client.getService().setCleaningQuantity(0);
        client.getService().validateClientCredits(order);
        assertDoesNotThrow(()->client.getService().validateClientCredits(order));
    }

    @Test
    void classicNoOrderingCreditsFail() throws CouldNotVerifyOrderException.HasNoCreditsExeption {
        client = new Client(111, new Classic(), null);
        order.setWantsOrder(true);
        order.setClient(client);
        client.getService().setOrderingQuantity(0);
        assertThrows(CouldNotVerifyOrderException.HasNoCreditsExeption.class, ()->client.getService().validateClientCredits(order));
    }
    @Test
    void platinumUnlimitedCreditsOk() throws CouldNotVerifyOrderException.HasNoCreditsExeption {
        client = new Client(111, new Platinum(), null);
        order.setWantsOrder(true);
        order.setWantsPolish(true);
        order.setClient(client);
        client.getService().setOrderingQuantity(0);
        client.getService().setCleaningQuantity(0);
        assertDoesNotThrow(()->client.getService().validateClientCredits(order));
    }


}