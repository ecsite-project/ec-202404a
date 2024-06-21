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
    // ログイン中のユーザ情報持ってくる
    User user = loginUser.getUser();
    Integer loginUserId = user.getId();
    System.out.println("userID: " + loginUserId);

    //Integer testUserId = 1;
    // Order loginUserOrder = shoppingCartService.showOrder(testUserId);

    // 得たログイン情報が持つ未注文のオーダーがあれば持ってくる
    Order loginUserOrder = shoppingCartService.showOrder(loginUserId);
    System.out.println("loginUserOrder: " + loginUserOrder);

    if(session.getAttribute("notLoginUser").equals("true")){
      System.out.println("tmpUserId: " + (Integer) session.getAttribute("tmpUserId"));
      // ログイン前のカート情報持ってくる
      Order tmpOrder = shoppingCartService.showOrder((Integer) session.getAttribute("tmpUserId"));
      // Order tmpOrder = orderConfirmService.showConfirmOrder(1);
      System.out.println("tmpOrder: " + tmpOrder);

      if(loginUserOrder == null){ // ログイン中の未注文が無ければ、仮注文を正式な注文化
        System.out.println("if文入ってるか確認");
        tmpOrder.setUserId(loginUserId);
      }else { // ログイン中の未注文がある
        System.out.println("else文入ってるか確認");
        for(OrderItem orderItem : tmpOrder.getOrderItemList()){ // 仮注文の商品情報をログイン中の注文IDで書き換え
          orderItem.setOrderId(loginUserOrder.getId());
          System.out.println("else文のorderItem" + orderItem);

          orderService.updateOrderItem(orderItem);
        }
      }
      System.out.println("if文後tmpOrder: " + tmpOrder);
      orderService.updateUserId(tmpOrder);
    }

    // loginUserOrder = shoppingCartService.showOrder(loginUserId);
    //Order order = loginUserOrder;
    Order updateUserOrder = shoppingCartService.showOrder(loginUserId);
    model.addAttribute("order", updateUserOrder);
    return "order-confirm";
  }
}
