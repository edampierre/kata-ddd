package ecommerce.mapper;

import ecommerce.model.Customer;
import stubs.bankService.CreditCardDetails;
import stubs.bankService.Transaction;

public class TransactionMapper {

    public Transaction from(Customer customer, Float amount, Transaction.Currency currency) {

        CreditCardDetails creditCardDetails = CreditCardDetails.builder().name(customer.getPaymentInfo().getName())
                                                .expirationDate(customer.getPaymentInfo().getExpirationDate())
                                                .number(customer.getPaymentInfo().getCardNumber()).build();

        return Transaction.builder().creditCardDetails(creditCardDetails).amount(amount).currency(currency).build();

    }


}
