package com.example.controller;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.User;
import com.example.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 注文履歴の処理の制御用コントローラ.
 *
 * @author rui.inoue
 */
@Controller
@RequestMapping("/order-history")
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    /**
     * 注文履歴の表示
     * @param model 注文情報のリストの格納
     * @return 注文履歴画面
     */
    @GetMapping()
    public String orderHistory(Model model, @AuthenticationPrincipal LoginUser loginUser){
//        Integer testUserId = 1;
//        List<Order> orderList = orderHistoryService.showOrderHistory(testUserId);

        User user = loginUser.getUser();
        List<Order> orderList = orderHistoryService.showOrderHistory(user.getId());
        model.addAttribute("orderList", orderList);

        return "order-history";
    }
}
