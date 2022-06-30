package CompanyUtils.OrderVerifyerExceptions;

public class CouldNotVerifyOrderException extends Exception{
    public static class HasNoCreditsExeption extends Exception{
        public HasNoCreditsExeption(String message){
            super(message);
        }
    }

    public static class ServiceNotIncludedExeption extends Exception{
        public ServiceNotIncludedExeption(String message){
            super(message);
        }
    }
}
