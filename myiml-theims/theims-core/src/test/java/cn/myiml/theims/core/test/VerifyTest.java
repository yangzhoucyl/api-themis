package cn.myiml.theims.core.test;

import cn.myiml.theims.core.enums.BasicRules;
import cn.myiml.theims.core.enums.PatternEnum;
import cn.myiml.theims.core.verify.annotation.VerifyField;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.myiml.theims.core.enums.BasicRules.NUMBER_REG;
import static cn.myiml.theims.core.enums.ErrorMessage.DEFAULT;

/**
 * @author yangzhou
 */
@Component
public class VerifyTest {

    @VerifyField(names = {"paramName", "checkRule"}, pattern = PatternEnum.REGULAR, message = DEFAULT)
    public  void annotationVerifyFieldTestFail(String paramName) {
        System.out.println(paramName);
    }


    @VerifyField(names = {"paramName", "checkRule"}, pattern = PatternEnum.DEFAULT, message = DEFAULT)
    public  void annotationVerifyFieldTestSuccess(String paramName, String checkRule) {
        System.out.println(paramName);
    }

    @VerifyField(names = {"paramName", "checkRule"}, pattern = PatternEnum.REGULAR, rule= BasicRules.NUMBER_REG, message = DEFAULT)
    public  void annotationVerifyFieldTestRegularFail(String paramName,  String checkRule) {
        System.out.println(paramName);
        System.out.println(checkRule);
    }

    @VerifyField(names = {"companyId"}, pattern = PatternEnum.REGULAR, rule = NUMBER_REG, message = DEFAULT)
    public void annotationVerifyFieldObj(QueryDepositOverviewDTO queryDeposit) {
        System.out.println(queryDeposit);
    }

    @VerifyField(names = {"companyId"}, pattern = PatternEnum.DEFAULT, message = DEFAULT)
    public void annotationVerifyFieldPrimitive(int companyId){
        System.out.println(companyId);
    };

    @VerifyField(names = {"companyId"}, pattern = PatternEnum.DEFAULT, message = DEFAULT)
    public void annotationVerifyFieldPrimitive(List<QueryDepositOverviewDTO> data){
    };

    @VerifyField(names = {"companyId"}, pattern = PatternEnum.REGULAR, rule = NUMBER_REG,message = DEFAULT)
    public void annotationVerifyFieldListPrimitive(List<String> data){
    };

    @VerifyField(names = {"companyId"}, pattern = PatternEnum.DEFAULT, message = DEFAULT)
    public void annotationVerifyFieldMap(Map<String, Object> data){
    };
}
