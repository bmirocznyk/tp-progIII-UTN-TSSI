package CompanyUtils.Employees;

import Order.Repairs.Repair;

import java.util.Objects;

public class Gasist extends Specialist {

    public Gasist(float salary, String name)
    {
        super(name, salary);
    }

    public boolean canHandle(Repair repair) {
        return Objects.equals(repair.getType(), "Gas");
    }

    public void repair() {}
}
