package Services;

import CompanyUtils.OrderVerifyerExceptions.CouldNotVerifyOrderException;
import Order.Order;
import Services.Exeptions.EconomicOverpassesDebtExeption;

public class Economic extends Service  {

    public Economic(){
            this.cleaningQuantity = 3;
            this.orderingQuantity = 3;
            this.orderValue = 0;

    }


    @Override
    public boolean overpassesDebtLimit(float clientDebt) throws EconomicOverpassesDebtExeption {
        if(clientDebt>0){
            throw new EconomicOverpassesDebtExeption("Cliente economico se paso.");
        }else{
            return true;
        }
    }

    public boolean validateService(Order order) throws CouldNotVerifyOrderException.ServiceNotIncludedExeption {
        if(order.doesWantOrder() && order.getClient().getServiceName().equals("Economic")){
            throw new CouldNotVerifyOrderException.ServiceNotIncludedExeption("El cliente economico no puede pedir ordenamiento.");}
        else
            return true;
    }

    public boolean validateClientCredits(Order order) throws CouldNotVerifyOrderException.HasNoCreditsExeption {
     if (order.getClient().getService().getCleaningQuantity() <= 0) {
        throw new CouldNotVerifyOrderException.HasNoCreditsExeption("El cliente Economico no tiene creditos para limpieza.");
    }else{
         order.getClient().getService().substractCleaningQuantity();
        return true;
    }
    }
}