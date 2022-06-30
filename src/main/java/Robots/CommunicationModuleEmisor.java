package Robots;

import CompanyUtils.Company;

public class CommunicationModuleEmisor {

    private Company company;

    public void readyMessage(Robot robot){
        company.getCommunicationModuleReciver().reciveMessage(robot);
    }
    
}
