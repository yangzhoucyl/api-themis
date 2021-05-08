package cn.myiml.theims.core.enums;

import java.io.Serializable;

/**
 * @author yangzhou
 */
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = 1895783342500716100L;

    public static final String NOT_NULL = "参数{}不能为空";

    public static final String ILLEGAL = "参数{}不合法!";

    public static final String DEFAULT = "{} is Illegal parameter";
}
