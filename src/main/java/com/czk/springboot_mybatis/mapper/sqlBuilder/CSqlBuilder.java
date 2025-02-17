package com.czk.springboot_mybatis.mapper.sqlBuilder;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public class CSqlBuilder {
    public String buildCalculateSql() {
        return new SQL()
                .INSERT_INTO("c_entity")
                .INTO_COLUMNS("b", "aaS", "bbS", "ccS", "ddS",  "aaA", "bbA", "ccA", "aaSS", "aaC")
                .INTO_VALUES(
                        "b_entity.b",
                        "SUM(b_entity.correct_aa) / SUM(a_entity.aa)",
                        "SUM(b_entity.correct_bb) / SUM(a_entity.bb)",
                        "SUM(b_entity.correct_cc) / SUM(a_entity.cc)",
                        "SUM(b_entity.correct_dd) / SUM(a_entity.dd)",
                        "SUM(b_entity.correct_aa) / COUNT(b_entity.aa)",
                        "SUM(b_entity.correct_bb) / COUNT(b_entity.bb)",
                        "SUM(b_entity.correct_cc) / COUNT(b_entity.cc)",
                        "SUM(b_entity.correct_ee) / SUM(a_entity.ee)",
                        "(SUM(b_entity.correct_aa) + SUM(b_entity.correct_ee)) / (SUM(a_entity.aa) + SUM(a_entity.ee))"
                )
                .FROM("b_entity b_entity")
                .JOIN("a_entity a_entity ON b_entity.a = a_entity.a")
                .GROUP_BY("b_entity.b")
                .toString();
    }

    public String buildSearchByBSql(String keyword) {
        return new SQL()
                .SELECT("*")
                .FROM("c")
                .WHERE("b LIKE CONCAT('%', #{keyword}, '%')")
                .toString();
    }

    public String buildSelectAllSql() {
        return new SQL().SELECT("*").FROM("c").toString();
    }
}
