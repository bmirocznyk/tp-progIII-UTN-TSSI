package CompanyUtils;

import CompanyUtils.RobotAssignerExceptions.CouldNotAssignRobotException;
import Robots.Robot;
import Order.Order;
import Robots.RobotRegister;

import java.util.ArrayList;


public class RobotAssigner
{
    private GetSuitableRobotsStrategy strategy;

    public void AssignRobot(Order order, ArrayList<Robot> robots, ArrayList<RobotRegister> robotsOrders) throws CouldNotAssignRobotException
    {
        ArrayList<Robot> suitableRobots;

        if(order.getClient().getService().getServiceName().equals("Platinum"))
        {
            strategy = new GetRobotsForPlatinumStrategy();
        }
        else
        {
            strategy = new GetCheapestRobotsStrategy();
        }

        suitableRobots = strategy.GetSuitableRobots(order, robots, robotsOrders);

        if (suitableRobots.size() == 0)
        {
            throw new CouldNotAssignRobotException("No hay robots disponibles para el pedido solicitado");
        }

        for(Robot robot : suitableRobots)
        {
            order.addRobot(robot);
            RobotRegister assignedRobotRegistry;
            assignedRobotRegistry = robotsOrders.stream().filter(
                    x -> x.GetRobot().equals(robot)
            ).findFirst().orElse(null);

            if (assignedRobotRegistry == null)
            {
                assignedRobotRegistry = new RobotRegister(robot);
                assignedRobotRegistry.AddOrder(order);
                robotsOrders.add(assignedRobotRegistry);
            }
            else
            {
                robotsOrders.get(robotsOrders.indexOf(assignedRobotRegistry)).AddOrder(order);
            }
        }
    }
}
