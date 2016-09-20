/**
 * @Title: DateRangeFieldValidator.java
 * @Package com.icss.lighttower.validation.validators
 * @Description: TODO(用一句话描述该文件做什么)
 * @author s54322/sunyue
 * @date 2016年6月7日 下午4:41:35
 * @version V1.0
 */
package com.icss.lighttower.validator.validators;

import java.util.Date;

/**
 * @ClassName: DateRangeFieldValidator
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author s54322/sunyue
 * 
 */
public class DateRangeFieldValidator extends RangeValidatorSupport<Date>
{

    /**
     * @Title: DateRangeFieldValidator
     * @Description: DateRangeFieldValidator构造函数
     * @param type
     * 
     */
    public DateRangeFieldValidator()
    {
        super(Date.class);
        // TODO Auto-generated constructor stub
    }

}
