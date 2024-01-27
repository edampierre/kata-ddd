package ecommerce.business;

import ecommerce.mapper.RecipientMapper;
import ecommerce.mapper.TransactionMapper;
import ecommerce.model.Customer;
import ecommerce.model.PaymentInfos;
import ecommerce.model.Product;
import ecommerce.repository.CustomerRepository;
import ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import stubs.bankService.BankServiceWS;
import stubs.shippingService.ShippingServiceWS;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    CustomerRepository customerRepository;
    ProductRepository productRepository;
    TransactionMapper transactionMapper;
    RecipientMapper recipientMapper;

    PurchaseService purchaseService;

    @Mock
    BankServiceWS bankServiceWS;

    @Mock
    ShippingServiceWS shippingServiceWS;


    @BeforeEach
    public void setup() {
        customerRepository = new CustomerRepository();
        productRepository = new ProductRepository();
        transactionMapper = new TransactionMapper();
        recipientMapper = new RecipientMapper();

        purchaseService = new PurchaseService(customerRepository, productRepository, transactionMapper, recipientMapper);

    }

    @Test()
    public void shouldNotMakeTransactionNorShippingNorChangingProductQuantityWhenCustomerDoNotExist() {
        Customer customer = givenACustomerInDatabase();
        Product product = givenAProductInDatabase(10);

        UUID unknownCustomerId = UUID.randomUUID();

        purchaseService.purchaseAProduct(unknownCustomerId, product.getId(), 2);

        verify(bankServiceWS, times(0)).performTransaction(any());
        verify(shippingServiceWS, times(0)).registerShippingItem(any(), anyFloat(), anyInt(), any());

        Product currentProduct = productRepository.findById(product.getId()).get();
        assertEquals(currentProduct.getQuantity(), product.getQuantity());
    }

    @Test()
    public void shouldNotMakeTransactionNorShippingWhenProductDoNotExist() {
        Customer customer = givenACustomerInDatabase();

        UUID unknownProductId = UUID.randomUUID();

        purchaseService.purchaseAProduct(customer.getId(), unknownProductId, 2);

        verify(bankServiceWS, times(0)).performTransaction(any());
        verify(shippingServiceWS, times(0)).registerShippingItem(any(), anyFloat(), anyInt(), any());
    }

    @Test()
    public void shouldMakeTransactionAndShippingAndChangingProductQuantityWhenCustomerAndProductExists() {
        Customer customer = givenACustomerInDatabase();
        Product product = givenAProductInDatabase(10);

        purchaseService.purchaseAProduct(customer.getId(), product.getId(), 2);

        verify(bankServiceWS, times(0)).performTransaction(any());
        verify(shippingServiceWS, times(0)).registerShippingItem(any(), anyFloat(), anyInt(), any());

        Product currentProduct = productRepository.findById(product.getId()).get();
        assertEquals(currentProduct.getQuantity(), 8);
    }

    @Test()
    public void shouldNotMakeTransactionNorShippingNorChangingProductQuantityWhenQuantityNotEnough() {
        Customer customer = givenACustomerInDatabase();
        Product product = givenAProductInDatabase(10);

        purchaseService.purchaseAProduct(customer.getId(), product.getId(), 11);

        verify(bankServiceWS, times(0)).performTransaction(any());
        verify(shippingServiceWS, times(0)).registerShippingItem(any(), anyFloat(), anyInt(), any());

        Product currentProduct = productRepository.findById(product.getId()).get();
        assertEquals(currentProduct.getQuantity(), 10);
    }


    private Customer givenACustomerInDatabase() {
        PaymentInfos paymentInfos = PaymentInfos.builder()
                .cardNumber("1234567")
                .expirationDate("04/25")
                .name("Mr John Smith")
                .build();

        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("SMITH")
                .firstname("John")
                .email("john.smith@gmail.com")
                .paymentInfo(paymentInfos)
                .build();
        this.customerRepository.save(customer);
        return customer;
    }

    private Product givenAProductInDatabase(int quantity) {
        UUID productId = UUID.randomUUID();
        Product product = Product.builder().id(productId)
                .name("MY PRODUCT")
                .quantity(quantity)
                .weight(5.10F)
                .price(6.5F)
                .build();
        this.productRepository.save(product);
        return product;
    }

}