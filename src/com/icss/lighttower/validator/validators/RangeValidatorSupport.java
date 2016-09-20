/**
 * @Title: RangeValidatorSupport.java
 * @Package com.icss.lighttower.validation.validators
 * @Description: TODO(用一句话描述该文件做什么)
 * @author s54322/sunyue
 * @date 2016年6月7日 下午4:03:11
 * @version V1.0
 */
package com.icss.lighttower.validator.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.icss.lighttower.validator.IValidator;
import com.icss.lighttower.validator.config.pojo.Rule;

/**
 * @ClassName: RangeValidatorSupport
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author s54322/sunyue
 * 
 */
public class RangeValidatorSupport<T extends Comparable<?>> implements IValidator
{

    private final Class<T> type;

    /**
     * @Title: RangeValidatorSupport
     * @Description: RangeValidatorSupport构造函数
     * @param type
     * 
     */
    public RangeValidatorSupport(Class<T> type)
    {
        this.type = type;
    }

    /*
     * (非 Javadoc)
     * 
     * @Title: execute
     * 
     * @Description: TODO(描述方法作用)
     * 
     * @param context
     * 
     * @param type
     * 
     * @param value
     * 
     * @param rule
     * 
     * @return
     * 
     * @see com.icss.lighttower.validation.IValidator#execute(java.lang.Object, java.lang.Class, java.lang.Object, com.icss.lighttower.validation.config.pojo.Rule)
     * 
     * @Version v1.0
     * 
     * @author s54322/sunyue
     * 
     * @Date 2016年6月7日
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public boolean execute(Object value, Class types, Object targetValue, Rule rule)
    {
        if (value == null)
        {
            return false;
        }
        Comparable<T> val = (Comparable<T>) value;
        T minComparatorValue = getParam(rule.getParameter("min"));
        if ((minComparatorValue != null) && (val.compareTo(minComparatorValue) < 0))
        {
            return false;
        }

        T maxComparatorValue = getParam(rule.getParameter("max"));
        if ((maxComparatorValue != null) && (val.compareTo(maxComparatorValue) > 0))
        {
            return false;
        }
        return true;
    }

    /**
     * @return the type
     */
    public Class<T> getType()
    {
        return type;
    }

    @SuppressWarnings("unchecked")
	private T getParam(String param)
    {
        if(StringUtils.isEmpty(param))
        {
            return null;
        }
        if (type == Integer.class || type == int.class)
        {
            return (T) Integer.valueOf(param);
        }
        if (type == Long.class || type == long.class)
        {
            return (T) Long.valueOf(param);
        }
        if (type == Double.class || type == double.class)
        {
            return (T) Double.valueOf(param);
        }
        if (type == Short.class || type == short.class)
        {
            return (T) Short.valueOf(param);
        }
        if (type == Date.class)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            try
            {
                return (T) sdf.parse(param);
            }
            catch (ParseException e)
            {
                return null;
            }
        }
        return null;
    }

}
