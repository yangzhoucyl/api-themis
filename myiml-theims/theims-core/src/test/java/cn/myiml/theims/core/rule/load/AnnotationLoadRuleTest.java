package cn.myiml.theims.core.rule.load;

import cn.hutool.core.lang.Assert;
import cn.myiml.theims.core.model.VerifyRulesConfigModel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class AnnotationLoadRuleTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();


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
    public void loadRuleWhenClassIsNoExistentThrowClassNotFundException(){
        exception.expect(ClassNotFoundException.class);
        exception.expectMessage("class:cn.myiml.theims.core.verify.annotation.VerifyFieldTest1 not fund");
        String routeName = "cn.myiml.theims.core.verify.annotation.VerifyFieldTest1&annotationVerifyFieldsTest";
        LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
        loadVerifyRule.loadRule(routeName);
    }

    @Test
    public void loadRuleWhenMethodIsNoExistentReturnResultSizeIsZero(){
        String routeName = "cn.myiml.theims.core.verify.annotation.VerifyFieldTest&annotationVerifyFieldsTest1";
        LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
        ConcurrentHashMap<String,List<VerifyRulesConfigModel>> result =  loadVerifyRule.loadRule(routeName);
        assertEquals(0, result.size());
    }

    @Test
    public void loadRuleWhenMethodNameIsNotInRulerNull(){
        String routeName = "cn.myiml.theims.core.verify.annotation.VerifyFieldTest.annotationVerifyFieldsTest";
        LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
        assertNull(loadVerifyRule.loadRule(routeName));
    }

    @Test
    public void loadRuleWhenMethodAnnotationIsVerifyField(){
        String routeName = "cn.myiml.theims.core.verify.annotation.VerifyFieldTest&annotationVerifyFieldTest";
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
    public void loadRuleForObject() throws ClassNotFoundException, NoSuchMethodException {
        LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
        String routeName = "cn.myiml.theims.core.verify.annotation.VerifyFieldTest";
        String methodName = "annotationVerifyFieldsTest";
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Class clazz = classLoader.loadClass(routeName);
        Method method = clazz.getMethod(methodName);
        assertEquals(1, loadVerifyRule.loadRuleForObject(method).size());
    }
    @Test
    public void loadRuleForObjectWhenParamTypeNotIsMethod() {
        LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule = new AnnotationLoadRule();
        assertEquals(0, loadVerifyRule.loadRuleForObject(new Object()).size());
    }

}