package CompanyUtils;

import Client.Client;
import Order.FactoryCleanType.CleanData;
import Order.FactoryCleanType.SimpleClean;
import Robots.*;
import Services.Classic;
import Services.Economic;
import Services.Platinum;
import Services.Service;
import Order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RobotAssignerTest {

    public RobotAssigner robotAssigner;
    public ArrayList<Robot> robots;
    public ArrayList<RobotRegister> robotOrders;
    public Client testClient;
    public Service testService;
    Order testOrder;

    public Robot k311Yfu;
    public Robot k311Ya;
    public Robot po11h;
    public Robot so31rty;
    public Robot k311yfl;

    @BeforeEach
    void setUp()
    {
        robotAssigner = new RobotAssigner();
        robots = new ArrayList<>();
        robotOrders = new ArrayList<>();
        testService = new Economic();
        testClient = new Client(43085477, testService, null);
        CleanData cleanData = new CleanData("2022-06-01", new HashSet<>(), 1);
        testOrder = new Order(testClient, cleanData, null, true, false, Surface.PISOS);

        k311Yfu = new K311Y_fu();
        k311Ya = new K311Ya();
        po11h = new P011H();
        so31rty = new S031RTY();
        k311yfl = new K311Y_fl();

        robots.add(k311Yfu);
        robots.add(k311Ya);
        robots.add(po11h);
        robots.add(so31rty);
        robots.add(k311yfl);

        RobotRegister k311yflOrders = new RobotRegister(k311yfl);
        k311yflOrders.AddOrder(testOrder);
        robotOrders.add(k311yflOrders);
    }

    private boolean hasRobot(Order order, Robot expectedRobot)
    {
        for(Robot robot : order.getRobots())
        {
            if (robot.equals(expectedRobot))
            {
                return true;
            }
        }
        return false;
    }

    @Test
    void AssignTwoRobotsForAClassicClient()
    {
        testClient.setService(new Classic());
        assertDoesNotThrow(() ->robotAssigner.AssignRobot(testOrder, robots, robotOrders));
        assertTrue(hasRobot(testOrder, k311yfl));
        assertTrue(hasRobot(testOrder, so31rty));
    }

    @Test
    void AssignTwoRobotsForAClassicWithPolish()
    {
        testClient.setService(new Classic());
        testOrder.setWantsPolish(true);
        assertDoesNotThrow(() ->robotAssigner.AssignRobot(testOrder, robots, robotOrders));
        assertTrue(hasRobot(testOrder, k311yfl));
        assertTrue(hasRobot(testOrder, k311Yfu));
    }

    @Test
    void AssignSingleRobotForPlatinum()
    {
        testClient.setService(new Platinum());
        testOrder.setWantsPolish(false);
        assertDoesNotThrow(() ->robotAssigner.AssignRobot(testOrder, robots, robotOrders));
        assertTrue(hasRobot(testOrder, k311Ya));
    }
}