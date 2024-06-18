package com.example.controller;

import com.example.domain.Item;
import com.example.service.ShowItemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
  public String toItemList(String searchWord,Model model,Integer sortType) {
    List<Item> itemList = new ArrayList<>();
    try {
      itemList = showItemListService.showItemReFuzSearch(searchWord,sortType);
      if (itemList.isEmpty()){
        model.addAttribute("notFound","検索結果：0件");
        itemList = showItemListService.showItemList(sortType);
      }
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
      model.addAttribute("notFound","存在しない並び替えが選択されました。名前順で表示しています。");
      itemList = showItemListService.showItemList(null);
    }
    model.addAttribute("searchWord",searchWord);
    model.addAttribute("itemList",itemList);
    return "item-list";
  }
}
