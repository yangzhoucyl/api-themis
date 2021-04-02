package cn.myiml.theims.core.verify.annotation;

import java.lang.annotation.*;

/**
 * 校验参数集合
 * @author yangzhou
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface VerifyFields{

    VerifyField[] fields();

}
