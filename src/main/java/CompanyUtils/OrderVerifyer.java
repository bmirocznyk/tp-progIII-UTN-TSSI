package CompanyUtils;

import CompanyUtils.OrderVerifyerExceptions.CouldNotVerifyOrderException;
import Robots.Robot;
import Order.Order;
import Client.Client;


public class OrderVerifyer
{
    public void verifyOrder(Order order) throws Exception{
        try
        {
            order.getClient().getService().validateService(order);
            order.getClient().getService().validateClientCredits(order);
        }
        catch (CouldNotVerifyOrderException.ServiceNotIncludedExeption | CouldNotVerifyOrderException.HasNoCreditsExeption e)
        {
            throw e;
        }
    }
}