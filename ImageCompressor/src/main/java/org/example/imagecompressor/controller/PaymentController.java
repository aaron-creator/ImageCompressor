package org.example.imagecompressor.controller;


import com.razorpay.RazorpayException;
import org.example.imagecompressor.dto.CreateOrderResponse;
import org.example.imagecompressor.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "${frontend.url}")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/order")
    public ResponseEntity<CreateOrderResponse> createDownloadOrder() throws RazorpayException {

        try{
            int amount = 1000;
            CreateOrderResponse createOrderResponse = paymentService.createOrder(amount);
            return ResponseEntity.ok(createOrderResponse);
        } catch (RazorpayException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
