package com.example.service;

import com.example.domain.CreditCard;
import com.example.domain.Order;
import com.example.form.OrderForm;
import com.example.repository.OrderRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 注文の処理のサービス.
 *
 * @author rui.inoue
 */
@Service
@Transactional
public class OrderService {

    @Value("${CreditCardCheckApi: ''}")
    private String creditCardCheckApi;

    @Autowired
    private OrderRepository orderRepository;

    public boolean checkCreditCard(CreditCard card, Integer orderId){
        Order order = orderRepository.findById(orderId);
        if(order == null){
            return false;
        }

        card.setAmount(order.getCalcTotalPrice() + order.getTax());
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.postForObject(creditCardCheckApi, card, JsonNode.class);

        if(jsonNode.findValue("status").equals("success")){
            return true;
        }
        return false;
    }

    /**
     * 注文の処理.
     *
     * @param form 注文の入力情報
     */
    public void order(OrderForm form){
        Order order = orderRepository.findById(form.getOrderId());
        if(form.getPaymentMethod().equals(1)){
            order.setStatus(1);
        }else {
            order.setStatus(2);
        }
        order.setDestinationName(form.getDestinationName());
        order.setDestinationEmail(form.getDestinationEmail());
        order.setDestinationZipcode(form.getDestinationZipcode());
        order.setDestinationPrefecture(form.getDestinationPrefecture());
        order.setDestinationMunicipalities(form.getDestinationMunicipalities());
        order.setDestinationAddress(form.getDestinationAddress());
        order.setDestinationTel(form.getDestinationTel());
        LocalDate deliveryDate = LocalDate.parse(form.getDeliveryDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime deliveryTime = LocalTime.of(Integer.parseInt(form.getDeliveryTime()), 0, 0);
        order.setDeliveryTime(LocalDateTime.of(deliveryDate, deliveryTime));
        order.setPaymentMethod(form.getPaymentMethod());

        orderRepository.update(order);
    }
}
