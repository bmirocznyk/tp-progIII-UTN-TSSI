package CompanyUtils;
import Client.Client;
import Services.Exeptions.OverpassesDebtExeption;

public interface PaymentModule {

    public void checkClientsDebt(Client client) throws OverpassesDebtExeption;
}
