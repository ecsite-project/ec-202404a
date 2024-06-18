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
     * 検索ワードと開始位置の如何に沿って、削除フラグのたっていない商品の情報を10件検索します.
     *
     * @return 検索結果に沿った商品情報10件のリスト
     */
    public List<Item> findItemsSearchByWordOrderBySortClipByOffset(String searchWord, String sortStr, Integer offset){
        String sql = """
                        SELECT id,name,description,price_s,price_m,price_l,image_path
                        FROM items
                        WHERE deleted = false
                        """;
        if (searchWord != null) {
            sql += " AND name LIKE :searchWord";
        }
        if (sortStr != null){
            sql += " ORDER BY " + sortStr;
        } else {
            sql += " ORDER BY name ASC ";
        }
        sql += " LIMIT 10 ";
        if (offset != null) {
            sql += " OFFSET :offset ";
        }
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("searchWord", "%" + searchWord + "%")
                .addValue("offset", offset);
        return template.query(sql,param,ITEM_ROW_MAPPER);
    }

}
