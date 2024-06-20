package com.example.controller;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.example.form.OrderForm;
import com.example.service.OrderConfirmService;
import com.example.service.OrderService;
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
    Order loginUserOrder = orderConfirmService.findAllOrderInfoByUserIdAndStatus(orderId, 0);

    if(session.getAttribute("notLoginUser").equals("true")){
      System.out.println("tmpUserId: " + (Integer) session.getAttribute("tmpUserId"));
      Order tmpOrder = orderConfirmService.showConfirmOrder((Integer) session.getAttribute("tmpUserId"));
      System.out.println(tmpOrder);

      if(loginUserOrder == null){
        User user = loginUser.getUser();
        tmpOrder.setUserId(user.getId());
      }else {
        for(OrderItem orderItem : tmpOrder.getOrderItemList()){
          orderItem.setOrderId(loginUserOrder.getId());
        }
        
      }
      orderService.updateUserId(tmpOrder);
    }

    model.addAttribute("order", loginUserOrder);
    return "order-confirm";
  }
}
