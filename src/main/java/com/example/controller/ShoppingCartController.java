package com.example.controller;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.form.AddItemForm;
import com.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * カートの処理の制御用のコントローラ.
 *
 * @author rui.inoue
 */
@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

  @Autowired
  private ShoppingCartService shoppingCartService;

  /**
   * カート画面の表示.
   *
   * @param model 注文情報の格納
   * @return カート画面
   */
  @GetMapping("")
  public String toShoppingCart(Model model) {
    Integer testUserId = 1;
    Order order = shoppingCartService.showOrder(testUserId);
    if(order == null){
      model.addAttribute("noOrder", "カートに商品は1つもありません");
    }else {
      model.addAttribute("orderItemList", order.getOrderItemList());
      System.out.println(order.getOrderItemList());
      model.addAttribute("S", "S");
      model.addAttribute("M", "M");
    }
    return "shopping-cart";
  }

  /**
   * 商品をカートに追加.
   *
   * @param form 入力情報
   * @return カートの画面へリダイレクト
   */
  @PostMapping("/add-item")
  public String addItem(AddItemForm form){
    Integer testUserId = 1;
    shoppingCartService.addItem(testUserId, form);
    return "redirect:/shopping-cart";
  }
}
