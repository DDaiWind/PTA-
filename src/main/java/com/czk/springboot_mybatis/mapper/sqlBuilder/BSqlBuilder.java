package com.czk.springboot_mybatis.mapper.sqlBuilder;

import com.czk.springboot_mybatis.pojo.BEntity;
import org.apache.ibatis.jdbc.SQL;
import java.util.Map;

public class BSqlBuilder {
    public String buildInsertSql(BEntity entity) {
        return new SQL() {{
            INSERT_INTO("b_entity");
            VALUES("a", "#{a}");
            VALUES("c", "#{c}");
            VALUES("b", "#{b}");
            VALUES("d", "#{d}");
            VALUES("e", "#{e}");
            VALUES("aa", "#{aa}");
            VALUES("bb", "#{bb}");
            VALUES("cc", "#{cc}");
            VALUES("dd", "#{dd}");
            VALUES("ee", "#{ee}");

            addCorrectValue(this, "correct_aa", "aa");
            addCorrectValue(this, "correct_bb", "bb");
            addCorrectValue(this, "correct_cc", "cc");
            addCorrectValue(this, "correct_dd", "dd");
            addCorrectValue(this, "correct_ee", "ee");
        }}.toString();
    }

    private void addCorrectValue(SQL sql, String correctColumn,
                                 String bField) {
        String expr = String.format(
                "COALESCE(#{%s}, (%s AND (SELECT %s FROM a_entity WHERE a = #{a}))",
                correctColumn, bField, bField
        );
        sql.VALUES(correctColumn, expr);
    }

    public String buildUpdateSql(BEntity entity) {
        return new SQL() {{
            UPDATE("b_entity");
            SET("b = #{b}");
            SET("d = #{d}");
            SET("e = #{e}");
            SET("aa = #{aa}");
            SET("bb = #{bb}");
            SET("cc = #{cc}");
            SET("dd = #{dd}");
            SET("ee = #{ee}");

            addCorrectSet(this, "correct_aa", "aa");
            addCorrectSet(this, "correct_bb", "bb");
            addCorrectSet(this, "correct_cc", "cc");
            addCorrectSet(this, "correct_dd", "dd");
            addCorrectSet(this, "correct_ee", "ee");

            WHERE("a = #{a}");
            WHERE("c = #{c}");
        }}.toString();
    }

    private void addCorrectSet(SQL sql, String correctColumn,
                               String bField) {
        String expr = String.format(
                "%s = COALESCE(#{%s}, %s AND (SELECT %s FROM a_entity WHERE a = #{a})))",
                correctColumn, correctColumn, bField, bField
        );
        sql.SET(expr);
    }

    public String buildDeleteByCompositeKeySql() {
        return new SQL() {{
            DELETE_FROM("b_entity");
            WHERE("a = #{a}");
            WHERE("c = #{c}");
        }}.toString();
    }

    public String buildDeleteByCSql() {
        return new SQL() {{
            DELETE_FROM("b_entity");
            WHERE("c = #{c}");
        }}.toString();
    }

    public String buildBatchInsertSql(Map<String, Object> params) {
        return new SQL() {{
            INSERT_INTO("b_entity");
            VALUES("a", "#{item.a}");
            VALUES("c", "#{item.c}");
            VALUES("b", "#{item.b}");
            VALUES("d", "#{item.d}");
            VALUES("e", "#{item.e}");
            VALUES("aa", "#{item.aa}");
            VALUES("bb", "#{item.bb}");
            VALUES("cc", "#{item.cc}");
            VALUES("dd", "#{item.dd}");
            VALUES("ee", "#{item.ee}");

            addBatchCorrectValue(this, "correct_aa", "aa");
            addBatchCorrectValue(this, "correct_bb", "bb");
            addBatchCorrectValue(this, "correct_cc", "cc");
            addBatchCorrectValue(this, "correct_dd", "dd");
            addBatchCorrectValue(this, "correct_ee", "ee");
        }}.toString() +
                " ON DUPLICATE KEY UPDATE " +
                "b = VALUES(b), d = VALUES(d), e = VALUES(e), " +
                "aa = VALUES(aa), bb = VALUES(bb), cc = VALUES(cc), " +
                "dd = VALUES(dd), ee = VALUES(ee)";
    }

    private void addBatchCorrectValue(SQL sql, String correctColumn, String bField) {
        String expr = String.format(
                "COALESCE(#{item.correct%s}, (#{item.%s} OR (SELECT %s FROM a_entity WHERE a = #{item.a})))",
                correctColumn, bField, bField
        );
        sql.VALUES(correctColumn, expr);
    }

    public String buildSelectAllSql() {
        return new SQL() {{
            SELECT("*");
            FROM("b_entity");
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

    public String buildSumCAaSql() {
        return new SQL() {{
            SELECT("SUM(correct_aa)");
            FROM("a_entity");
        }}.toString();
    }

    public String buildSumCBbSql() {
        return new SQL() {{
            SELECT("SUM(correct_bb)");
            FROM("a_entity");
        }}.toString();
    }

    public String buildSumCCcSql() {
        return new SQL() {{
            SELECT("SUM(correct_cc)");
            FROM("a_entity");
        }}.toString();
    }

    public String buildSumCDdSql() {
        return new SQL() {{
            SELECT("SUM(correct_dd)");
            FROM("a_entity");
        }}.toString();
    }

    public String buildSumCEeSql() {
        return new SQL() {{
            SELECT("SUM(correct_ee)");
            FROM("a_entity");
        }}.toString();
    }

}