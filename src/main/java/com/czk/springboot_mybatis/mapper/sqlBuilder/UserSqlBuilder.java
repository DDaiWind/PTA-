package com.czk.springboot_mybatis.mapper.sqlBuilder;

import com.czk.springboot_mybatis.pojo.User;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlBuilder {
    public String buildInsertUserSql(User user) {
        return new SQL() {{
            INSERT_INTO("user");
            VALUES("username", "#{username}");
            VALUES("password", "#{password}");
            VALUES("create_time", "NOW()"); // 使用数据库当前时间
            VALUES("status", "#{status}");
        }}.toString();
    }

    public String buildSelectUserByUsernameSql(String username) {
        return new SQL() {{
            SELECT("*");
            FROM("user");
            WHERE("username = #{username}");
        }}.toString();
    }
}
