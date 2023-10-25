package com.sunny.pms.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 提取字符串里的数字
     *
     * @param source "love23next234csdn3423javaeye"
     * @param split  "-"
     * @return "----23----234----3423-------"
     */
    public static String extractNumber(String source, String split) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(source);
        return m.replaceAll(split).trim();
    }

    /**
     * 去除‘制表符’，‘回车符’，‘换行符’，‘换页符’，‘垂直制表符’,两端空格
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        str = str.trim();
        String pattern = "\\t|\\r|\\n|\\f|\\v";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.replaceAll("");
    }

    /**
     * 判断两个字符串是否相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean StringEqual(String str1, String str2) {
        if (StringUtils.isBlank(str1)) {
            return StringUtils.isBlank(str2);
        }

        return str1.equals(str2);
    }

    /**
     * 将开头第一个字母转为大写
     *
     * @param str
     * @return
     */
    public static String doFirstUpperCase(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    /**
     * 将开头两个字母转为大写
     *
     * @param str
     * @return
     */
    public static String doFTwoUpperCase(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        } else {
            return str.substring(0, 2).toUpperCase() + str.substring(2);
        }
    }

    /**
     * 将第二个字母转为大写
     *
     * @param str
     * @return
     */
    public static String doSecondUpperCase(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        } else {
            return str.substring(0, 1) + str.substring(1, 2).toUpperCase() + str.substring(2);
        }
    }

    public static Boolean notEmpty(String str) {
        return str != null && !str.trim().equals("");
    }

    public static String convernLikeCondition(String condition) {
        return "%" + condition + "%";
    }

    public static Object NullToEmpty(Object str) {
        return str == null ? "" : str;
    }

    public static String randomstr() {
        long l = new Date().getTime();
        return l + "_" + new DecimalFormat("#").format(Math.random() * 10 * l);
    }

    public static String poUrlFormat(String host, String port, String uri) {
        return "http://" + host + ":" + port + uri;
    }

    public static String moneyToChinese(BigDecimal i_money) {
        if (i_money.equals(BigDecimal.ZERO)) {
            return "零圆整";
        }
        if (i_money.doubleValue() >= 100000000 || i_money.doubleValue() < 0.01) {
            return "";
        }
        i_money = i_money.setScale(2, RoundingMode.HALF_UP);
        String numStr = i_money.toString();
        int pointPos = numStr.indexOf('.');
        String s_int = null; //整数部分
        String s_point = null; //小数部分
        if (pointPos >= 0) {
            s_int = numStr.substring(0, pointPos);
            s_point = numStr.substring(pointPos + 1);
        } else {
            s_int = numStr;
        }
        StringBuilder sb = new StringBuilder();
        if (!"0".equals(s_int)) {
            int groupCount = (int) Math.ceil(s_int.length() / 4.0);
            for (int group = 0; group < groupCount; group++) {
                boolean zeroFlag = true;
                boolean noZeroFlag = false;
                int start = (s_int.length() % 4 == 0 ? 0 : (s_int.length() % 4 - 4)) + 4 * group;
                for (int i = 0; i < 4; i++) {
                    if (i + start >= 0) {
                        int value = s_int.charAt(i + start) - '0';
                        if (value > 0) {
                            sb.append(CN_UPPER_NUMBER[value]);
                            if (i < 3) {
                                sb.append(CN_UPPER_UNIT[i]);
                            }
                            zeroFlag = true;
                            noZeroFlag = true;
                        } else if (zeroFlag) {
                            sb.append('零');
                            zeroFlag = false;
                        }
                    }
                }
                if (sb.charAt(sb.length() - 1) == '零') {
                    sb.deleteCharAt(sb.length() - 1);
                }
                if (noZeroFlag || groupCount - group == 1) {
                    sb.append(CN_GROUP[groupCount - group - 1]);
                }
            }
        }
        if (s_point == null || "00".equals(s_point)) {
            sb.append('整');
        } else {
            int j = s_point.charAt(0) - '0';
            int f = s_point.charAt(1) - '0';
            if (j > 0) {
                sb.append(CN_UPPER_NUMBER[j]).append('角');
                if (f != 0) {
                    sb.append(CN_UPPER_NUMBER[f]).append('分');
                }
            } else if ("0".equals(s_int)) {
                sb.append(CN_UPPER_NUMBER[f]).append('分');
            } else {
                sb.append('零').append(CN_UPPER_NUMBER[f]).append('分');
            }
        }
        return sb.toString();
    }


    private static final char[] CN_UPPER_NUMBER = "零壹贰叁肆伍陆柒捌玖".toCharArray();
    private static final char[] CN_UPPER_UNIT = "仟佰拾".toCharArray();
    private static final char[] CN_GROUP = "圆万亿".toCharArray();
}
