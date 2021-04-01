package org.themis.check.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.google.common.cache.Cache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.themis.check.utils.check.CheckRuleSingleton;
import org.themis.check.utils.check.CheckRulesConfigModel;
import org.themis.check.utils.check.ProcessTypeEnum;
import org.themis.check.utils.check.RuleConfigModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
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

    @Test
    public void whenParamsIsManyAddListIsNotNull(){
        ruleConfig = new RuleConfigModel();
        ruleConfig.setParamName("data.companyId,data.id");
        List<String[]> list = new ArrayList<>();
        String[] params = ruleConfig.getParamName().split(",");
        for (String str:params) {
            list.add(str.split("\\."));
        }
        assertTrue(list.size() == 2);
        assertTrue(list.get(0).length == 2);
        assertTrue(list.get(1).length == 2);
//        ParameterInterceptor.traversalParameterFromRule(param,paramNames, 0, ruleConfig);
    }


    @Test
    public void whenParamsIsManyAndParamsDefectSplitIsTrue(){
        ruleConfig = new RuleConfigModel();
        ruleConfig.setParamName("data.companyId,data.");
        List<String[]> list = new ArrayList<>();
        String[] params = ruleConfig.getParamName().split(",");
        for (String str:params) {
            list.add(str.split("\\."));
        }
        assertTrue(list.size() == 2);
        assertTrue(list.get(0).length == 2);
        assertTrue(list.get(1).length == 1);
    }

    @Test
    public void whenParamsIsManyAndParamsDefectAndFormatIsErrorSplitIsTrue(){
        ruleConfig = new RuleConfigModel();
        ruleConfig.setParamName("data.companyId,,.data..,,,,");
        List<String[]> list = new ArrayList<>();
        Iterable<String> params = Splitter.on(',').trimResults().omitEmptyStrings().split(ruleConfig.getParamName());;
        for (String str:params) {
            Iterable<String> strings = Splitter.on('.').trimResults().omitEmptyStrings().split(str);
            List<String> paramStr = new ArrayList<>();
            strings.forEach(paramStr::add);
            list.add(paramStr.toArray(new String[list.size()]));
        }

        assertTrue(list.size() == 2);
        assertTrue(list.get(0).length == 2);
        assertTrue(list.get(1).length == 1);
    }

    @Test
    public void whenParamsArrayAddCacheIsTrue(){
        ruleConfig = new RuleConfigModel();
        ruleConfig.setCheckRule("");
        ruleConfig.setMessage("主体id不合法");
        ruleConfig.setPattern("DEFAULT");
        ruleConfig.setParamName("data.companyId,,.data.id.,,,,");

        List<CheckRulesConfigModel> checkRules = new ArrayList<>();
        List<RuleConfigModel> ruleConfigModels = new ArrayList<>();
        ruleConfigModels.add(ruleConfig);

        CheckRulesConfigModel checkRulesConfigModel = new CheckRulesConfigModel();
        checkRulesConfigModel.setRules(ruleConfigModels);
        checkRulesConfigModel.setRoute("/nk/deposit/v1/list_deposit_notes.do");
        checkRulesConfigModel.setProcessType("processType");
        checkRulesConfigModel.setName("保证金详情记录列表查询");
        checkRulesConfigModel.setTypeVal(ProcessTypeEnum.DEFAULT.name());
        checkRules.add(checkRulesConfigModel);

        Cache cache = CheckRuleSingleton.getInstance().addCache("/nk/deposit/v1/list_deposit_notes.do", checkRules);
        assertTrue(cache.size() == 1);
    }

    @Test
    public void whenParamsArrayAddCacheCheckIsTrue(){
        String data  = readJsonFile("src/test/resources/test_data.json");
        param = (JSON) JSON.parse(data);
        ruleConfig = new RuleConfigModel();
        ruleConfig.setCheckRule("");
        ruleConfig.setMessage("主体id不合法");
        ruleConfig.setPattern("DEFAULT");
        ruleConfig.setParamName("data.companyId,,.data.id.,,,,");

        List<CheckRulesConfigModel> checkRules = new ArrayList<>();
        List<RuleConfigModel> ruleConfigModels = new ArrayList<>();
        ruleConfigModels.add(ruleConfig);

        CheckRulesConfigModel checkRulesConfigModel = new CheckRulesConfigModel();
        checkRulesConfigModel.setRules(ruleConfigModels);
        checkRulesConfigModel.setRoute("/nk/deposit/v1/list_deposit_notes.do");
        checkRulesConfigModel.setProcessType("processType");
        checkRulesConfigModel.setName("保证金详情记录列表查询");
        checkRulesConfigModel.setTypeVal(ProcessTypeEnum.DEFAULT.name());
        checkRules.add(checkRulesConfigModel);
        ParameterInterceptor.requestParameterCheck(param,checkRules);
    }



}