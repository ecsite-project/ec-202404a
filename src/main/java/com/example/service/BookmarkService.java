package com.example.service;

import com.example.domain.Bookmark;
import com.example.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ブックマーク機能を処理するサービス.
 *
 * @author rui.inoue
 */
@Service
@Transactional
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    /**
     * ブックマーク情報の切り替え.
     *
     * @param userId ユーザのid
     * @param itemId 商品のid
     */
    public void bookmark(Integer userId, Integer itemId){
        Bookmark bookmark = bookmarkRepository.findByUserIdAndItemId(userId, itemId);
        if(bookmark == null){
            bookmark = new Bookmark();
            bookmark.setUserId(userId);
            bookmark.setItemId(itemId);
            bookmarkRepository.insert(bookmark);
        }else {
            bookmarkRepository.delete(bookmark.getId());
        }
    }
}
