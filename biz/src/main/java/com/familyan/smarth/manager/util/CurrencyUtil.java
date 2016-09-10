package com.familyan.smarth.manager.util;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;

/**
 * 货币处理 工具类
 */
public class CurrencyUtil {
    private static final Logger logger = Logger.getLogger(CurrencyUtil.class);
    /**
     * 格式化
     *
     * @param pattern 格式 如： #.##
     * @param num     金额（单位：分）
     */
    public static String format(String pattern, Integer num) {
        return new DecimalFormat(pattern).format(num / 100.00);
    }

    /**
     * 数字格式化
     *
     * @param num 金额（单位：分）
     */
    public static String format(Integer num) {
        return format("#.##", num);
    }

    /**
     * 格式化数字字符串
     * @param num
     * @return
     */
    public static String format(String num){
        Integer result = 0;
        try{
            result = Integer.parseInt(num);
        }catch (NumberFormatException e){
            logger.info("解析字符串异常,"+num);
            result = 0;
        }
        return format(result);
    }

    public static void main(String[] args) {
        Integer num = 9900;
//        Integer num = 9900;
        System.out.println(CurrencyUtil.format("#.##", num));
        System.out.println(CurrencyUtil.format("#.0#", num));
        System.out.println(CurrencyUtil.format("#.00", num));
        System.out.println("----------------------------------");
        System.out.println(CurrencyUtil.format(num));

    }
}
