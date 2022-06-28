package Robots;

import Order.Order;

import java.util.LinkedList;

public class RobotRegister {
    private Robot robot;
    private LinkedList<Order> orders;

    public RobotRegister(Robot robot, LinkedList<Order> orders)
    {
        this.orders = new LinkedList<Order>();
        this.robot = robot;
        this.orders.addAll(orders);
    }

    public int GetAmountOfOrders()
    {
        return orders.size();
    }

    public Robot GetRobot()
    {
        return robot;
    }

    public void AddOrder(Order order)
    {
        orders.add(order);
    }
}
