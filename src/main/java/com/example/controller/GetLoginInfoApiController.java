package com.example.controller;

import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * ログイン者情報をレスポンスするapiコントローラ.
 *
 * @author rui.inoue
 */
@RestController
@RequestMapping("/get-user")
public class GetLoginInfoApiController {

    @Autowired
    private UserService userService;

    /**
     * ユーザ情報を渡す.
     *
     * @param userId ユーザの主キー
     * @return ユーザ情報
     */
    @PostMapping("/user-info")
    public Map<String, User> getLoginInfo(Integer userId){
        Map<String, User> map = new HashMap<>();
        User user = userService.getUser(userId);
        map.put("user", user);
        return map;
    }
}
