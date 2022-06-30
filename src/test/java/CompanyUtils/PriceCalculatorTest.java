package CompanyUtils;

import Client.Location;
import Order.FactoryCleanType.CleanData;
import Order.FactoryCleanType.CleanType;
import Order.FactoryCleanType.SimpleClean;
import Order.Repairs.ElectricalRepair;
import Order.Repairs.Repair;
import Robots.*;
import Robots.Robot;
import Robots.Surface;
import CompanyUtils.Employees.Electritian;
import CompanyUtils.Employees.Gasist;
import CompanyUtils.Employees.NoHayEspecialistaExepcion;
import CompanyUtils.Employees.Specialist;
import CompanyUtils.Employees.SpecialistAssigner;
import CompanyUtils.PriceUtils.*;
import Services.Classic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Order.*;
import Client.*;
import Services.*;
//import org.mockito.internal.matchers.Or;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;




public class PriceCalculatorTest {
    
    PriceCalculator priceCalculator;
    float subTotal;
    Company company;
    PaymentModule paymentModule;
    Client testClient;
    Location location;
    Service testService;
    CleanData testCleanData;
    Order testOrder;
    ArrayList<Repair> repairs;
    ArrayList<Specialist> specialists;
    LocalDate today;
    LocalDate fifteenDaysAgo;
    SpecialistAssigner specialistAssigner;


    @BeforeEach
    void setUp() {
        priceCalculator = new PriceCalculator();
        today = LocalDate.now();
        ArrayList<Robot> robots = new ArrayList<>();
        Robot k311Yfu = new K311Y_fu();
        Robot k311Ya = new K311Ya();
        Robot po11h = new P011H();
        Robot so31rty = new S031RTY();
        Robot k311yfl = new K311Y_fl();
        specialistAssigner = new SpecialistAssigner();

        robots.add(k311Yfu);
        robots.add(k311Ya);
        robots.add(po11h);
        robots.add(so31rty);
        robots.add(k311yfl);

        company = new Company(robots, new ArrayList<>(), new ArrayList<>());
        specialists = new ArrayList<>();
        paymentModule = company.getPaymentModule();

        Specialist electricalSpecialist = new Electritian(1f, "Nikola Tesla");
        Specialist gasSpecialist = new Gasist(1f, "Juan Carlos Perez Garay");

        specialists.add(electricalSpecialist);
        specialists.add(gasSpecialist);

        company.getSpecialistAssigner().setSpecialistRegister(specialists);

        location = new Location("Buenos Aires", "Olivos", "Maipu 2300");
        testClient = new Client(111111111, testService, Collections.singleton(location));
        testService = new Economic();
        testCleanData = new CleanData(LocalDate.now().toString(), new HashSet<>(), 1);
        repairs = new ArrayList<>();
    }

    @Test
    void testPrice0Order() {
        
        priceCalculator.setStrategy(new SimpleClean());

        testService = new Classic();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);
        repairs.add(new ElectricalRepair(1));
        testOrder.setRepairsNeeded(repairs);
        testOrder.addRobot(new K311Y_fl());

        try {
            specialistAssigner.iterateOrder(testOrder);
        } catch (NoHayEspecialistaExepcion e) {
            System.out.println("a");
        }
        

        subTotal = priceCalculator.getFinalPrice(testOrder);

        assertEquals((int)3000, (int)subTotal);

    }


    @Test
    void test1RobotOrder() {
        
        priceCalculator.setStrategy(new SimpleClean());

        testService = new Classic();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);
        repairs.add(new ElectricalRepair(1));
        testOrder.setRepairsNeeded(repairs);
        testOrder.addRobot(new K311Y_fl());

        

        subTotal = priceCalculator.getFinalPrice(testOrder);

        assertEquals(1000, subTotal);

    }

    


    

}
