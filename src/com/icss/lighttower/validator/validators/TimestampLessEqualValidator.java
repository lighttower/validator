package com.icss.lighttower.validator.validators;

import java.sql.Timestamp;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.icss.lighttower.validator.IValidator;
import com.icss.lighttower.validator.config.pojo.Rule;

/**
 * 当前时间需要小于等于
 * 
 * @ClassName: TimestampLessEqualValidator
 * @Description: 当前时间需要小于等于
 * @author s54322/sunyue
 *
 */
public class TimestampLessEqualValidator implements IValidator
{
    private static final Logger logger = Logger.getLogger(TimestampLessEqualValidator.class);

    @SuppressWarnings("rawtypes")
    public boolean execute(Object context, Class type, Object value, Rule rule)
    {
        if (value == null || !(value instanceof Timestamp))
        {
            return false;
        }
        String toName = rule.getParameter("target");
        if (StringUtils.isBlank(toName))
        {
            logger.warn("Less And Equal target parameter missed");
            return false;
        }

        Object toValue = null;
        try
        {
            toValue = PropertyUtils.getProperty(context, toName);
            if (!(toValue instanceof Timestamp))
                return false;
        }
        catch (Exception e)
        {
            logger.warn("Less And Equal target value missed , " + toName);
        }
        Timestamp timestamp = (Timestamp) value;
        Timestamp toTimestamp = (Timestamp) toValue;
        return timestamp.getTime() <= toTimestamp.getTime();
    }

}
