package ecommerce.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentInfos {
    String cardNumber;
    String expirationDate;
    String name;
}
