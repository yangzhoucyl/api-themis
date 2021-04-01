package cn.myiml.theims.core.verify.annotation;

import cn.myiml.theims.core.enums.PatternEnum;

import java.lang.annotation.*;

import static cn.myiml.theims.core.enums.PatternEnum.DEFAULT;

/**
 * 校验参数
 * @author yangzhou
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface VerifyField {

    /**
     * 参数名
     * @return String
     */
    String[] names() default {};

    /**
     * 校验规则
     * @return String
     */
    String rule() default "";

    PatternEnum pattern() default DEFAULT;

}
