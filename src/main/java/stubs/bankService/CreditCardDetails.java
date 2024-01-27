package stubs.bankService;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCardDetails {
    String name;
    String expirationDate;
    String number;
}
