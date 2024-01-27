package ecommerce.mapper;

import ecommerce.model.Customer;
import stubs.shippingService.Recipient;

public class RecipientMapper {

    public Recipient from(Customer customer) {

       return Recipient.builder().name(customer.getName() + " " + customer.getFirstname())
               .streetNumber(customer.getAddress().getNumber())
               .streetName(customer.getAddress().getStreet())
               .PostalCode(customer.getAddress().getPostalCode())
               .city(customer.getAddress().getCity())
               .country(customer.getAddress().getCountry()).build();

    }


}
