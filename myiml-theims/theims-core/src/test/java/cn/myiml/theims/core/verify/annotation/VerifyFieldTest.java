package cn.myiml.theims.core.verify.annotation;


import cn.myiml.theims.core.enums.PatternEnum;
import cn.myiml.theims.core.model.VerifyRulesConfigModel;
import cn.myiml.theims.core.verify.VerifyRuleSingleton;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class VerifyFieldTest {

    @VerifyField(names = {"paramName", "checkRule"}, pattern = PatternEnum.REGULAR)
    public void annotationVerifyFieldTest() {

    }
    @VerifyFields(fields = {@VerifyField(names = {"paramName", "checkRule"}, pattern = PatternEnum.REGULAR)})
    public void annotationVerifyFieldsTest(){

    }

    @Test
    public void testAnnotationVerifyField() {
        Class clazz = VerifyRuleSingleton.getInstance().getClass();
        List<VerifyField> verifyFields = new ArrayList<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof VerifyField) {
                    verifyFields.add((VerifyField) annotation);
                }
            }
        }
        assertTrue(verifyFields.size() == 1);
    }

    @Test
    public void testAnnotationVerifyFieldAddToCache() {
        Class clazz = VerifyRuleSingleton.getInstance().getClass();
        List<VerifyField> verifyFields = new ArrayList<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof VerifyField) {
                    verifyFields.add((VerifyField) annotation);
                }
            }
        }

    }


}
