package com.example.controller;

import com.example.domain.CreditCard;
import com.example.form.OrderForm;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 注文処理の制御のコントローラ.
 *
 * @author rui.inoue
 */
@Controller
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderConfirmController orderConfirmController;

  @Autowired
  private OrderService orderService;

  /**
   * 注文.
   *
   * @param orderForm 入力情報
   * @param result バリデーションチェック
   * @param model 注文画面に渡すmodel
   * @return 注文完了画面
   */
  @PostMapping("")
  public String order(@Validated OrderForm orderForm, BindingResult result, Model model) {
    if(orderForm.getPaymentMethod().equals(2)) {
      CreditCard creditCard = new CreditCard();
      creditCard.setUser_id(1);
      creditCard.setOrder_number("12345678912345");
      if (orderForm.getCardNumber() != null) {
        creditCard.setCard_number(orderForm.getCardNumber().toString());
      }
      creditCard.setCard_exp_year(orderForm.getCardExpYear());
      creditCard.setCard_exp_month(orderForm.getCardExpMonth());
      creditCard.setCard_name(orderForm.getCardName());
      creditCard.setCard_cvv(orderForm.getCardCvv());

      boolean checkCreditCard = orderService.checkCreditCard(creditCard, orderForm.getOrderId());

      if (orderForm.getPaymentMethod().equals(2) && !checkCreditCard) {
        result.rejectValue("cardNumber", "", "カードが使えません");
      }
    }

    if(result.hasErrors()){
      return orderConfirmController.showConfirmOrder(orderForm.getOrderId(), model, orderForm);
    }

    orderService.order(orderForm);

    return "order-complete";
  }
}
