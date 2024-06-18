package com.example.service;

import com.example.domain.Item;
import com.example.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品情報一覧を持ってくるサービス.
 *
 * @author krkrHotaru
 */

@Service
@Transactional
public class ShowItemListService {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * 全商品情報から10件持ってきます.
     *
     * @return 商品情報リスト
     */
    public List<Item> showItemList(){
        return itemRepository.findItemsFromOffsetLimit10();
    }
}
