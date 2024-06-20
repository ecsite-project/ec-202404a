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
     * 重複しないEmail情報で一意のユーザ情報を持ってくる.
     *
     * @param email メールアドレス
     * @return ユーザ情報
     */
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 受け取ったユーザ情報で登録情報を上書きする.
     *
     * @param user ユーザ情報
     */
    public void updateUserInfo(User user,Integer id){
        userRepository.update(user,id);
    }
}
