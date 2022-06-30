package CompanyUtils.PriceUtils;

import Order.Order;
import Order.FactoryCleanType.CleanType;
import Order.FactoryCleanType.CostCalculator;

public class PriceCalculator {

    private CleanType cleanTypeStrategy;
    private CostCalculator costCalculator;

    public PriceCalculator() {
        this.costCalculator = new CostCalculator();
    }

    public void setStrategy(CleanType cleanTypeStrategy){
        this.cleanTypeStrategy = cleanTypeStrategy;
    }

    public float getFinalPrice(Order order){
        return cleanTypeStrategy.calculatePrice(order) + costCalculator.calculatePrice(order);
    }


    public CleanType getCleanTypeStrategy() {
        return cleanTypeStrategy;
    }

    public void setCleanTypeStrategy(CleanType cleanTypeStrategy) {
        this.cleanTypeStrategy = cleanTypeStrategy;
    }
}
