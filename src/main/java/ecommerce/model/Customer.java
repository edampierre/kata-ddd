package ecommerce.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Customer {

    UUID id;
    String name;
    String firstname;

    String email;

    Address address;

    PaymentInfos paymentInfo;





}
