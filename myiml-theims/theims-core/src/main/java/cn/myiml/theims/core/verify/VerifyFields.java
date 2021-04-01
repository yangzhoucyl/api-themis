package cn.myiml.theims.core.verify;

import java.lang.annotation.*;

/**
 * 校验参数集合
 * @author yangzhou
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface VerifyFields {

    VerifyField[] fields() default {};

}
