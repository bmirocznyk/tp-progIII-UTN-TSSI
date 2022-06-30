package CompanyUtils;

import java.util.Map;

public class RegistryPrinter {
    private CompanyRegistry companyRegistry;

    public RegistryPrinter(CompanyRegistry companyRegistry){
        this.companyRegistry = companyRegistry;

    }
    public void printUserAndSpent(){
        Map<Integer,Float> map =companyRegistry.getListOfUsersSpendings();

        for (Integer key : map.keySet()) {
            System.out.println("Usuario: " + key + " -> Gasto un total: " + map.get(key));
        }


    }
}
