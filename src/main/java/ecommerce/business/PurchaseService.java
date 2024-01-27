package ecommerce.business;

import ecommerce.mapper.RecipientMapper;
import ecommerce.mapper.TransactionMapper;
import ecommerce.model.Customer;
import ecommerce.model.Product;
import ecommerce.repository.CustomerRepository;
import ecommerce.repository.ProductRepository;
import stubs.bankService.BankServiceWS;
import stubs.bankService.Transaction;
import stubs.shippingService.ShippingServiceWS;

import java.time.OffsetDateTime;
import java.util.UUID;

public class PurchaseService {

    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private TransactionMapper transactionMapper;
    private RecipientMapper recipientMapper;

    public PurchaseService(CustomerRepository customerRepository,
                           ProductRepository productRepository,
                           TransactionMapper transactionMapper,
                           RecipientMapper recipientMapper) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.transactionMapper = transactionMapper;
        this.recipientMapper = recipientMapper;
    }


    public boolean purchaseAProduct(UUID customerId, UUID productId, int quantity) {
        boolean SUCCESS = false;

        try {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found :" + customerId));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found :" + productId));

            checkQuantityEnough(product, quantity);

            Float amount = computeAmount(product, quantity);

            BankServiceWS.bankServiceWS().performTransaction(   transactionMapper.from(customer,
                                                                amount,
                                                                Transaction.Currency.EURO));

            updateProductQuantity(product, quantity);

            ShippingServiceWS.shippingServiceWS().registerShippingItem( productId,
                                                                        product.getWeight(),
                                                                        quantity,
                                                                        recipientMapper.from(customer));

            SUCCESS = true;

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return SUCCESS;
    }

    private void checkQuantityEnough(Product product, int quantity) {
        if(product.getQuantity() < quantity) {
            throw new RuntimeException("Not Enough Product Quantity available !");
        }
    }

    private float computeAmount(Product product, int quantity) {
        return product.getPrice()  * quantity ;
    }

    private void updateProductQuantity(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    private String createPurchaseValidationMessage(Customer customer, Product product, int quantity) {
        return String.format("%d produit(s) %s ont été commandé() par %s %s le %s", quantity, product.getName(), customer.getName(), customer.getFirstname(), OffsetDateTime.now());
    }
}
