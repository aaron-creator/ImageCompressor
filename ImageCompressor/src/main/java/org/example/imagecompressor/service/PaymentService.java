package org.example.imagecompressor.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.example.imagecompressor.dto.CreateOrderResponse;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key.id}")
    private String keyId;

    public PaymentService(
            @Value("${razorpay.key.id}") String keyId,
            @Value("${razorpay.key.secret}") String keySecret
    ) throws RazorpayException {

        System.out.println("Razorpay key id: " + keyId);
        System.out.println(
                "Razorpay secret available: " +
                        (keySecret != null && !keySecret.isBlank())
        );

        this.razorpayClient =
                new RazorpayClient(keyId, keySecret);

        this.keyId = keyId;
    }

//    public CreateOrderResponse createOrder()
//            throws RazorpayException {
//        System.out.println("Razor pay key "+keyId);
//
//        JSONObject options = new JSONObject();
//
//        // ₹10 = 1000 paise
//        options.put("amount", 1000);
//        options.put("currency", "INR");
//        options.put(
//                "receipt",
//                "img_" + System.currentTimeMillis()
//        );
//
//        Order order =
//                razorpayClient.orders.create(options);
//
//        return new CreateOrderResponse(
//                order.get("id"),
//                1000,
//                "INR",
//                keyId
//        );
//    }


    public CreateOrderResponse createOrder()
            throws RazorpayException {

        JSONObject options = new JSONObject();

        int amount = 1000;

        options.put("amount", amount);
        options.put("currency", "INR");
        options.put(
                "receipt",
                "img_" + System.currentTimeMillis()
        );

        Order order =
                razorpayClient.orders.create(options);

        String orderId =
                order.get("id");

        System.out.println(
                "Razorpay Order ID: " + orderId
        );

        return new CreateOrderResponse(
                orderId,
                amount,
                "INR",
                keyId
        );
    }

}

