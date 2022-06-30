package Order.Repairs;

public class ElectricalRepair extends Repair
{
    @Override
    public String getType() {
        return "Electrical";
    }

    public ElectricalRepair(int complexity)
    {
        super(complexity);
        priceChart.put(RepairPriceType.BASIC, 2000f);
        priceChart.put(RepairPriceType.MEDIUM, 4573f);
        priceChart.put(RepairPriceType.COMPLEX, 7359f);
        calculateRepairPrice();
    }
}
