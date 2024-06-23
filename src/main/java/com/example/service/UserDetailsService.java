package com.example.service;

import com.example.controller.ShoppingCartController;
import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ログイン処理を行うサービス.
 * 認証を行うために，Spring SecurityのUserDetailsServiceインターフェースを実装している。
 *
 * @author YusakuTerashima
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartController shoppingCartController;

    @Autowired
    private OrderConfirmService orderConfirmService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private HttpSession session;

    /**
     * メールアドレスの変更時の再認証処理.
     *
     * @param newEmail 新しいメールアドレス
     */
    public void updateEmail(String newEmail) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

        // 新しいメールアドレスでユーザー情報を再取得
        UserDetails newUserDetails = loadUserByUsername(newEmail);

        // 新しい認証情報を作成
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                newUserDetails, currentAuth.getCredentials(), newUserDetails.getAuthorities());

        // 新しい認証情報をセッションに設定
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Not found mail address:" + email);
        }

        Order loginUserOrder = shoppingCartService.showOrder(user.getId());
        Integer tmpId = shoppingCartController.extractNumbers(session.getId());

        Order tmpOrder = shoppingCartService.showOrder(tmpId);
        if (tmpOrder != null) {
            if (loginUserOrder == null) {
                tmpOrder.setUserId(user.getId());
                orderConfirmService.updateUserId(tmpOrder);
            } else {
                for (OrderItem orderItem : tmpOrder.getOrderItemList()) {
                    orderItem.setOrderId(loginUserOrder.getId());
                    orderConfirmService.updateOrderItem(orderItem);
                }
                orderConfirmService.deleteById(tmpOrder.getId());
            }
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new LoginUser(user, authorities);
    }

}
