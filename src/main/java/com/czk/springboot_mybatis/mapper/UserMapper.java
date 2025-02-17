package com.czk.springboot_mybatis.mapper;

import com.czk.springboot_mybatis.mapper.sqlBuilder.UserSqlBuilder;
import com.czk.springboot_mybatis.pojo.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {
    @InsertProvider(type = UserSqlBuilder.class, method = "buildInsertUserSql")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertUser(User user);// 获取自增主键

    @SelectProvider(type = UserSqlBuilder.class, method = "buildSelectUserByUsernameSql")
    User selectUserByUsername(String username);
}
