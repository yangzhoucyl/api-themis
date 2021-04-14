package cn.myiml.theims.core.verify;

import cn.myiml.theims.core.model.VerifyRulesConfigModel;
import cn.myiml.theims.core.rule.load.AnnotationLoadRule;
import cn.myiml.theims.core.rule.load.LoadVerifyRule;
import cn.myiml.theims.core.verify.cache.VerifyRuleSingleton;

import java.util.List;
import java.util.Map;

/**
 * 注解验证方式
 * @author yangzhou
 */

public class AnnotationVerify extends AbstractParamVerify {


    @Override
    public void verify(Object args, String route) {
        if (args instanceof Map){
            LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
            List<VerifyRulesConfigModel> verifyRulesConfigModelList = VerifyRuleSingleton.getInstance().getVal(route, loadVerifyRule);
            requestParameterCheck((Map)args, verifyRulesConfigModelList);
        }
    }
}
