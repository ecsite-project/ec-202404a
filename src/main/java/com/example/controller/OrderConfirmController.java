package com.example.controller;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.OrderForm;
import com.example.service.OrderConfirmService;
import com.example.service.OrderService;
import com.example.service.ShoppingCartService;
import com.example.service.UserDetailsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

  @Autowired
  private OrderService orderService;

  @Autowired
  private ShoppingCartService shoppingCartService;

  @Autowired
  private HttpSession session;

  @Autowired
  private UserDetailsService userDetailsService;

  /**
   * 注文確認画面の表示.
   *
   * @param orderId 注文情報の主キー
   * @param model 注文情報の格納
   * @return 注文確認画面
   */
  @GetMapping("")
  public String showConfirmOrder(Integer orderId, Model model, OrderForm form, HttpSession session, @AuthenticationPrincipal LoginUser loginUser) {
    // ログイン中のユーザ情報持ってくる
    User user = loginUser.getUser();
    Integer loginUserId = user.getId();

    Order updateUserOrder = shoppingCartService.showOrder(loginUserId);
    model.addAttribute("order", updateUserOrder);
    return "order-confirm";
  }
}
