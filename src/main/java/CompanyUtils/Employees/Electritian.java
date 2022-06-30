package CompanyUtils.Employees;

import Order.Repairs.Repair;

import java.util.Objects;

public class Electritian extends Specialist {

    public Electritian(float salary, String name)
    {
        super(name, salary);
    }

    public void repair() {

    }

    public boolean canHandle(Repair repair) {
        return Objects.equals(repair.getType(), "Electrical");
    }
}
