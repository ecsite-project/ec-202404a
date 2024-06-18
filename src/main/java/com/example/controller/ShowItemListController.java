package com.example.controller;

import com.example.domain.Item;
import com.example.service.ShowItemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 商品情報を操作するコントローラ.
 *
 * @author krkrHotaru
 */

@Controller
@RequestMapping("/show-item-list")
public class ShowItemListController {

  @Autowired
  private ShowItemListService showItemListService;

  /**
   * 商品一覧画面を表示する.
   *
   * @param model Requestスコープの準備
   * @return 商品一覧画面
   */
  @GetMapping("")
  public String toItemList(Model model) {
    List<Item> itemList = showItemListService.showItemList();
    if (itemList.isEmpty()){
      model.addAttribute("notFound","商品が存在しません");
    }
    model.addAttribute("itemList",itemList);
    return "item-list";
  }
}
