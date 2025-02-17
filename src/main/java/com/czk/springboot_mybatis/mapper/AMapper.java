package com.czk.springboot_mybatis.mapper;

import com.czk.springboot_mybatis.mapper.sqlBuilder.ASqlBuilder;
import com.czk.springboot_mybatis.pojo.AEntity;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AMapper {
    @InsertProvider(type = ASqlBuilder.class, method = "buildInsertSql")
    int insert(AEntity entity);

    @UpdateProvider(type = ASqlBuilder.class, method = "buildUpdateSql")
    int update(AEntity entity);

    @DeleteProvider(type = ASqlBuilder.class, method = "buildDeleteSql")
    int delete(@Param("a") String a);

    @SelectProvider(type = ASqlBuilder.class, method = "buildSearchSql")
    List<AEntity> searchByA(@Param("keyword") String keyword);

    @Insert({"<script>",
            "INSERT INTO a_entity (a, b, c, d, e, aa, bb, cc, dd, ee) VALUES ",
            "<foreach collection='list' item='item' separator=','>",
            "(#{item.a}, #{item.b}, #{item.c}, #{item.d}, #{item.e}, ",
            "#{item.aa}, #{item.bb}, #{item.cc}, #{item.dd}, #{item.ee})",
            "</foreach>",
            "</script>"})
    int batchInsert(@Param("list") List<AEntity> entities);

    @Select("SELECT * FROM a_entity")
    List<AEntity> selectAll();

    @SelectProvider(type = ASqlBuilder.class, method = "buildSumAaSql")
    Integer sumAa();

    @SelectProvider(type = ASqlBuilder.class, method = "buildSumBbSql")
    Integer sumBb();

    @SelectProvider(type = ASqlBuilder.class, method = "buildSumCcSql")
    Integer sumCc();

    @SelectProvider(type = ASqlBuilder.class, method = "buildSumDdSql")
    Integer sumDd();

    @SelectProvider(type = ASqlBuilder.class, method = "buildSumEeSql")
    Integer sumEe();
}