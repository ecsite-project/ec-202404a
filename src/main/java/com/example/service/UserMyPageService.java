package com.example.service;

import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザ情報をマイページに持ってくるサービス.
 *
 * @author krkrHotaru
 */

@Service
@Transactional
public class UserMyPageService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 受け取ったユーザ情報で登録情報を上書きする.
     *
     * @param user ユーザ情報
     */
    public void updateUserInfo(User user){
        userRepository.update(user);
    }

    /**
     * 受け取ったユーザ以外にemail重複がないか確認.
     *
     * @param user 重複確認をしたいユーザの情報
     */
    public boolean isExistDuplicateEmailExceptUser(User user){
        return userRepository.findByUserInfo(user) != null;
    }
}
