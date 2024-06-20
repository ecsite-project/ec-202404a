package com.example.service;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 注文確認処理のサービス.
 *
 * @author rui.inoue
 */
@Service
@Transactional
public class OrderConfirmService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    /**
     * 主キーから注文情報の取得.
     *
     * @param orderId 注文情報のid
     * @return 注文情報
     */
    public Order showConfirmOrder(Integer orderId){
        return orderRepository.findById(orderId);
    }

    public Order findAllOrderInfoByUserIdAndStatus(Integer userId, Integer status){
        return orderRepository.findAllOrderInfoByUserIdAndStatus(userId, status);
    }
}
