package org.themis.check.interceptor;

import com.alibaba.fastjson.JSON;
import org.themis.check.utils.check.RuleConfigModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.themis.check.utils.ReaderTestDataUtils.readJsonFile;

public class ParameterInterceptorTest {


    private RuleConfigModel ruleConfig;


    private JSON param = null;

    private String[] paramNames;

    @Before
    public void setUp() throws Exception {
        String data  = readJsonFile("src/test/resources/test_data.json");
        param = (JSON) JSON.parse(data);
    }

    @After
    public void tearDown() throws Exception {
        ruleConfig = null;
    }

    @Test
    public void whenDataArrayParamNotNullIsTrueTest(){
        ruleConfig = new RuleConfigModel();
        ruleConfig.setConfigId(723062485926457969L);
        ruleConfig.setCheckRule("^[-\\+]?[\\d]*$");
        ruleConfig.setMessage("主体id不合法");
        ruleConfig.setPattern("REGULAR");
        ruleConfig.setParamName("data.companyId");
        paramNames = ruleConfig.getParamName().split("\\.");
        ParameterInterceptor.traversalParameterFromRule(param,paramNames, 0, ruleConfig);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDataArrayParamIsNullThrowIllegalArgumentException(){
        ruleConfig = new RuleConfigModel();
        ruleConfig.setCheckRule("^[-\\+]?[\\d]*$");
        ruleConfig.setMessage("主体id不合法");
        ruleConfig.setPattern("REGULAR");
        ruleConfig.setParamName("data.companyIds");
        paramNames = ruleConfig.getParamName().split("\\.");
        ParameterInterceptor.traversalParameterFromRule(param,paramNames, 0, ruleConfig);
    }

    @Test
    public void whenDataArrayParamIsOneAndNotNullCheckIsTrue(){
        ruleConfig = new RuleConfigModel();
        ruleConfig.setCheckRule("");
        ruleConfig.setMessage("主体id不合法");
        ruleConfig.setPattern("DEFAULT");
        ruleConfig.setParamName("one_param_arr");
        paramNames = ruleConfig.getParamName().split("\\.");
        ParameterInterceptor.traversalParameterFromRule(param,paramNames, 0, ruleConfig);
    }

    @Test
    public void whenDataArrayParamsAndNotNullCheckIsTrue(){
        ruleConfig = new RuleConfigModel();
        ruleConfig.setCheckRule("");
        ruleConfig.setMessage("主体id不合法");
        ruleConfig.setPattern("DEFAULT");
        ruleConfig.setParamName("data.array1.array2.companyId");
        paramNames = ruleConfig.getParamName().split("\\.");
        ParameterInterceptor.traversalParameterFromRule(param,paramNames, 0, ruleConfig);
    }

    @Test
    public void whenDataArrayParamsIsArrayCheckIsTrue(){
        String data  = readJsonFile("src/test/resources/test_data_array.json");
        param = (JSON) JSON.parse(data);
        ruleConfig = new RuleConfigModel();
        ruleConfig.setCheckRule("");
        ruleConfig.setMessage("主体id不合法");
        ruleConfig.setPattern("DEFAULT");
        ruleConfig.setParamName("data");
        paramNames = ruleConfig.getParamName().split("\\.");
        ParameterInterceptor.traversalParameterFromRule(param,paramNames, 0, ruleConfig);
    }


}