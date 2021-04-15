package cn.myiml.theims.core.test;

import cn.myiml.theims.core.enums.BasicRules;
import cn.myiml.theims.core.enums.PatternEnum;
import cn.myiml.theims.core.verify.annotation.VerifyField;
import org.springframework.stereotype.Component;

import static cn.myiml.theims.core.enums.BasicRules.NUMBER_REG;

/**
 * @author yangzhou
 */
@Component
public class VerifyTest {

    @VerifyField(names = {"paramName", "checkRule"}, pattern = PatternEnum.REGULAR)
    public  void annotationVerifyFieldTestFail(String paramName) {
        System.out.println(paramName);
    }


    @VerifyField(names = {"paramName", "checkRule"}, pattern = PatternEnum.DEFAULT)
    public  void annotationVerifyFieldTestSuccess(String paramName, String checkRule) {
        System.out.println(paramName);
    }

    @VerifyField(names = {"paramName", "checkRule"}, pattern = PatternEnum.REGULAR, rule= BasicRules.NUMBER_REG)
    public  void annotationVerifyFieldTestRegularFail(String paramName,  String checkRule) {
        System.out.println(paramName);
        System.out.println(checkRule);
    }

    @VerifyField(names = {"companyId"}, pattern = PatternEnum.REGULAR, rule = NUMBER_REG)
    public void annotationVerifyFieldObj(QueryDepositOverviewDTO queryDeposit) {
        System.out.println(queryDeposit);
    }


}
