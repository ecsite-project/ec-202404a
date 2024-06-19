package com.example.controller;

import com.example.domain.Order;
import com.example.form.OrderForm;
import com.example.service.OrderConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 注文確認処理を制御するコントローラ.
 *
 * @author rui.inoue
 */
@Controller
@RequestMapping("/order-confirm")
public class OrderConfirmController {

  @Autowired
  private OrderConfirmService orderConfirmService;

  /**
   * 注文確認画面の表示.
   *
   * @param orderId 注文情報の主キー
   * @param model 注文情報の格納
   * @return 注文確認画面
   */
  @GetMapping("")
  public String showConfirmOrder(Integer orderId, Model model, OrderForm form) {
    Order order = orderConfirmService.showConfirmOrder(orderId);
    model.addAttribute("order", order);
    return "order-confirm";
  }
}
