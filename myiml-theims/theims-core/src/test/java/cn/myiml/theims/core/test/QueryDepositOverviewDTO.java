package cn.myiml.theims.core.test;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 保证金查询
 * @author yangzhou
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryDepositOverviewDTO extends BasePageDTO implements Serializable {

    private static final long serialVersionUID = -7962386461575749971L;

    //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * 开始时间
     */
    private String startDate;


    /// @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 主体id
     */
    private Long companyId;

    /**
     * 最小保证金余额
     */
    private Double minBalance;

    /**
     * 最大保证金余额
     */
    private Double maxBalance;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 业务类型
     */
    private String businessType;

}
