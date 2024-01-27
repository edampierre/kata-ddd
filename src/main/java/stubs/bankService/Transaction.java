package stubs.bankService;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction {
    public enum Currency {
        EURO,
        US_DOLLAR,
        YEN
    }

    CreditCardDetails creditCardDetails;
    Float amount;
    Currency currency;

}
