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
}
