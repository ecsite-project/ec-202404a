package com.example.service;

import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private UserDetailsService userDetailsService;

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
        return userRepository.findByUniqueUserAttribute(user) != null;
    }

    /**
     * メールアドレスの変更時の再認証処理.
     *
     * @param newEmail 新しいメールアドレス
     */
    public void updateEmail(String newEmail) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails newUserDetails = userDetailsService.loadUserByUsername(newEmail);
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                newUserDetails, currentAuth.getCredentials(), newUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
