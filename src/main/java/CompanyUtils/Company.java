package CompanyUtils;

import Client.Client;
import CompanyUtils.Employees.NoHayEspecialistaExepcion;
import CompanyUtils.Employees.SpecialistAssigner;
import CompanyUtils.OrderVerifyerExceptions.*;
import CompanyUtils.PriceUtils.PriceCalculator;
import CompanyUtils.RobotAssignerExceptions.*;
import Order.FactoryCleanType.CleanTypeSelector;
import Order.Order;
import Robots.Robot;
import Robots.RobotRegister;
import Services.Exeptions.OverpassesDebtExeption;
import Services.Service;

import java.util.*;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;


public class Company {
    private final ArrayList<Robot> robots;
    private final RobotAssigner robotAssigner;
    private final OrderVerifyer orderVerifyer;
    private final PaymentModule paymentModule;
    private final RegistryPrinter registryPrinter;
    private final SpecialistAssigner specialistAssigner;
    private final CleanTypeSelector cleanTypeSelector;
    private PriceCalculator priceCalculator;
    private CompanyRegistry companyRegistry;
    private ArrayList<Client> clients;
    private ArrayList<Order> orders;
    private ArrayList<RobotRegister> orderPerRobot;
    private CommunicationModuleReciver communicationModuleReciver;
    private float robotAdjustmentFactor;


    public Company(ArrayList<Robot> robots, ArrayList<Client> clients, ArrayList<Order> orders)
    {
        this.robotAssigner = new RobotAssigner();
        this.specialistAssigner = new SpecialistAssigner();
        this.orderVerifyer = new OrderVerifyer();
        this.cleanTypeSelector = CleanTypeSelector.getInstance(this);
        this.priceCalculator = new PriceCalculator();
        this.robots = new ArrayList<>();
        this.orderPerRobot = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.robots.addAll(robots);
        this.clients.addAll(clients);
        this.orders.addAll(orders);
        this.robotAdjustmentFactor = 1;
        this.companyRegistry = new CompanyRegistry();
        this.paymentModule = mock(PaymentModule.class);
        this.registryPrinter = new RegistryPrinter(companyRegistry);
    }

    public void recieveOrder(Order order) throws Exception
    {
        Service backupService = order.getClient().getService();
        ArrayList<RobotRegister> backupRobots = orderPerRobot;
        try
        {
            paymentModule.checkClientsDebt(order.getClient());
            orderVerifyer.verifyOrder(order);
            cleanTypeSelector.setCleanStrategy(order.getCleanData());
            robotAssigner.AssignRobot(order, robots, orderPerRobot);
            specialistAssigner.iterateOrder(order);
            priceCalculator.setStrategy(order.getCleanData().getCleanType());
            order.setOrderPrice(priceCalculator.getFinalPrice(order));
            createOrUpdateClient(order);

        }
        catch (OverpassesDebtExeption e)
        {
            System.out.println("Error de pago.");
            printExceptionReasonAndThrowBack(e);
        }
        catch (CouldNotVerifyOrderException.ServiceNotIncludedExeption e)
        {
            System.out.println("Error verificando el servicio del cliente.");
            printExceptionReasonAndThrowBack(e);
        }
        catch (CouldNotVerifyOrderException.HasNoCreditsExeption e)
        {
            System.out.println("Error de creditos.");
            printExceptionReasonAndThrowBack(e);
        }
        catch (CouldNotAssignRobotException e)
        {
            System.out.println("No se pudo asignar el robot a la orden.");
            order.getClient().setService(backupService);
            printExceptionReasonAndThrowBack(e);
        }
        catch (NoHayEspecialistaExepcion e)
        {
            System.out.println("No se pudo procesar el pedido de reparacion.");
            order.getClient().setService(backupService);
            orderPerRobot = backupRobots;
            printExceptionReasonAndThrowBack(e);
        }
        catch (Exception e)
        {
            System.out.println("Error inesperado procesando el pedido.");
            e.printStackTrace();
            printExceptionReasonAndThrowBack(e);
        }
    }

    public void createOrUpdateClient(Order order){
        ClientOrders clientRegister =findClient(order);

        if((clientRegister!=null)){
            clientRegister.getClientOrders().add(order);
        }
        else{
            ArrayList<Order> firstClientOrder = new ArrayList<Order>();
            firstClientOrder.add(order);
            getCompanyRegistry().getClientsAgenda().add(new ClientOrders(order.getClient(),firstClientOrder));
        }
    };

    public ClientOrders findClient(Order order){
        Stream<ClientOrders> a= companyRegistry.getClientsAgenda().stream().filter(
                (clientOrders -> order.getClient().getDni() == clientOrders.client.getDni()));
        return a.findFirst().orElse(null);
    }

    public void setPriceCalculator(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }
    
    public float getRobotAdjustmentFactor() {
        return this.robotAdjustmentFactor;
    }

    public SpecialistAssigner getSpecialistAssigner()
    {
        return specialistAssigner;
    }

    public CompanyRegistry getCompanyRegistry() {
        return this.companyRegistry;
    }

    public void setCompanyRegistry(CompanyRegistry companyRegistry) {
        this.companyRegistry = companyRegistry;
    }

    public void increaseComplexRegistry(){
        companyRegistry.increaseNumberOfComplex();
    }

    public void increaseSimpleRegistry(){
        companyRegistry.increasNumberOfSimplex();
    }

    public void setRobotAdjustmentFactor(float robotAdjustmentFactor) {
        this.robotAdjustmentFactor = robotAdjustmentFactor;
    }

    private void printExceptionReasonAndThrowBack(Exception e) throws Exception
    {
        System.out.println("Detalle: ");
        System.out.println(e.toString());
        throw e;
    }

    public PaymentModule getPaymentModule() {
        return paymentModule;
    }

    public RegistryPrinter getRegistryPrinter() {
        return this.registryPrinter;
    }

    public CommunicationModuleReciver getCommunicationModuleReciver(){
        return this.communicationModuleReciver;
    }

    public PriceCalculator getPriceCalculator(){
        return this.priceCalculator;
    }
}
