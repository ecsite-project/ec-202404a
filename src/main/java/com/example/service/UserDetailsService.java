package com.example.service;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("Not found mail address:" + email);
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new LoginUser(user, authorities);
    }

    /**
     * 注文情報のIDの更新.
     *
     * @param order 注文情報
     */
    public void updateUserId(Order order){
        orderRepository.update(order);
    }

    /**
     * 商品情報のIDの更新.
     *
     * @param orderItem 注文情報にある商品
     */
    public void updateOrderItem(OrderItem orderItem){
        orderItemRepository.updateOrderItem(orderItem);
    }

    /**
     * IDに基づいたオーダーを削除.
     *
     * @param id 削除するオーダーID
     */
    public void deleteById(Integer id){
        orderRepository.deleteById(id);
    }
}
