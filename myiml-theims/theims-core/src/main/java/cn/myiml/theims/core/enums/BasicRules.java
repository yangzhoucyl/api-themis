package cn.myiml.theims.core.enums;

import java.util.Locale;

/**
 * 基础规则每局类
 * @author yangzhou
 */

public class BasicRules {
    /**
     * 数字校验
     */
    public static final String NUMBER_REG = "^[-\\+]?[\\d]*$";
    /**
     * 中文校验
     */
    public static final String CHINESE_REG = "^[\\u4e00-\\u9fa5]{0,}$";

    /**
     * 由26个英文字母组成的字符串
     */
    public static final String ENGLISH_REG = "^[A-Za-z]+$";

    /**
     * 英文大写
     */
    public static final String ENGLISH_UPPER_REG = "^[A-Z]+$";

    /**
     * 英文小写
     */
    public static final String ENGLISH_LOWER_REG = "^[a-z]+$";

    /**
     * 电子邮件
     */
    public static final String EMAIL_REG = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 日期格式
     */
    public static final String DATE_REG = "^\\d{4}-\\d{1,2}-\\d{1,2}";

}
