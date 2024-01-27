package ecommerce.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Product {
    UUID id;
    float price;
    float weight;
    int quantity;
    String name;
}
