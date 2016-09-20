package com.icss.lighttower.validator.validators;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import com.icss.lighttower.validator.IValidator;
import com.icss.lighttower.validator.config.pojo.Rule;

/**
 * 相等匹配
 * 
 * @ClassName: EqualsValidator
 * @Description: 相等匹配
 * @author s54322/sunyue
 *
 */
public class EqualsValidator implements IValidator {
	private static final Logger logger = Logger.getLogger(EqualsValidator.class);

	@SuppressWarnings("rawtypes")
	public boolean execute(Object value, Class type, Object targetValue, Rule rule) {
		if (value == null) {
			return false;
		}
		if (ClassUtils.isAssignable(value.getClass(), Object[].class)) {
			Object[] objValues = (Object[]) value;
			for (Object obj : objValues) {
				String toValue = String.valueOf(obj);
				if (toValue == null || !toValue.equals(String.valueOf(targetValue))) {
					logger.error("EqualsValidator return false");
					return false;
				}
			}
			return true;
		}
		return value.equals(String.valueOf(targetValue));
	}

}
