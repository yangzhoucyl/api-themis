package cn.myiml.theims.core.verify;

import cn.hutool.core.bean.BeanUtil;
import cn.myiml.theims.core.model.RuleConfigModel;
import cn.myiml.theims.core.model.VerifyRulesConfigModel;
import cn.myiml.theims.core.rule.load.AnnotationLoadRule;
import cn.myiml.theims.core.rule.load.LoadVerifyRule;
import cn.myiml.theims.core.verify.cache.VerifyRuleSingleton;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 注解验证方式
 *
 * @author yangzhou
 */

public class AnnotationVerify extends AbstractParamVerify {


    @Override
    public void verify(Object args, String route) {
        ProceedingJoinPoint joinPoint = (ProceedingJoinPoint) args;
        // 获取方法类型
        Map<String, Object> params = getFieldsName(joinPoint);
        LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
        List<VerifyRulesConfigModel> verifyRulesConfigModelList = VerifyRuleSingleton.getInstance().getVal(route, joinPoint,loadVerifyRule);
        if (params.size() == 0){
            Object val = joinPoint.getArgs()[0];
            if (val instanceof Iterable){
                requestParameterCheck((JSON) JSON.toJSON(val), verifyRulesConfigModelList);
            }
            RuleConfigModel ruleConfig =  verifyRulesConfigModelList.get(0).getRules().get(0);
            patternVerified(val,ruleConfig, ruleConfig.getMessage());
        }else {
            requestParameterCheck((JSON) JSON.toJSON(params), verifyRulesConfigModelList);

        }

    }

    @SneakyThrows
    private Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Signature sig = joinPoint.getSignature();
        MethodSignature signature = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        signature = (MethodSignature) sig;
        Object target = joinPoint.getTarget();
        Method currentMethod = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
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
