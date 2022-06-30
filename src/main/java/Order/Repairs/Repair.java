package Order.Repairs;

import java.util.HashMap;
import java.util.Map;

public abstract class Repair
{
    protected int complexity;
    protected float cost;
    protected RepairPriceType priceType;
    protected Map<RepairPriceType, Float> priceChart;


    abstract public String getType();


    public Repair(int complexity)
    {
        this.complexity = complexity;
        priceChart = new HashMap<>();
        verifyComplexityRange();
    }

    public int getComplexity() { return complexity; }

    public float getCost() { return cost; }

    private void verifyComplexityRange()
    {
        if (complexity < 1 || complexity >= 9)
        {
            throw new RuntimeException("Valor para complejidad invalido. (1-8)");
        }
    }

    void calculateRepairPrice()
    {
        if (complexity < 3)
        {
            priceType = RepairPriceType.BASIC;
        }
        else if(complexity <= 6)
        {
            priceType = RepairPriceType.MEDIUM;
        }
        else if(complexity < 9)
        {
            priceType = RepairPriceType.COMPLEX;
        }
        cost = priceChart.get(priceType);
    }
}
