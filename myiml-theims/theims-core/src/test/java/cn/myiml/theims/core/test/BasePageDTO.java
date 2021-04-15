package cn.myiml.theims.core.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yangzhou
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePageDTO {
    protected String start;
    protected String length;

    protected String limit;

    protected String keyWord;

}
