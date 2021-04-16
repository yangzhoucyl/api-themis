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
        // 获取方法类型
       Object args = getFieldsName(joinPoint);
        if (((Map)args).size() == 0){
            args = joinPoint.getArgs()[0];
        }
        String TypeName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String routeName = TypeName + "&" + methodName;
        AbstractParamVerify paramVerify = new AnnotationVerify();
        paramVerify.verify(args, routeName);
        return joinPoint.proceed();
    }


    private static HashMap<String, Class> map = new HashMap<String, Class>() {
        {
            put("java.lang.Integer", int.class);
            put("java.lang.Double", double.class);
            put("java.lang.Float", float.class);
            put("java.lang.Long", long.class);
            put("java.lang.Short", short.class);
            put("java.lang.Boolean", boolean.class);
            put("java.lang.Char", char.class);
        }
    };


    @SneakyThrows
    private Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = joinPoint.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        // 获取指定的方法,为了防止有重载的现象，需要传入参数的类型
        // 参数名
        String[] parameterNames = pnd.getParameterNames(currentMethod);
        // 通过map封装参数和参数值
        Map<String, Object> paramMap = new HashMap<>(8);
        if (args.length == 1 && args[0] != null && isValidObject(args[0])){
            return BeanUtil.beanToMap(args[0]);
        }
        for (int i = 0; i < Objects.requireNonNull(parameterNames).length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }

    private boolean isValidObject(Object arg){
        Class<?> clazzType = map.get(arg.getClass());
        if (clazzType != null){
            if (clazzType.isPrimitive()){
                return false;
            }
        }
        if (BeanUtil.isBean(arg.getClass())){
            return true;
        }
        if (arg instanceof Map){
            return true;
        }
        if (arg instanceof Iterable){
            return true;
        }
        return false;
    }

}
