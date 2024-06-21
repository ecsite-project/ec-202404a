package com.example.controller;

import com.example.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * ブックマーク機能の処理を制御するコントローラ.
 *
 * @author rui.inoue
 */
@RestController
@RequestMapping("/bookmark")
public class BookmarkApiController {

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping()
    public Map<String, Boolean> bookmark(Integer itemId){
        Map<String, Boolean> map = new HashMap<>();
        Integer userId = 1;
        bookmarkService.bookmark(userId, itemId);
        map.put("status", true);
        return map;
    }
}
