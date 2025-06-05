package com.zyw.service.handler.processor.parser;

import com.zyw.entity.BatterySignal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电压信号解析
 */
public class VoltageAlarmRuleParser {

    // 内部类表示单个预警规则
    private static class AlarmRule {
        Double minValue;  // 最小值(可为null，表示无下限)
        Double maxValue;  // 最大值(可为null，表示无上限)
        int alarmLevel;   // 预警等级

        boolean matches(double value) {
            boolean minMatch = (minValue == null) || (value >= minValue);
            boolean maxMatch = (maxValue == null) || (value < maxValue);
            return minMatch && maxMatch;
        }
    }

    private final String ruleStr;
    private final List<AlarmRule> rules = new ArrayList<>();

    /**
     * 解析预警规则字符串
     */
    public VoltageAlarmRuleParser(String ruleStr) {
        this.ruleStr = ruleStr;
        // 按逗号分割各个规则
        String[] ruleItems = ruleStr.split(",");

        // 正则表达式匹配规则
        Pattern pattern = Pattern.compile("(\\d*\\.?\\d+)?<=?diff<?(\\d*\\.?\\d+)?:(\\-?\\d+)");

        for (String item : ruleItems) {
            item = item.trim();
            Matcher matcher = pattern.matcher(item);

            if (matcher.find()) {
                AlarmRule rule = new AlarmRule();

                // 解析最小值
                String minStr = matcher.group(1);
                rule.minValue = minStr == null ? null : Double.parseDouble(minStr);

                // 解析最大值
                String maxStr = matcher.group(2);
                rule.maxValue = maxStr == null ? null : Double.parseDouble(maxStr);

                // 解析预警等级
                rule.alarmLevel = Integer.parseInt(matcher.group(3));

                rules.add(rule);
            }
        }

        // 按优先级排序(范围小的先匹配)
        rules.sort((r1, r2) -> {
            double r1Min = r1.minValue != null ? r1.minValue : Double.MIN_VALUE;
            double r2Min = r2.minValue != null ? r2.minValue : Double.MIN_VALUE;
            return Double.compare(r2Min, r1Min); // 降序排列
        });
    }

    /**
     * 根据电池信号计算预警等级
     */
    public int calculateAlarmLevel(BatterySignal signal) {
        double voltageDiff = signal.getMx() - signal.getMi();
        if (ruleStr.startsWith("5")) {
            if (voltageDiff >= 5) {
                return 0;
            } else if (voltageDiff >= 3) {
                return 1;
            } else if (voltageDiff >= 1) {
                return 2;
            } else if (voltageDiff >= 0.6) {
                return 3;
            } else if (voltageDiff >= 0.2) {
                return 4;
            }
        } else if (ruleStr.startsWith("2")) {
            if (voltageDiff >= 2) {
                return 0;
            } else if (voltageDiff >= 1) {
                return 1;
            } else if (voltageDiff >= 0.7) {
                return 2;
            } else if (voltageDiff >= 0.4) {
                return 3;
            } else if (voltageDiff >= 0.2) {
                return 4;
            }
        }
        return -1;
    }

}
