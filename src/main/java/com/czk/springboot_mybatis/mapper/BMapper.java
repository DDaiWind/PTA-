package com.czk.springboot_mybatis.mapper;

import com.czk.springboot_mybatis.mapper.sqlBuilder.ASqlBuilder;
import com.czk.springboot_mybatis.mapper.sqlBuilder.BSqlBuilder;
import com.czk.springboot_mybatis.pojo.BEntity;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BMapper {
    @InsertProvider(type = BSqlBuilder.class, method = "buildInsertSql")
    int insert(BEntity entity);

    @UpdateProvider(type = BSqlBuilder.class, method = "buildUpdateSql")
    int update(BEntity entity);

    @DeleteProvider(type = BSqlBuilder.class, method = "buildDeleteByCompositeKeySql")
    int deleteByCompositeKey(@Param("a") String a, @Param("c") String c);

    @DeleteProvider(type = BSqlBuilder.class, method = "buildDeleteByCSql")
    int deleteByC(@Param("c") String c);

    @InsertProvider(type = BSqlBuilder.class, method = "buildBatchInsertSql")
    int batchInsert(@Param("list") List<BEntity> entities);

    @SelectProvider(type = BSqlBuilder.class, method = "buildSelectAllSql")
    List<BEntity> selectAll();

    @SelectProvider(type = BSqlBuilder.class, method = "buildSumAaSql")
    Integer sumAa();

    @SelectProvider(type = BSqlBuilder.class, method = "buildSumBbSql")
    Integer sumBb();

    @SelectProvider(type = BSqlBuilder.class, method = "buildSumCcSql")
    Integer sumCc();

    @SelectProvider(type = BSqlBuilder.class, method = "buildSumDdSql")
    Integer sumDd();

    @SelectProvider(type = BSqlBuilder.class, method = "buildSumEeSql")
    Integer sumEe();

    @SelectProvider(type = BSqlBuilder.class, method = "buildSumCAaSql")
    Integer sumCAa();

    @SelectProvider(type = BSqlBuilder.class, method = "buildSumCBbSql")
    Integer sumCBb();

    @SelectProvider(type = BSqlBuilder.class, method = "buildSumCCcSql")
    Integer sumCCc();

    @SelectProvider(type = BSqlBuilder.class, method = "buildSumCDdSql")
    Integer sumCDd();

    @SelectProvider(type = BSqlBuilder.class, method = "buildSumCEeSql")
    Integer sumCEe();
}
