package cn.myiml.theims.core.verify.annotation;

import cn.myiml.theims.core.model.RuleConfigModel;

public class VerifyFieldTest {

    @VerifyField(names = {"configId", "paramName", "checkRule"})
    public void exampleMethod(RuleConfigModel ruleConfigModel){
        return;
    }




}
