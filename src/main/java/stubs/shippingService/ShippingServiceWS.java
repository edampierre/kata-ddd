package stubs.shippingService;

import java.util.UUID;

public class ShippingServiceWS {

    static ShippingServiceWS shippingServiceWS = null;

    private ShippingServiceWS() {

    }

    public static ShippingServiceWS shippingServiceWS() {
        if(shippingServiceWS == null) {
            shippingServiceWS = new ShippingServiceWS();
        }
        return shippingServiceWS;
    }

    public void registerShippingItem(UUID itemId, float weight, int quantity, Recipient recipient) {
        System.out.println("Registering Shipping");

        System.out.println("Registering Shipping Done");
    }
}
