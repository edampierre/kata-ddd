package stubs.shippingService;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Recipient {
    String name;
    String streetNumber;
    String streetName;
    String PostalCode;
    String city;
    String country;
}
