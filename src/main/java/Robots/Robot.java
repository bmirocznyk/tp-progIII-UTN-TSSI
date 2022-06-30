package Robots;

import java.util.ArrayList;

public abstract class Robot {

    protected ArrayList<Surface> surfaces;
    private CommunicationModuleEmisor communicationModule;
    protected float costPH;

    public Robot(float costPH){
        surfaces = new ArrayList<>();
        this.costPH=costPH;
    }

    public ArrayList<Surface> getSurface(){
        return surfaces;
    }
    public float getCostPH(){
        return costPH;
    }

    public void sendMessage(){
        this.communicationModule.readyMessage(this);
    }

    public boolean doesSupportThisSurface(Surface surface)
    {
        if (surfaces.contains(Surface.ANY))
        {
            return true;
        }
        return surfaces.contains(surface);
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Robot oRobot))
        {
            return false;
        }
        return oRobot.toString().equals(this.toString());
    }

    @Override
    public int hashCode()
    {
        float v = costPH * costPH;
        return (int)v;
    }

}
