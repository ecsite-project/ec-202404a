package com.example.controller;

import com.example.domain.Item;
import com.example.service.ShowItemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
