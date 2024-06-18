package com.example.service;

import com.example.common.SortType;
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
     * あいまい検索結果の商品情報を10件持ってきます.
     *
     * @return 商品情報リスト
     */
    public List<Item> showItemReFuzSearch(String searchWord, Integer sortType) {
        String sortStr = null;
        if (sortType != null) {
            sortStr = SortType.of(sortType).getValue();
        }
        return itemRepository.findItemsSearchByWordOrderBySortClipByOffset(searchWord, sortStr, null);
    }

    /**
     * 全商品情報から10件持ってきます.
     *
     * @return 商品情報リスト
     */
    public List<Item> showItemList(Integer sortType) {
        String sortStr = null;
        if (sortType != null) {
            sortStr = SortType.of(sortType).getValue();
        }
        return itemRepository.findItemsSearchByWordOrderBySortClipByOffset(null, sortStr, null);
    }
}
