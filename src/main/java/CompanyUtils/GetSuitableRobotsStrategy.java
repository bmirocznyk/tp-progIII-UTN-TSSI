package CompanyUtils;

import Order.Order;
import Robots.Robot;
import Robots.RobotRegister;

import java.util.ArrayList;

public interface GetSuitableRobotsStrategy
{
    public ArrayList<Robot> GetSuitableRobots(Order order, ArrayList<Robot> robots, ArrayList<RobotRegister> robotsOrders);
}
