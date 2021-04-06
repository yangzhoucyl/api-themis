package cn.myiml.theims.core.rule.load;

import cn.myiml.theims.core.enums.PatternEnum;
import cn.myiml.theims.core.enums.ProcessTypeEnum;
import cn.myiml.theims.core.model.RuleConfigModel;
import cn.myiml.theims.core.model.VerifyRulesConfigModel;
import cn.myiml.theims.core.verify.annotation.VerifyField;
import cn.myiml.theims.core.verify.annotation.VerifyFields;
import com.google.common.base.Splitter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 通过注解方式加载校验规则
 * @author yangzhou
 */
public class AnnotationLoadRule implements LoadVerifyRule<VerifyRulesConfigModel>{

    private final int ROUTE_SPLIT_SIZE = 2;

    /**
     * 根据方法全限定名获取校验规则
     * @param routeName routeName 方法全限定名 必须是 类全限定名 + "&" + 方法名
     * @return ConcurrentHashMap<String,List<VerifyRulesConfigModel>> key为方法全限定名
     * @since 1.0.0
     */
    @Override
    public ConcurrentHashMap<String,List<VerifyRulesConfigModel>> loadRule(String routeName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Iterable<String> routes = Splitter.on('&').trimResults().omitEmptyStrings().split(routeName);
        List<String> routeNames = new ArrayList<>();
        routes.forEach(routeNames::add);
        if (routeNames.size() == ROUTE_SPLIT_SIZE ){
            String clazzRoute = routeNames.get(0);
            String methodName = routeNames.get(1);
            try {
                Class clazz = classLoader.loadClass(clazzRoute);
                Method method = clazz.getMethod(methodName);
                return this.loadRuleForObject(method);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ConcurrentHashMap<String, List<VerifyRulesConfigModel>> loadAllRules() {
        return new ConcurrentHashMap<>(0);
    }

    @Override
    public List<VerifyRulesConfigModel> loadAllRuleList() {
        return new ArrayList<>();
    }

    /**
     * 根据方法实例获取校验规则
     * @param loadObj 获取校验规则的方法实体
     * @return ConcurrentHashMap 具体的校验规则 key为方法全限定名, value为具体校验规则
     * @since 1.0.0
     */
    @Override
    public ConcurrentHashMap<String,List<VerifyRulesConfigModel>> loadRuleForObject(Object loadObj) {
        Method method = (Method) loadObj;
        Annotation[] annotations = method.getAnnotations();
        List<RuleConfigModel> configModel = loadRulesByAnnotationArrays(annotations);
        String routeName = method.getDeclaringClass().getName() + "&" + method.getName();
        List<VerifyRulesConfigModel> configs = createRulesConfigModel(configModel, routeName);
        ConcurrentHashMap<String,List<VerifyRulesConfigModel>> configMap = new ConcurrentHashMap<>();
        configMap.put(routeName, configs);
        return configMap;
    }

    /**
     * 创建最终校验规则集合
     * @param configModel 具体校验规则 由loadRulesByAnnotationArrays生成
     * @param routeName 方法路径全限定名
     * @return VerifyRulesConfigModel
     * @since 1.0.0
     */
    private List<VerifyRulesConfigModel> createRulesConfigModel(List<RuleConfigModel> configModel, String routeName) {
        List<VerifyRulesConfigModel> configs = new ArrayList<>();
        VerifyRulesConfigModel rulesConfigModel = new VerifyRulesConfigModel();
        rulesConfigModel.setRules(configModel);
        rulesConfigModel.setName(routeName);
        rulesConfigModel.setRoute(routeName);
        rulesConfigModel.setProcessType(ProcessTypeEnum.PROCESS_TYPE.name());
        rulesConfigModel.setTypeVal(PatternEnum.DEFAULT.name());
        configs.add(rulesConfigModel);
        return configs;
    }

    /**
     * 创建具体规则集合 根据注解数组
     * @param annotations  {@link VerifyFields,VerifyField}
     * @return RuleConfigModel arrays
     * @since 1.0.0
     */
    private List<RuleConfigModel> loadRulesByAnnotationArrays(Annotation[] annotations){
        List<VerifyField> verifyFieldList = new ArrayList<>();
        for (Annotation annotation : annotations) {
            if (annotation instanceof VerifyFields) {
                VerifyFields verifyFields = (VerifyFields) annotation;
                VerifyField[] verifyFieldArrays = verifyFields.fields();
                verifyFieldList.addAll(Arrays.stream(verifyFieldArrays).collect(Collectors.toList()));
            }
            if (annotation instanceof VerifyField){
                verifyFieldList.add((VerifyField) annotation);
            }
        }

        return createRulesByAnnotation(verifyFieldList);
    }

    /**
     * 创建校验规则 根据传入注解 {@link VerifyField}
     * @param verifyFieldList Annotation VerifyField Arrays
     * @return RuleConfigModel
     * @since 1.0.0
     */
    private List<RuleConfigModel> createRulesByAnnotation(List<VerifyField> verifyFieldList){
        List<RuleConfigModel> rules = new ArrayList<>();
        verifyFieldList.forEach(verifyField -> {
            RuleConfigModel ruleConfigModel = new RuleConfigModel();
            List<String[]> totalParams = new ArrayList<>();
            for (String param: verifyField.names()) {
                Iterable<String> lastSplits = Splitter.on('.').trimResults().omitEmptyStrings().split(param);
                List<String> params = new ArrayList<>();
                lastSplits.forEach(params::add);
                totalParams.add(params.toArray(new String[params.size()]));
            }
            ruleConfigModel.setParamArrays(totalParams);
            ruleConfigModel.setCheckRule(verifyField.rule());
            String paramNames = Arrays.stream(verifyField.names()).reduce((s, s2) -> s + "," + s2).get();
            ruleConfigModel.setParamName(paramNames);
            ruleConfigModel.setPattern(verifyField.pattern().name());
            rules.add(ruleConfigModel);
        });
        return rules;
    }
}
