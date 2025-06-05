package com.zyw.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RuleMapper {

    @Select("select rule_name from warning_rule where rule_id = #{warnId} limit 1")
    String getWarnNameByWarnId(Integer warnId);

    @Select("select rule_condition from warning_rule " +
            "where rule_id = #{warnId} and battery_type = #{batteryType}")
    String getRule(Integer warnId,String batteryType);

}
