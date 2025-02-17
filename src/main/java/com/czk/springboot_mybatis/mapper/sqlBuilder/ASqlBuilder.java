package com.czk.springboot_mybatis.mapper.sqlBuilder;

import com.czk.springboot_mybatis.pojo.AEntity;
import org.apache.ibatis.jdbc.SQL;
import java.util.Map;

public class ASqlBuilder {
    public String buildInsertSql() {
        return new SQL() {{
            INSERT_INTO("a_entity");
            VALUES("a", "#{a}");
            VALUES("b", "#{b}");
            VALUES("c", "#{c}");
            VALUES("d", "#{d}");
            VALUES("e", "#{e}");
            VALUES("aa", "#{aa}");
            VALUES("bb", "#{bb}");
            VALUES("cc", "#{cc}");
            VALUES("dd", "#{dd}");
            VALUES("ee", "#{ee}");
        }}.toString();
    }

    public String buildUpdateSql(AEntity entity) {
        return new SQL() {{
            UPDATE("a_entity");
            SET("b = #{b}");
            SET("c = #{c}");
            SET("d = #{d}");
            SET("e = #{e}");
            SET("aa = #{aa}");
            SET("bb = #{bb}");
            SET("cc = #{cc}");
            SET("dd = #{dd}");
            SET("ee = #{ee}");
            WHERE("a = #{a}");
        }}.toString();
    }

    public String buildDeleteSql() {
        return new SQL() {{
            DELETE_FROM("a_entity");
            WHERE("a = #{a}");
        }}.toString();
    }

    public String buildSearchSql(Map<String, Object> params) {
        String keyword = (String) params.get("keyword");
        return new SQL() {{
            SELECT("*");
            FROM("a_entity");
            WHERE("a LIKE CONCAT('%', #{keyword}, '%')");
            ORDER_BY("a DESC");
        }}.toString();
    }

    public String buildSumAaSql() {
        return new SQL() {{
            SELECT("SUM(aa)");
            FROM("a_entity");
        }}.toString();
    }

    public String buildSumBbSql() {
        return new SQL() {{
            SELECT("SUM(bb)");
            FROM("a_entity");
        }}.toString();
    }

    public String buildSumCcSql() {
        return new SQL() {{
            SELECT("SUM(cc)");
            FROM("a_entity");
        }}.toString();
    }

    public String buildSumDdSql() {
        return new SQL() {{
            SELECT("SUM(dd)");
            FROM("a_entity");
        }}.toString();
    }

    public String buildSumEeSql() {
        return new SQL() {{
            SELECT("SUM(ee)");
            FROM("a_entity");
        }}.toString();
    }
}