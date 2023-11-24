package com.example.dacn.modules.payos.service;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.payos.model.PaymentRequestBody;
import com.example.dacn.modules.payos.model.PaymentResponse;
import com.example.dacn.utils.Constant;
import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;


@Service
public class PayOSService
{
    private RestTemplate restTemplate;
    private String generateHmacSHA256(PaymentRequestBody requestBody) throws Exception {
        String amountStr = String.format("%.0f", requestBody.getAmount());
        String dataStr = "amount=" + amountStr
                + "&cancelUrl=" + requestBody.getCancelUrl()
                + "&description=" + requestBody.getDescription()
                + "&orderCode=" + requestBody.getOrderCode()
                + "&returnUrl=" + requestBody.getReturnUrl();
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(Constant.CHECKSUM_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256Hmac.init(secretKey);
        byte[] hmacBytes = sha256Hmac.doFinal(dataStr.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : hmacBytes) {
            hexStringBuilder.append(String.format("%02x", b));
        }
        return hexStringBuilder.toString();
    }
    public PaymentResponse getPaymentLink(PaymentRequestBody body) throws Exception {

            String signature = generateHmacSHA256(body);
            body.setSignature(signature);
            System.out.println("signature"+signature);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-client-id", Constant.X_CLIENT_KEY);
            headers.set("x-api-key", Constant.X_API_KEY);

            HttpEntity<PaymentRequestBody> request = new HttpEntity<>(body, headers);
            restTemplate=new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(Constant.GET_PAYMENT_LINK, HttpMethod.POST, request, String.class);

            // Handle the response as needed
//            HttpStatusCode statusCode = response.getStatusCode();
            String responseBody = response.getBody();

            PaymentResponse paymentResponse=new Gson().fromJson(responseBody,PaymentResponse.class);
            return paymentResponse;
    }
}
