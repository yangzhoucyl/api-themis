package cn.myiml.theims.core.verify;

import cn.myiml.theims.core.BeanUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        try {
            Map<String, Object> args = getFieldsName(joinPoint);
            String TypeName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            String routeName = TypeName + "&" + methodName;
            AbstractParamVerify paramVerify = new AnnotationVerify();
            paramVerify.verify(args, routeName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
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
    private static Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        // 参数值
        Object[] args = joinPoint.getArgs();
        Class<?>[] classes = new Class[args.length];
        for (int k = 0; k < args.length; k++) {
            if (!args[k].getClass().isPrimitive()) {
                // 获取的是封装类型而不是基础类型
                String result = args[k].getClass().getName();
                Class s = map.get(result);
                classes[k] = s == null ? args[k].getClass() : s;
            }
        }
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        // 获取指定的方法,第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
        Method method = Class.forName(classType).getMethod(methodName, classes);
        // 参数名
        String[] parameterNames = pnd.getParameterNames(method);
        // 通过map封装参数和参数值
        Map<String, Object> paramMap = new HashMap<>(8);
        if (args != null && args.length == 1 && args[0] instanceof Object){
            return BeanUtils.objectToMap(args[0]);
        }
        for (int i = 0; i < Objects.requireNonNull(parameterNames).length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }
}
