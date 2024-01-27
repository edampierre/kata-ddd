package stubs.bankService;

public class BankServiceWS {

    static BankServiceWS bankServiceWS = null;

    private BankServiceWS() {

    }

    public static BankServiceWS bankServiceWS() {
        if(bankServiceWS == null) {
            bankServiceWS = new BankServiceWS();
        }
        return bankServiceWS;
    }

    public void performTransaction(Transaction transaction) {
        System.out.println("Performing Transaction ...");

        System.out.println("Transaction Done");
    }
}
