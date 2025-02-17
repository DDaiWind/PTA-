package com.czk.springboot_mybatis.mapper;

import com.czk.springboot_mybatis.mapper.sqlBuilder.CSqlBuilder;
import com.czk.springboot_mybatis.pojo.CEntity;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CMapper {
    @UpdateProvider(type = CSqlBuilder.class, method = "buildCalculateSql")
    void calculateStats();

    @SelectProvider(type = CSqlBuilder.class, method = "buildSearchByBSql")
    List<CEntity> searchByB(String keyword);

    @Select("SELECT * FROM c_entity")
    List<CEntity> selectAll();
}