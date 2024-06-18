package com.example.repository;

import com.example.domain.Item;
import com.example.domain.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * order_itemsテーブル操作用のリポジトリ.
 *
 * @author rui.inoue
 */
@Repository
public class OrderItemRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    /** order_itemsテーブルにある情報をドメインに詰め替えるRowMapper　*/
    private static final RowMapper<OrderItem> ORDER_ITEM_WITH_ITEM_ROW_MAPPER = (rs, i) -> {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getInt("ord_id"));
        orderItem.setItemId(rs.getInt("ord_item_id"));
        orderItem.setOrderId(rs.getInt("ord_order_id"));
        orderItem.setQuantity(rs.getInt("ord_quantity"));
        orderItem.setSize((Character) rs.getObject("ord_size"));
        return orderItem;
    };

    /**
     * 注文商品情報のinsert.
     *
     * @param orderItem 注文商品情報
     * @return idがセットされた注文商品情報
     */
    public OrderItem insert(OrderItem orderItem){
        SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
        String sql = """
                INSERT INTO order_items (
                    item_id,
                    order_id,
                    quantity,
                    size
                ) VALUES (
                    :itemId, :orderId, :quantity, :size
                );
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] keyColumnNames = {"id"};
        template.update(sql, param, keyHolder, keyColumnNames);
        orderItem.setId(keyHolder.getKey().intValue());
        return orderItem;
    }
}
