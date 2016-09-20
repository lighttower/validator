/**
 * @Title: DoubleRangeFieldValidator.java
 * @Package com.icss.lighttower.validation.validators
 * @Description: TODO(用一句话描述该文件做什么)
 * @author s54322/sunyue
 * @date 2016年6月7日 下午4:58:18
 * @version V1.0
 */
package com.icss.lighttower.validator.validators;

/**
 * @ClassName: DoubleRangeFieldValidator
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author s54322/sunyue
 * 
 */
public class DoubleRangeFieldValidator extends RangeValidatorSupport<Double>
{

    /**
     * @Title: DoubleRangeFieldValidator
     * @Description: DoubleRangeFieldValidator构造函数
     * 
     */
    public DoubleRangeFieldValidator()
    {
        super(Double.class);
    }

}
