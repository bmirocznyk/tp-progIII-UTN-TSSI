package SpecialistAssigner;
import CompanyUtils.Employees.*;
import Order.Order;
import Order.Repairs.ElectricalRepair;
import Order.Repairs.GasRepair;
import Order.Repairs.Repair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class SpecialistTest {

    private SpecialistRegister specialistRegister;
    private SpecialistAssigner specialistAssigner;
    private ArrayList<Repair> repairList;
    private Order order;
    private Specialist electritian;
    private Specialist gasist;

    @BeforeEach
    void setUp() throws ParseException
    {
        specialistRegister = SpecialistRegister.getInstance();
        SpecialistRegister.clearSpecialist();
        specialistAssigner = new SpecialistAssigner();
        repairList = new ArrayList<>();
        order= new Order();
        electritian = new Electritian(5,"Bruno");
        gasist = new Gasist(5,"Bruno");
    }

    @Test
    void getElectricianOk() throws Exception {

        SpecialistRegister.addSpecialist(electritian);
        repairList.add(new ElectricalRepair(5));
        order.setRepairsNeeded(repairList);
        specialistAssigner.iterateOrder(order);

       assertEquals(order.getSpecialistsAssigned().get(0).getClass(),electritian.getClass());
    }

    @Test
    void getGasistOk() throws NoHayEspecialistaExepcion {

        SpecialistRegister.addSpecialist(gasist);
        repairList.add(new GasRepair(5));
        order.setRepairsNeeded(repairList);
        specialistAssigner.iterateOrder(order);
        assertEquals(order.getSpecialistsAssigned().get(0).getClass(),gasist.getClass());

    }

 @Test
    void getGasistAndElectricianOk() throws NoHayEspecialistaExepcion {
        SpecialistRegister.addSpecialist(electritian);
        SpecialistRegister.addSpecialist(gasist);
        repairList.add(new GasRepair(5));
        repairList.add(new ElectricalRepair(5));
        order.setRepairsNeeded(repairList);
        specialistAssigner.iterateOrder(order);
        boolean validation1= order.getSpecialistsAssigned().get(0).getClass()== gasist.getClass();
        boolean validation2= order.getSpecialistsAssigned().get(1).getClass()== electritian.getClass();
        assertTrue(validation1 && validation2);

    }
    @Test
    void getElectricianAndGasistOk() throws NoHayEspecialistaExepcion {
        SpecialistRegister.addSpecialist(gasist);
        SpecialistRegister.addSpecialist(electritian);
        repairList.add(new GasRepair(5));
        repairList.add(new ElectricalRepair(5));
        order.setRepairsNeeded(repairList);
        specialistAssigner.iterateOrder(order);
        boolean validation2= order.getSpecialistsAssigned().get(0).getClass()== gasist.getClass();
        boolean validation1= order.getSpecialistsAssigned().get(1).getClass()== electritian.getClass();
        assertTrue(validation1 && validation2);
    }

    @Test
    void getElectricianAndGasistFailNoGasist(){

        SpecialistRegister.addSpecialist(electritian);
        SpecialistRegister.addSpecialist(electritian);
        repairList.add(new GasRepair(5));
        repairList.add(new ElectricalRepair(5));
        order.setRepairsNeeded(repairList);


        assertThrows(NoHayEspecialistaExepcion.class, () -> {
            specialistAssigner.iterateOrder(order);
        });

}


    @Test
    void noAvailableGasist(){

        SpecialistRegister.addSpecialist(electritian);
        repairList.add(new GasRepair(5));
        order.setRepairsNeeded(repairList);


        assertThrows(NoHayEspecialistaExepcion.class, () -> {
            specialistAssigner.iterateOrder(order);
        });

    }


    @Test
    void noAvailableElectritian(){

        SpecialistRegister.addSpecialist(gasist);
        repairList.add(new ElectricalRepair(5));
        order.setRepairsNeeded(repairList);

        assertThrows(NoHayEspecialistaExepcion.class, () -> {
            specialistAssigner.iterateOrder(order);
        });
    }
}
