package com.example.controller;

import com.example.domain.Item;
import com.example.service.ShowItemDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品の詳細表示機能を操作するコントローラ.
 *
 * @author rui.inoue
 */
@Controller
@RequestMapping("/show-item-detail")
public class ShowItemDetailController {

  @Autowired
  private ShowItemDetailService showItemDetailService;

  /**
   * 商品の詳細画面の表示.
   *
   * @param id 商品のid
   * @param model 商品の格納
   * @return 商品詳細画面
   */
  @GetMapping()
  public String toItemDetail(Integer id, Model model) {
    Item item = showItemDetailService.showItem(id);
    model.addAttribute("item", item);
    return "item-detail";
  }
}
