package cn.myiml.theims.core.verify;

import cn.myiml.theims.core.enums.PatternEnum;
import cn.myiml.theims.core.model.RuleConfigModel;
import cn.myiml.theims.core.model.VerifyRulesConfigModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author yangzhou
 */
public abstract class AbstractParamVerify {


    /**
     * 校验抽象方法
     * @param args 参数
     * @param route 匹配路径
     */
    public abstract void verify(Object args, String route);

    /**
     * 对象校验
     * @param requestParameters 请求参数
     * @param rules 请求对象
     */
    protected static void requestParameterCheck(Map<String, Object> requestParameters, List<VerifyRulesConfigModel> rules) {
        rules.forEach(
                rule -> {
                    // 区分参数类型校验
                    if (!ObjectUtils.isEmpty(rule.getProcessType()) && !ObjectUtils.isEmpty(requestParameters.getOrDefault(rule.getProcessType(), ""))) {
                        if (!ObjectUtils.isEmpty(rule.getTypeVal()) && rule.getTypeVal().equals(requestParameters.getOrDefault(rule.getProcessType(), ""))) {
                            configRuleCheck(rule, requestParameters);
                        }
                        // 默认校验 不区分参数类型
                    } else if (!ObjectUtils.isEmpty(rule.getProcessType()) && PatternEnum.DEFAULT.name().equals(rule.getTypeVal())
                            && ObjectUtils.isEmpty(requestParameters.getOrDefault(rule.getProcessType(), ""))) {
                        configRuleCheck(rule, requestParameters);
                    }
                }
        );
    }

    /**
     * list校验
     * @param requestParameters 请求参数
     * @param rules 校验规则
     */
    protected static void requestParameterCheck(JSON requestParameters, List<VerifyRulesConfigModel> rules) {
        rules.forEach(
                rule -> {
                    // 默认校验 不区分参数类型
                    if (!ObjectUtils.isEmpty(rule.getProcessType()) && PatternEnum.DEFAULT.name().equals(rule.getTypeVal())) {
                        configRuleCheck(rule, requestParameters);
                    }
                }
        );
    }

    /**
     * list校验
     * @param requestParameters 请求参数
     * @param rules 校验规则
     */
    protected static void parameterVerifyDefault(Map<String, Object> requestParameters, List<VerifyRulesConfigModel> rules) {
        rules.forEach(
                rule -> {
                    // 默认校验 不区分参数类型
                    if (!ObjectUtils.isEmpty(rule.getProcessType()) && PatternEnum.DEFAULT.name().equals(rule.getTypeVal())) {
                        configRuleCheck(rule, requestParameters);
                    }
                }
        );
    }

    protected static void configRuleCheck(VerifyRulesConfigModel rule, Map<String, Object> requestParameters) {
        if (!ObjectUtils.isEmpty(rule.getProcessType())) {
            if (rule.getRules().size() > 0) {
                for (RuleConfigModel checkRule : rule.getRules()) {
                    useRuleCheckParameter(checkRule, requestParameters);
                }
            }
        }
    }

    protected static void configRuleCheck(VerifyRulesConfigModel rule, JSON requestParameters) {
        if (!ObjectUtils.isEmpty(rule.getProcessType())) {
            if (rule.getRules().size() > 0) {
                for (RuleConfigModel checkRule : rule.getRules()) {
                    useRuleCheckParameter(checkRule, requestParameters);
                }
            }
        }
    }

    protected static void useRuleCheckParameter(RuleConfigModel ruleConfig, Map<String, Object> requestParameters) {
        // 复制一个参数出来作为校验 防止校验过程中修改原方法入参
        JSON copyParameter = (JSON) JSON.toJSON(requestParameters);
        if (!ObjectUtils.isEmpty(ruleConfig.getPattern())) {
            ruleConfig.getParamArrays().forEach(paramNames -> traversalParameterFromRule(copyParameter, paramNames, 0, ruleConfig));
        }
    }
    protected static void useRuleCheckParameter(RuleConfigModel ruleConfig,JSON requestParameters) {
        // 复制一个参数出来作为校验 防止校验过程中修改原方法入参
        JSON copyParameter = (JSON) JSON.toJSON(requestParameters);
        if (!ObjectUtils.isEmpty(ruleConfig.getPattern())) {
            ruleConfig.getParamArrays().forEach(paramNames -> traversalParameterFromRule(copyParameter, paramNames, 0, ruleConfig));
        }
    }

    /**
     * 根据规则遍历参数
     * @param parameters 原参数
     * @param paramNames 校验参数名
     * @param paramIndex 当前参数深度
     * @param ruleConfig 参数校验规则
     */
    protected static void traversalParameterFromRule(JSON parameters, String[] paramNames, int paramIndex , RuleConfigModel ruleConfig) {
        String errorMessage = ObjectUtils.isEmpty(ruleConfig.getMessage()) ?  paramNames[paramIndex] + " is Illegal parameter": ruleConfig.getMessage();
        List<Object> verifiedParameters = new ArrayList<>(2);
        // 获取需要校验的参数
        tobeVerifiedParamsList(parameters, paramNames, paramIndex, verifiedParameters);
        // 验证
        verifiedParameters.forEach(val -> patternVerified(val, ruleConfig, errorMessage));

    }

    /**
     * 根据规则遍历参数
     * @param parameters 原参数
     * @param paramNames 校验参数名
     * @param paramIndex 当前参数深度
     * @param checkValue 待校验参数集合
     */
    protected static void tobeVerifiedParamsList(JSON parameters, String[] paramNames, int paramIndex, List<Object> checkValue) {
        if (parameters instanceof JSONObject){
            // 当前对象获取下级字段类型是JsonObject
            if (((JSONObject) parameters).get(paramNames[paramIndex]) instanceof JSON){
                // 强转 递归调用
                tobeVerifiedParamsList((JSON) ((JSONObject) parameters).get(paramNames[paramIndex++]), paramNames, paramIndex, checkValue);
            }else {
                // 不是object就是字符串 加入待校验集合
                checkValue.add(((JSONObject) parameters).get(paramNames[paramIndex]) != null? ((JSONObject) parameters).get(paramNames[paramIndex]) : "");
            }
            // 如果是json数组
        }else if (parameters instanceof JSONArray){
            // 循环遍历进行递归调用
            for (int i = 0; i < ((JSONArray) parameters).size(); i++) {
                // 如果当前数组下标元素类型为JSON
                if (((JSONArray) parameters).get(i) instanceof JSON){
                    // 递归进入下一层参数
                    tobeVerifiedParamsList((JSON) ((JSONArray) parameters).get(i), paramNames, paramIndex, checkValue);
                }else {
                    checkValue.add(((JSONArray) parameters).get(i) != null? ((JSONArray) parameters).get(i):"");
                }
            }
        }else {
            checkValue.add(parameters != null? parameters.toString() : "");
        }
    }


    protected static void patternCheck(JSONObject parameters, String[] paramNames, int paramIndex, RuleConfigModel ruleConfig, String errorMessage) {
        if (PatternEnum.DEFAULT.equals(PatternEnum.valueOf(ruleConfig.getPattern()))) {
            Assert.isTrue(!ObjectUtils.isEmpty(parameters.get(paramNames[paramIndex])), errorMessage);
        } else if (PatternEnum.REGULAR.equals(PatternEnum.valueOf(ruleConfig.getPattern()))) {
            Pattern pattern = Pattern.compile(ruleConfig.getCheckRule());
            Assert.isTrue(!ObjectUtils.isEmpty(parameters.get(paramNames[paramIndex])), errorMessage);
            Assert.isTrue(pattern.matcher(parameters.get(paramNames[paramIndex]).toString()).matches(),
                    errorMessage);
        }
    }
    protected static void patternVerified(Object value, RuleConfigModel ruleConfig, String errorMessage) {
        if (PatternEnum.DEFAULT.equals(PatternEnum.valueOf(ruleConfig.getPattern()))) {
            Assert.isTrue(!ObjectUtils.isEmpty(value), errorMessage);
        } else if (PatternEnum.REGULAR.equals(PatternEnum.valueOf(ruleConfig.getPattern()))) {
            Pattern pattern = Pattern.compile(ruleConfig.getCheckRule());
            Assert.isTrue(!ObjectUtils.isEmpty(value), errorMessage);
            Assert.isTrue(pattern.matcher(value.toString()).matches(), errorMessage);
        }
    }
}
