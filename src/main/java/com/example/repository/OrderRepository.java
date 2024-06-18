package com.example.repository;

import com.example.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ordersテーブルを操作するリポジトリ
 *
 * @author rui.inoue
 */
@Repository
public class OrderRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    /** ordersテーブルにある情報をドメインに詰め替えるRowMapper */
    @Autowired
    private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        order.setStatus(rs.getInt("status"));
        order.setTotalPrice(rs.getInt("total_price"));
        order.setDestinationName(rs.getString("destination_name"));
        order.setDestinationEmail(rs.getString("destination_email"));
        order.setDestinationZipcode(rs.getString("destination_zipcode"));
        order.setDestinationPrefecture(rs.getString("destination_prefecture"));
        order.setDestinationMunicipalities(rs.getString("destination_municipalities"));
        order.setDestinationAddress(rs.getString("destination_address"));
        order.setDestinationTel(rs.getString("destination_tel"));
        order.setOrderTime(rs.getTimestamp("order_time").toLocalDateTime());
        order.setDeliveryTime(rs.getTimestamp("delivery_time").toLocalDateTime());
        order.setPaymentMethod(rs.getInt("payment_method"));
        return order;
    };

    /**
     * ユーザidとstatusに基づく注文情報の検索.
     *
     * @param userId ユーザid
     * @param status 注文状況
     * @return 注文情報
     */
    public Order findByUserIdAndStatus(Integer userId, Integer status){
        String sql = """
                SELECT
                    id, user_id, status, total_price, destination_name, destination_email, destination_zipcode, destination_prefecture, destination_municipalities, destination_address, destination_tel, order_time, delivery_time, payment_method
                FROM
                    orders
                WHERE
                    user_id=:userId AND status=:status
                ;
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
        List<Order> orderList = template.query(sql, param, ORDER_ROW_MAPPER);
        if(orderList.isEmpty()){
            return null;
        }
        return orderList.get(0);
    }

    /**
     * 注文情報のinsert.
     *
     * @param order 注文情報
     * @return idをセットした注文情報
     */
    public Order insert(Order order){
        SqlParameterSource param = new BeanPropertySqlParameterSource(order);
        String sql = """
                INSERT INTO orders (
                    user_id,
                    status,
                    total_price,
                    destination_name,
                    destination_email,
                    destination_zipcode,
                    destination_prefecture,
                    destination_municipalities,
                    destination_address,
                    destination_tel,
                    order_time,
                    delivery_time,
                    payment_method
                ) VALUES (
                    :userId, :status, :totalPrice, :destinationName, :destinationEmail, :destinationZipcode, :destinationPrefecture, :destinationMunicipalities, :destinationAddress, :destinationTel, :orderTime, :deliveryTime, :paymentMethod
                );
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] keyColumnNames = {"id"};
        template.update(sql, param, keyHolder, keyColumnNames);
        order.setId(keyHolder.getKey().intValue());
        return order;
    }
}
