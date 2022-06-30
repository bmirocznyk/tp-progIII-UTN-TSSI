package CompanyUtils.Employees;

import Order.Order;
import Order.Repairs.Repair;

import java.util.ArrayList;

public class SpecialistAssigner
{
    private final SpecialistRegister specialistRegister;

    public SpecialistAssigner()
    {
        specialistRegister = SpecialistRegister.getInstance();
    }

    public void iterateOrder(Order order) throws NoHayEspecialistaExepcion {

        order.getRepairsNeeded().forEach(repair ->
                {
                    try {
                        Specialist specialist = getRequiredSpecialist(repair);
                        order.assignSpecialist(getRequiredSpecialist(repair));
                        specialist.setRepair(repair);

                    } catch (NoHayEspecialistaExepcion e) {
                        System.out.println(e.getMessage());
                    }
                }
        );

        if (order.getRepairsNeeded().size() != order.getSpecialistsAssigned().size()) {
            throw new NoHayEspecialistaExepcion("Falta un especialista");
        }
    }

    public void setSpecialistRegister(ArrayList<Specialist> specialists)
    {
        specialistRegister.setSpecialists(specialists);
    }

    public ArrayList<Specialist> getSpecialists()
    {
        return specialistRegister.getSpecialists();
    }

    public void clearSpecialistRegister()
    {
        specialistRegister.clearSpecialist();
    }

    public Specialist getRequiredSpecialist(Repair repair) throws NoHayEspecialistaExepcion
    {
        Specialist specialistToAssign = specialistRegister.getSpecialists().stream().filter(specialist -> specialist.canHandle(repair)).findFirst().orElse(null);
        if(specialistToAssign == null)
        {
            throw new NoHayEspecialistaExepcion("No hay especialistas del tipo" + repair.getType());
        }
        else
        {
            return  specialistToAssign;
        }
    }

}
