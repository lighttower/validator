/**
 * @Title: ShortRangeFieldValidator.java
 * @Package com.icss.lighttower.validation.validators
 * @Description: TODO(用一句话描述该文件做什么)
 * @author s54322/sunyue
 * @date 2016年6月7日 下午4:54:56
 * @version V1.0
 */
package com.icss.lighttower.validator.validators;

/**
 * @ClassName: ShortRangeFieldValidator
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author s54322/sunyue
 * 
 */
public class ShortRangeFieldValidator extends RangeValidatorSupport<Short>
{

    /**
     * @Title: ShortRangeFieldValidator
     * @Description: ShortRangeFieldValidator构造函数
     * @param type
     * 
     */
    public ShortRangeFieldValidator()
    {
        super(Short.class);
    }

}
