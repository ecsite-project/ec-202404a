package com.example.repository;

import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * usersテーブルを操作するリポジトリ.
 *
 * @author YusakuTerashima
 */
@Repository
public class UserRepository {
    /**
     * ユーザ情報登録用のRowMapper.
     */
    private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setZipcode(rs.getString("zipcode"));
        user.setPrefecture(rs.getString("prefecture"));
        user.setMunicipalities(rs.getString("municipalities"));
        user.setAddress(rs.getString("address"));
        user.setTelephone(rs.getString("telephone"));
        user.setAdminFlag(rs.getBoolean("admin_flag"));
        return user;
    };

    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * ユーザを登録する.
     *
     * @param user 登録するユーザ
     */
    public void insert(User user) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(user);
        String sql = "INSERT INTO users(name,email,password,zipcode,prefecture,municipalities,address,telephone)VALUES(:name,:email,:password,:zipcode,:prefecture,:municipalities,:address,:telephone);";
        template.update(sql, param);
    }

    /**
     * メールアドレスによる検索する.
     *
     * @param email 検索するメールアドレス
     * @return 一致したユーザ情報
     */
    public User findByEmail(String email){
        String sql = "SELECT id, name, email, password, zipcode, prefecture, municipalities, address, telephone, admin_flag FROM users WHERE email=:email";
        SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
        List<User> userListList = template.query(sql, param, USER_ROW_MAPPER);
        if (userListList.isEmpty()) {
            return null;
        }
        return userListList.get(0);
    }
}