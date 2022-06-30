package CompanyUtils;

import Order.Order;
import Robots.Polisher;
import Robots.Robot;
import Robots.RobotRegister;
import Robots.RoomOrganizer;

import java.util.ArrayList;
import java.util.Comparator;

public class GetRobotsForPlatinumStrategy implements GetSuitableRobotsStrategy
{
    @Override
    public ArrayList<Robot> GetSuitableRobots(Order order, ArrayList<Robot> robots, ArrayList<RobotRegister> robotsOrders)
    {
        ArrayList<Robot> suitableRobots = new ArrayList<>(robots.stream().filter(
                x -> x.doesSupportThisSurface(order.getSurface())
        ).toList());
        ArrayList<RobotRegister> suitableRobotsWithOrders = new ArrayList<>();

        if (order.doesWantOrder())
        {
            suitableRobots = new ArrayList<>(
                    suitableRobots.stream().filter(
                            x -> x instanceof RoomOrganizer
                    ).toList()
            );
        }
        if (order.doesWantPolish())
        {
            suitableRobots = new ArrayList<>(
                    suitableRobots.stream().filter(
                            x -> x instanceof Polisher
                    ).toList()
            );
        }

        for (Robot robot: suitableRobots)
        {
            int index = GetRobotRegistry(robot, robotsOrders);
            if (index == -1)
            {
                suitableRobots.clear();
                suitableRobots.add(robot);
                return suitableRobots;
            }
            else
            {
                suitableRobotsWithOrders.add(robotsOrders.get(index));
            }
        }
        RobotRegister lessOccupiedRobot = suitableRobotsWithOrders.stream().min(
                Comparator.comparingInt(RobotRegister::GetAmountOfOrders)
        ).get();

        suitableRobots.clear();
        suitableRobots.add(lessOccupiedRobot.GetRobot());

        return suitableRobots;
    }

    private static int GetRobotRegistry(Robot robot, ArrayList<RobotRegister> robotOrders)
    {
        for (RobotRegister robotOrder : robotOrders)
        {
            if (robotOrder.GetRobot().equals(robot))
            {
                return robotOrders.indexOf(robotOrder);
            }
        }
        return -1;
    }
}
