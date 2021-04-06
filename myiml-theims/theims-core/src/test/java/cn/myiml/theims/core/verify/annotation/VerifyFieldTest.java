package cn.myiml.theims.core.verify.annotation;


import cn.myiml.theims.core.enums.PatternEnum;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class VerifyFieldTest {

    @VerifyField(names = {"paramName", "checkRule"}, pattern = PatternEnum.REGULAR)
    public void annotationVerifyFieldTest() {

    }
    @VerifyFields(fields = {@VerifyField(names = {"data.companyId", "checkRule"}, pattern = PatternEnum.REGULAR)})
    public void annotationVerifyFieldsTest(){

    }

    public void annotationVerifyFieldsTestNoAnnotation(){

    }

    @Test
    public void testAnnotationVerifyField() {
        Class<? extends Object> clazz = new VerifyFieldTest().getClass();
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
        assertEquals(1, verifyFields.size());
    }

    @Test
    public void testAnnotationVerifyFieldAddToCache() {
        Class clazz = new VerifyFieldTest().getClass();
        List<VerifyField> verifyFieldList = new ArrayList<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof VerifyFields) {
                    VerifyFields verifyFields = (VerifyFields) annotation;
                    VerifyField[] verifyFieldArrays = verifyFields.fields();
                    verifyFieldList.addAll(Arrays.stream(verifyFieldArrays).collect(Collectors.toList()));
                }
            }
        }
        assertEquals(1, verifyFieldList.size());
    }


}
