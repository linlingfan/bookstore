package com.fjnu.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.jdbc.TxQueryRunner;

import com.fjnu.bookstore.user.domain.User;

/**
 * User的持久层
 *
 * @author lenovo
 */
public class UserDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 添加用户
     *
     * @throws SQLException
     */
    public void add(User user) {

        try {
            String sql = "insert into user values(?,?,?,?,?,?)";
            qr.update(sql, user.getUid(), user.getUsername(), user.getPassword(),
                    user.getEmail(), user.getCode(), user.isState());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过用户名查询
     */
    public User findByUsername(String username) {
        try {
            String sql = "select * from user where username=?";
            return qr.query(sql, new BeanHandler<User>(User.class), username);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过邮箱查询
     */

    public User findByEmail(String email) {
        try {
            String sql = "select * from user where email=?";
            return qr.query(sql, new BeanHandler<User>(User.class), email);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 按激活码查询
     *
     * @param code
     * @return
     */
    public User findByCode(String code) {
        try {
            String sql = "select * from user where code=?";
            return qr.query(sql, new BeanHandler<User>(User.class), code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改指定用户的指定状态
     *
     * @param uid
     * @param state
     */
    public void updateState(String uid, boolean state) {
        try {
            String sql = "update user set state=? where uid=?";
            qr.update(sql, state, uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
