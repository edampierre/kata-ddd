import ecommerce.business.PurchaseService;
import ecommerce.mapper.RecipientMapper;
import ecommerce.mapper.TransactionMapper;
import ecommerce.repository.CustomerRepository;
import ecommerce.repository.ProductRepository;

import java.util.UUID;

public class application {

    public static void main(String[] arg) {

        CustomerRepository customerRepository = new CustomerRepository();
        ProductRepository productRepository = new ProductRepository();
        TransactionMapper transactionMapper = new TransactionMapper();
        RecipientMapper recipientMapper = new RecipientMapper();

        PurchaseService purchaseService = new PurchaseService(customerRepository, productRepository, transactionMapper, recipientMapper);
        purchaseService.purchaseAProduct(UUID.randomUUID(), UUID.randomUUID(), 5);
    }
}
