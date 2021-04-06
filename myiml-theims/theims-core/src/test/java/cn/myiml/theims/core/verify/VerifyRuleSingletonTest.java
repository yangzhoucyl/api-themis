package cn.myiml.theims.core.verify;

import cn.myiml.theims.core.model.VerifyRulesConfigModel;
import cn.myiml.theims.core.rule.load.AnnotationLoadRule;
import cn.myiml.theims.core.rule.load.LoadVerifyRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class VerifyRuleSingletonTest {

    VerifyRuleSingleton verifyRuleSingleton;

    @Before
    public void before(){
        verifyRuleSingleton = VerifyRuleSingleton.getInstance();
    }

    @After
    public void after(){
        verifyRuleSingleton = null;
    }

    @Test
    public void getInstance() {
        assertNotNull(verifyRuleSingleton);
    }

    @Test
    public void getRules() {
        assertEquals(0, verifyRuleSingleton.getRules().size());
    }

    @Test
    public void setRules() {
        LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
        verifyRuleSingleton.setRules(loadVerifyRule);
        assertEquals(0, verifyRuleSingleton.getRules().size());
    }

    @Test
    public void getVal() {
        String routeName = "cn.myiml.theims.core.verify.annotation.VerifyFieldTest&annotationVerifyFieldsTest";
        LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
        List<VerifyRulesConfigModel> rulesConfigModels =  verifyRuleSingleton.getVal(routeName, loadVerifyRule);
        assertEquals(1, rulesConfigModels.size());
    }

    @Test
    public void addCache(){
        String routeName = "cn.myiml.theims.core.verify.annotation.VerifyFieldTest&annotationVerifyFieldsTest";
        LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
        List<VerifyRulesConfigModel> rulesConfigModels =  verifyRuleSingleton.addCache(routeName, loadVerifyRule);
        assertEquals(1, rulesConfigModels.size());
    }
}