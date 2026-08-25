package org.example.imagecompressor.controller;


import com.razorpay.RazorpayException;
import org.example.imagecompressor.dto.CreateOrderResponse;
import org.example.imagecompressor.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/order")
    public ResponseEntity<CreateOrderResponse> createOrder() throws RazorpayException {

        try{
            CreateOrderResponse createOrderResponse = paymentService.createOrder();
            return ResponseEntity.ok(createOrderResponse);
        } catch (RazorpayException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
