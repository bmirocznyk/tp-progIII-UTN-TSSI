package CompanyUtils;

import Order.Order;
import Robots.Polisher;
import Robots.Robot;
import Robots.RobotRegister;
import Robots.RoomOrganizer;

import java.util.ArrayList;
import java.util.Comparator;

public class GetCheapestRobotsStrategy implements GetSuitableRobotsStrategy
{
    @Override
    public ArrayList<Robot> GetSuitableRobots(Order order, ArrayList<Robot> robots, ArrayList<RobotRegister> robotsOrders)
    {
        ArrayList<Robot> suitableRobots = new ArrayList<>();

        Robot suitableRobotForSurface = robots.stream().filter(
                x -> x.doesSupportThisSurface(order.getSurface())
        ).min(Comparator.comparingDouble(Robot::getCostPH)).get();

        suitableRobots.add(suitableRobotForSurface);

        Robot suitableRobotForOrder;
        Robot suitableRobotForPolish;

        if (order.doesWantOrder() && !(suitableRobotForSurface instanceof RoomOrganizer))
        {
            suitableRobotForOrder = robots.stream().filter(
                    x -> x instanceof RoomOrganizer
            ).min(Comparator.comparingDouble(Robot::getCostPH)).get();
            suitableRobots.add(suitableRobotForOrder);
        }
        if (order.doesWantPolish() && !(suitableRobotForSurface instanceof Polisher))
        {
            suitableRobotForPolish = robots.stream().filter(
                    x -> x instanceof Polisher
            ).min(Comparator.comparingDouble(Robot::getCostPH)).get();
            suitableRobots.add(suitableRobotForPolish);
        }

        return suitableRobots;
    }
}
