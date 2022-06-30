package Order.FactoryCleanType;

import Order.Order;

public class CostCalculator{

    public CostCalculator(){
        
    }

    public float calculatePrice(Order order){

        float sumatory = 0f;

        if (order.getSpecialistsAssigned().size() > 0) {
            sumatory += (
                order.getSpecialistsAssigned().stream()
                .map(specialist -> specialist.getRepair().getCost())
                .reduce(0f, (ans, i) -> ans + i)
            );
    
            sumatory += (
                order.getSpecialistsAssigned().stream()
                .map(specialist -> specialist.getSalary() / 160 * specialist.getRepair().getComplexity())
                .reduce(0f, (ans, i) -> ans + i)
            );
        }


        return sumatory;
    }
    
}
