package com.example.controller;

import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.User;
import com.example.service.ShoppingCartService;
import com.example.service.ShowItemListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品情報をレスポンスするapiコントローラ.
 *
 * @author krkrHotaru
 */

@RestController
@RequestMapping("/get-item-info")
public class GetItemInfoApiController {

    @Autowired
    private ShowItemListService showItemListService;

    @Autowired
    private HttpSession session;

    @Autowired
    private ShoppingCartController shoppingCartController;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ResponseBody
    @PostMapping("")
    public Map<String,List<String>> autoComp(String input){
        Map<String,List<String>> results = new HashMap<>();
        List<String> itemNameList = new ArrayList<>();

        List<Item> itemList = showItemListService.showItemsSearchedBySWord(input,0,0);
        for (Item item : itemList){
            itemNameList.add(item.getName());
        }

        results.put("itemNameList",itemNameList);
        return results;
    }

    @GetMapping("/count-item")
    public Map<String, Integer> countItem(@AuthenticationPrincipal LoginUser loginUser){
        Map<String, Integer> map = new HashMap<>();

        User user = new User();
        if(loginUser == null){
            Integer sessionId = shoppingCartController.extractNumbers(session.getId());
            user.setId(sessionId);
        }else {
            user = loginUser.getUser();
        }
        Order order = shoppingCartService.showOrder(user.getId());
        Integer countOrder = order.getOrderItemList().size();

        map.put("countOrder", countOrder);
        return map;
    }
}
