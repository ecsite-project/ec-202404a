package com.example.service;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private OrderConfirmService orderConfirmService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Not found mail address:" + email);
        }

        Integer loginUserId = user.getId();

        Order loginUserOrder = shoppingCartService.showOrder(loginUserId);

        Order tmpOrder = shoppingCartService.showOrder((Integer) session.getAttribute("tmpUserId"));
        if (tmpOrder != null) {
            if (loginUserOrder == null) {
                tmpOrder.setUserId(loginUserId);
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
