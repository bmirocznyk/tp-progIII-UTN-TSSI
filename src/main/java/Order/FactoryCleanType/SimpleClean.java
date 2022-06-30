package Order.FactoryCleanType;

import Order.Order;

public class SimpleClean implements CleanType{

    public SimpleClean(){

    }

    @Override

    public float calculatePrice(Order order){


        float sumatory=0;

        sumatory += order.getRobots().stream().map(y -> y.getCostPH()).reduce(0f, (ans, i) -> ans + i);

        return sumatory;
    }


}
