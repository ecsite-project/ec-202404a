package com.example.controller;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.example.form.OrderForm;
import com.example.service.OrderConfirmService;
import com.example.service.OrderService;
import com.example.service.ShoppingCartService;
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

  /**
   * 注文確認画面の表示.
   *
   * @param orderId 注文情報の主キー
   * @param model 注文情報の格納
   * @return 注文確認画面
   */
  @GetMapping("")
  public String showConfirmOrder(Integer orderId, Model model, OrderForm form, HttpSession session, @AuthenticationPrincipal LoginUser loginUser) {
    System.out.println("orderId:" + orderId);
    User user = loginUser.getUser();
    System.out.println("userID: " + user.getId());

    Integer testUserId = 1;
    Order loginUserOrder = shoppingCartService.showOrder(testUserId);
    System.out.println("loginUserOrder: " + loginUserOrder);

    if(session.getAttribute("notLoginUser").equals("true")){
      System.out.println("tmpUserId: " + (Integer) session.getAttribute("tmpUserId"));
      Order tmpOrder = shoppingCartService.showOrder((Integer) session.getAttribute("tmpUserId"));
      // Order tmpOrder = orderConfirmService.showConfirmOrder(1);
      System.out.println("tmpOrder: " + tmpOrder);

      if(loginUserOrder == null){
        System.out.println("if文入ってるか確認");
        tmpOrder.setUserId(user.getId());
      }else {
        System.out.println("else文入ってるか確認");
        for(OrderItem orderItem : tmpOrder.getOrderItemList()){
          orderItem.setOrderId(loginUserOrder.getId());
          System.out.println("else文のorderItem" + orderItem);
        }
      }
      System.out.println("if文後tmpOrder: " + tmpOrder);
      orderService.updateUserId(tmpOrder);
    }

    // loginUserOrder = shoppingCartService.showOrder(user.getId());
    Order order = loginUserOrder;
    model.addAttribute("order", order);
    return "order-confirm";
  }
}
