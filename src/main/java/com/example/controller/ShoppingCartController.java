package com.example.controller;

import com.example.form.AddItemForm;
import com.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

  @GetMapping("")
  public String toShoppingCart() {
    return "shopping-cart";
  }

  @PostMapping("/add-item")
  public String addItem(AddItemForm form){
    Integer testUserId = 1;
    shoppingCartService.addItem(testUserId, form);
    return "shopping-cart";
  }
}
