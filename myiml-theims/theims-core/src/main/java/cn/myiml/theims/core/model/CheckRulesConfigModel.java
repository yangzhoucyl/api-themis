package cn.myiml.theims.core.model;

import com.google.common.base.Splitter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 校验规则配置
 * @author YangZhou
 */
@Data
public class CheckRulesConfigModel {

    private Long configId;

    private String domain;

    /**
     * 校验路径
     */
    private String route;

    /**
     * 配置名
     */
    private String name;

    /**
     * 启用状态
     */
    private Integer status;

    private String processType;

    private String typeVal;

    List<RuleConfigModel> rules;

    public void setRules(List<RuleConfigModel> rules) {
        rules.forEach(rule -> {
            List<String[]> totalParams = new ArrayList<>();
            Iterable<String> firstSplit = Splitter.on(',').trimResults().omitEmptyStrings().split(rule.getParamName());;
            for (String param: firstSplit) {
                Iterable<String> lastSplits = Splitter.on('.').trimResults().omitEmptyStrings().split(param);
                List<String> params = new ArrayList<>();
                lastSplits.forEach(params::add);
                totalParams.add(params.toArray(new String[params.size()]));
            }
            rule.setParamArrays(totalParams);
        });
        this.rules = rules;
    }
}
