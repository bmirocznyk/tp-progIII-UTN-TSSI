package CompanyUtils;

import Client.Location;
import CompanyUtils.Employees.*;
import CompanyUtils.OrderVerifyerExceptions.CouldNotVerifyOrderException;
import CompanyUtils.PriceUtils.PriceCalculator;
import Order.FactoryCleanType.CleanData;
import Order.FactoryCleanType.ComplexClean;
import Order.FactoryCleanType.SimpleClean;
import Order.Repairs.ElectricalRepair;
import Order.Repairs.GasRepair;
import Order.Repairs.Repair;
import Robots.*;
import Services.Classic;
import Services.Exeptions.ClassicOverpassesDebtExeption;
import Services.Exeptions.EconomicOverpassesDebtExeption;
import Services.Exeptions.OverpassesDebtExeption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Order.*;
import Client.*;
import Services.*;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest
{
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

    @BeforeEach
    void setUp()
    {
        today = LocalDate.now();
        ArrayList<Robot> robots = new ArrayList<>();
        Robot k311Yfu = new K311Y_fu();
        Robot k311Ya = new K311Ya();
        Robot po11h = new P011H();
        Robot so31rty = new S031RTY();
        Robot k311yfl = new K311Y_fl();

        robots.add(k311Yfu);
        robots.add(k311Ya);
        robots.add(po11h);
        robots.add(so31rty);
        robots.add(k311yfl);

        company = new Company(robots, new ArrayList<>(), new ArrayList<>());
        specialists = new ArrayList<>();
        paymentModule = company.getPaymentModule();
        Mockito.reset(paymentModule);

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
    void tryToSendOrderFromClassicWithDebt() throws OverpassesDebtExeption
    {
        testService = new Classic();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);

        Mockito.doThrow(new ClassicOverpassesDebtExeption("El cliente con servicio Classic supero la mora de $2000"))
                .when(paymentModule).checkClientsDebt(testClient);

        assertThrows(ClassicOverpassesDebtExeption.class , () -> {
            testClient.sendOrder(company, testOrder);
        });
    }

    @Test
    void tryToSendOrderFromEconomicThrowCouldNotVerifyOrderException() {
        testService = new Economic();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);

        assertThrows(CouldNotVerifyOrderException.ServiceNotIncludedExeption.class , () -> {
            testClient.sendOrder(company, testOrder);
        });
    }

    @Test
    void tryToSendOrderFromToEconomicSuccess() {
        testService = new Economic();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, false, false, Surface.PISOS);

        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
    }

    @Test
    void tryToSendOrderFromPlatinumClientSuccess()
    {
        testService = new Platinum();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, false, false, Surface.PISOS);

        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
    }

    @Test
    void tryToSendOrderFromEconomicClientWithDebt() throws OverpassesDebtExeption
    {
        testService = new Economic();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, false, false, Surface.PISOS);

        Mockito.doThrow(new EconomicOverpassesDebtExeption("El cliente con servicio Economic se encuentra moroso."))
                .when(paymentModule).checkClientsDebt(testClient);

        assertThrows(EconomicOverpassesDebtExeption.class , () -> {
            testClient.sendOrder(company, testOrder);
        });
    }

    @Test
    void tryToSendOrderFromEconomicClientWithNoCleaningCredits()
    {
        testService = new Economic();
        testClient.setService(testService);

        testOrder = new Order(testClient, testCleanData, location, false, false, Surface.PISOS);

        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
        assertThrows(CouldNotVerifyOrderException.HasNoCreditsExeption.class , () -> {
            testClient.sendOrder(company, testOrder);
        });
    }

    @Test
    void tryToSendOrderFromClassicClientWithNoOrderingCredits()
    {
        testService = new Classic();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);

        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
        assertThrows(CouldNotVerifyOrderException.HasNoCreditsExeption.class , () -> {
            testClient.sendOrder(company, testOrder);
        });
    }

    @Test
    void tryToSendOrderWithElectricalRepairOk()
    {
        testService = new Classic();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);
        repairs.add(new ElectricalRepair(1));
        testOrder.setRepairsNeeded(repairs);
        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
    }

    @Test
    void tryToSendOrderWithGasRepairOk()
    {
        testService = new Classic();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);
        repairs.add(new GasRepair(1));
        testOrder.setRepairsNeeded(repairs);
        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
    }

    @Test
    void tryToSendOrderWithElectricalRepairWithNoEmployeesAvailable()
    {
        testService = new Classic();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);
        repairs.add(new ElectricalRepair(1));
        testOrder.setRepairsNeeded(repairs);
        company.getSpecialistAssigner().clearSpecialistRegister();

        assertThrows(NoHayEspecialistaExepcion.class, () -> testClient.sendOrder(company, testOrder));
    }

    @Test
    void tryToSendOrderWithGasRepairWithNoEmployeesAvailable()
    {
        testService = new Classic();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);
        repairs.add(new GasRepair(1));
        testOrder.setRepairsNeeded(repairs);
        company.getSpecialistAssigner().clearSpecialistRegister();

        assertThrows(NoHayEspecialistaExepcion.class, () -> testClient.sendOrder(company, testOrder));
    }

    @Test
    void tryToSendOrderExpectsASimpleCleanByDate()
    {
        testService = new Classic();
        testClient.setService(testService);
        HashSet<String> dirt = new HashSet<>();
        dirt.add("Barro");
        fifteenDaysAgo = LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth() - 15);
        testCleanData = new CleanData(fifteenDaysAgo.toString(), dirt, 2);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);
        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
        assertInstanceOf(SimpleClean.class, testOrder.getCleanData().getCleanType());
    }

    @Test
    void tryToSendOrderExpectsASimpleCleanBySurface()
    {
        testService = new Classic();
        testClient.setService(testService);
        HashSet<String> dirt = new HashSet<>();
        dirt.add("Polvo");
        testCleanData = new CleanData(LocalDate.now().toString(), dirt, 2);
        fifteenDaysAgo = LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth() - 16);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);
        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
        assertInstanceOf(SimpleClean.class, testOrder.getCleanData().getCleanType());
    }

    @Test
    void tryToSendOrderExpectsASimpleCleanByPets()
    {
        testService = new Classic();
        testClient.setService(testService);
        HashSet<String> dirt = new HashSet<>();
        dirt.add("Barro");
        testCleanData = new CleanData(LocalDate.now().toString(), dirt, 1);
        fifteenDaysAgo = LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth() - 16);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);
        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
        assertInstanceOf(SimpleClean.class, testOrder.getCleanData().getCleanType());
    }

    @Test
    void tryToSendOrderExpectsAComplexClean()
    {
        testService = new Classic();
        testClient.setService(testService);
        HashSet<String> dirt = new HashSet<>();
        dirt.add("Barro");
        fifteenDaysAgo = LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth() - 16);
        testCleanData = new CleanData(fifteenDaysAgo.toString(), dirt, 2);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);
        assertDoesNotThrow(() -> testClient.sendOrder(company, testOrder));
        assertInstanceOf(ComplexClean.class, testOrder.getCleanData().getCleanType());
    }

    @Test
    void getCompanySetOrderPriceOk() throws Exception {
        SpecialistAssigner specialistAssigner = new SpecialistAssigner();
        PriceCalculator priceCalculator =new PriceCalculator();
        priceCalculator.setStrategy(new SimpleClean());
        float subTotal;
        testService = new Classic();
        testClient.setService(testService);
        testOrder = new Order(testClient, testCleanData, location, true, false, Surface.PISOS);
        repairs.add(new ElectricalRepair(1));
        testOrder.setRepairsNeeded(repairs);
        testOrder.addRobot(new K311Y_fl());

        Specialist MaximoMagaldi =new Electritian(5,"MaximoMagaldi");
        Specialist OctavioGurnik =new Gasist(5,"MaximoMagaldi");
        Specialist BrunoMirocznyk =new Electritian(5,"MaximoMagaldi");

        SpecialistRegister.getInstance().addSpecialist(MaximoMagaldi);
        SpecialistRegister.getInstance().addSpecialist(OctavioGurnik);
        SpecialistRegister.getInstance().addSpecialist(BrunoMirocznyk);

        try {
            company.recieveOrder(testOrder);
            System.out.println();

        } catch (NoHayEspecialistaExepcion e) {
            System.out.println("a");
        }
        assertEquals((int)company.getCompanyRegistry().getClientsAgenda().get(0).clientOrders.get(0).getOrderPrice(),6700);
    }
}