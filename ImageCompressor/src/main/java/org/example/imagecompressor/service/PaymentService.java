package org.example.imagecompressor.service;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.Value;
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

        this.razorpayClient =
                new RazorpayClient(keyId, keySecret);

        this.keyId = keyId;
    }

    public CreateOrderResponse createOrder()
            throws RazorpayException {

        JSONObject options = new JSONObject();

        // ₹10 = 1000 paise
        options.put("amount", 1000);
        options.put("currency", "INR");
        options.put(
                "receipt",
                "img_" + System.currentTimeMillis()
        );

        Order order =
                razorpayClient.orders.create(options);

        return new CreateOrderResponse(
                order.get("id"),
                1000,
                "INR",
                keyId
        );
    }
}

