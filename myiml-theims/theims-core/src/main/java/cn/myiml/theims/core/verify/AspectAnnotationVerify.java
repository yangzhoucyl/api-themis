package cn.myiml.theims.core.verify;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.myiml.theims.core.BeanUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 参数验证注解拦截
 * @author yangzhou
 */
@Aspect
@Component
@Log4j
public class AspectAnnotationVerify {

    @Pointcut("@annotation(cn.myiml.theims.core.verify.annotation.VerifyFields) || @annotation(cn.myiml.theims.core.verify.annotation.VerifyField)) ")
    public void annotationVerify(){
    }

    @Around(value = "annotationVerify()")
    public Object annotationVerifyExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        String TypeName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String routeName = TypeName + "&" + methodName;
        AbstractParamVerify paramVerify = new AnnotationVerify();
        paramVerify.verify(joinPoint, routeName);
        return joinPoint.proceed();
    }




}
