package Services;

import CompanyUtils.OrderVerifyerExceptions.CouldNotVerifyOrderException;
import Order.Order;
import Services.Exeptions.EconomicOverpassesDebtExeption;

public class Classic extends Service  {

    public Classic(){
        this.cleaningQuantity = -1;
        this.orderingQuantity = 3;
        this.orderValue = 0;
        this.maxDebt = 0;
    }


    public boolean overpassesDebtLimit(float clientDebt) throws EconomicOverpassesDebtExeption {
        if(clientDebt>2001){
            throw new EconomicOverpassesDebtExeption("El usuario clasico tiene deuda, por lo tanto no puede ordenar");
        }else {
            return true;
        }
    }

    @Override
    public boolean validateService(Order order) throws CouldNotVerifyOrderException.ServiceNotIncludedExeption {
            return true;
    }
    public boolean validateClientCredits(Order order) throws CouldNotVerifyOrderException.HasNoCreditsExeption {
        if (order.doesWantOrder() && order.getClient().getService().getOrderingQuantity() <= 0) {
            throw new CouldNotVerifyOrderException.HasNoCreditsExeption("El cliente clasico no tiene creditos");
        } else {
            order.getClient().getService().substractOrderingQuantity();
            return true;
        }
    };


}