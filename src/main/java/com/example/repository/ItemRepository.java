package com.example.repository;

import com.example.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品情報一覧を扱うリポジトリ.
 *
 * @author krkrHotaru
 */

@Repository
public class ItemRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    /** itemsテーブルにある商品情報をドメインに詰め替えるRowMapper */
    private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("name"));
        item.setDescription(rs.getString("description"));
        item.setPriceS(rs.getInt("price_s"));
        item.setPriceM(rs.getInt("price_m"));
        item.setPriceL(rs.getInt("price_l"));
        item.setImagePath(rs.getString("image_path"));
        return item;
    };

    /**
     * 削除フラグのたっていない商品の情報を開始位置から10件検索します.
     *
     * @return 削除フラグのたっていない商品情報のリスト(開始位置から10件)
     */
    public List<Item> findItemsFromOffsetLimit10(){
        String sql = """
                        SELECT id,name,description,price_s,price_m,price_l,image_path
                        FROM items
                        WHERE deleted = false
                        ORDER BY name ASC
                        LIMIT 10;
                        """;
        SqlParameterSource param = new MapSqlParameterSource();
        return template.query(sql,param,ITEM_ROW_MAPPER);
    }

    /**
     * idに基づく商品の検索.
     *
     * @param id 検索する商品id
     * @return 商品情報
     */
    public Item findById(Integer id){
        String sql = """
                SELECT
                    id, name, description, price_s, price_m, price_l, image_path
                FROM
                    items
                WHERE
                    id=:id
                ;
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
        return item;
    }
}
