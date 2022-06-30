package CompanyUtils.Employees;

import Order.Repairs.Repair;


public abstract class Specialist
{
    protected Repair repair;
    protected float salary;
    protected String name;

    public Specialist(String name, float salary)
    {
        this.name = name;
        this.salary = salary;
        this.repair = null;
    }

    public Repair getRepair() { return repair; }

    public void setRepair(Repair repair) { this.repair = repair; }

    public void setSalary(float salary) { this.salary = salary; }

    public float getSalary() { return salary; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract boolean canHandle(Repair repair);

    public abstract void repair();
}
