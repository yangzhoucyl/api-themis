package cn.myiml.theims.core.rule.load;

import cn.myiml.theims.core.model.VerifyRulesConfigModel;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class AnnotationLoadRuleTest {



    @Before
    public void before(){

    }


    @Test
    public void loadRule() {
        String routeName = "cn.myiml.theims.core.verify.annotation.VerifyFieldTest&annotationVerifyFieldsTest";
        LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
        ConcurrentHashMap<String, List<VerifyRulesConfigModel>> rules =  loadVerifyRule.loadRule(routeName);
        assertEquals(1, rules.size());
    }

    @Test
    public void loadAllRules() {
        LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
        assertEquals(0,loadVerifyRule.loadAllRules().size());
    }

    @Test
    public void loadAllRuleList() {
        LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
        assertEquals(0, loadVerifyRule.loadAllRuleList().size());
    }

    @Test
    public void loadRuleForObject() {
    }
}