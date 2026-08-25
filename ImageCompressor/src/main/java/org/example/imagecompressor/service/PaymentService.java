package org.example.imagecompressor.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.example.imagecompressor.dto.CreateOrderResponse;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.util.UUID;

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

    public CreateOrderResponse createOrder(int amountInPaise)
            throws RazorpayException {

        if (amountInPaise <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero"
            );
        }

        String receiptId =
                "img_" + UUID.randomUUID();

        JSONObject options =
                new JSONObject();

        options.put(
                "amount",
                amountInPaise
        );

        options.put(
                "currency",
                "INR"
        );

        options.put(
                "receipt",
                receiptId
        );

        Order order =
                razorpayClient
                        .orders
                        .create(options);

        String orderId =
                order.get("id");

        if (orderId == null ||
                orderId.isBlank()) {

            throw new IllegalStateException(
                    "Razorpay order creation failed"
            );
        }

        return new CreateOrderResponse(
                orderId,
                amountInPaise,
                "INR",
                keyId
        );
    }


}

