package CompanyUtils.Employees;

import java.util.ArrayList;

public class SpecialistRegister {

    private ArrayList<Specialist> specialists;
    private static SpecialistRegister instance;

    private SpecialistRegister()
    {
        specialists = new ArrayList<>();
    }

    public static SpecialistRegister getInstance() {
        if (instance == null)
            instance = new SpecialistRegister();
        return instance;
    }

    public void addSpecialist(Specialist specialist){
        specialists.add(specialist);
    }

    public void clearSpecialist()
    {
        specialists.clear();
    }

    public void setSpecialists(ArrayList<Specialist> specialists)
    {
        this.specialists = specialists;
    }

    public ArrayList<Specialist> getSpecialists() {
        return specialists;
    }
}

