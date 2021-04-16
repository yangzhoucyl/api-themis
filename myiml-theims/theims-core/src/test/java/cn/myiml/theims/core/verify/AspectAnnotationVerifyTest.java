package cn.myiml.theims.core.verify;

import cn.myiml.theims.core.test.QueryDepositOverviewDTO;
import cn.myiml.theims.core.test.VerifyTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/spring-aop-verify.xml")
public class AspectAnnotationVerifyTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private VerifyTest verifyTest;

    @Before
    public void before(){

    }

    @Test
    public void annotationVerifyExecute() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("paramName is Illegal parameter");
        verifyTest.annotationVerifyFieldTestFail("11");
    }

    @Test
    public void annotationVerifyExecuteTrue() {
        verifyTest.annotationVerifyFieldTestSuccess("11", "22");
    }

    @Test
    public void annotationVerifyExecuteRegula() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("paramName is Illegal parameter");
        verifyTest.annotationVerifyFieldTestFail("11");
    }

    @Test
    public void annotationVerifyExecuteRegulaFail() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("checkRule is Illegal parameter");
        verifyTest.annotationVerifyFieldTestRegularFail("11", "xxxx");
    }

    @Test
    public void annotationVerifyExecuteRegulaSuccess() {
        verifyTest.annotationVerifyFieldTestRegularFail("11", "11");
    }
    @Test
    public void annotationVerifyObjExecuteRegulaSuccess() {
        QueryDepositOverviewDTO overview = new QueryDepositOverviewDTO();
        overview.setCompanyId(111L);
        verifyTest.annotationVerifyFieldObj(overview);
    }

    @Test
    public void annotationVerifyFieldPrimitiveIsTrue(){
        verifyTest.annotationVerifyFieldPrimitive(1);
    }

    @Test
    public void annotationVerifyFieldListIsTrue(){
        List<QueryDepositOverviewDTO> list = new ArrayList<>();
        QueryDepositOverviewDTO overview = new QueryDepositOverviewDTO();
        overview.setCompanyId(111L);
        list.add(overview);
        verifyTest.annotationVerifyFieldPrimitive(list);
    }
    @Test
    public void annotationVerifyFieldMapIsTrue(){
        Map<String,Object> result = new HashMap<>();
        result.put("companyId", 111L);
        verifyTest.annotationVerifyFieldMap(result);
    }

    @Test
    public void annotationVerifyFieldMapIsFalse(){
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("companyId is Illegal parameter");
        Map<String,Object> result = new HashMap<>();
        result.put("companyId", "");
        verifyTest.annotationVerifyFieldMap(result);
    }

    @Test
    public void annotationVerifyFileList(){
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("companyId is Illegal parameter");
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("xxx");
        verifyTest.annotationVerifyFieldListPrimitive(list);
    }

}