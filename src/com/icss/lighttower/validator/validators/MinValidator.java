package com.icss.lighttower.validator.validators;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.icss.lighttower.validator.IValidator;
import com.icss.lighttower.validator.config.pojo.Rule;

/**
 * 最小长度验证器
 * 
 * @ClassName: MinValidator
 * @Description: 最小长度验证器
 * @author s54322/sunyue
 *
 */
public class MinValidator implements IValidator {
	private static final Logger logger = Logger.getLogger(MinValidator.class);

	@SuppressWarnings("rawtypes")
	public boolean execute(Object value, Class type, Object targetValue, Rule rule) {
		if (value == null) {
			return false;
		}

		int length = 0;
		String minString = rule.getParameter("value");
		int min = StringUtils.isNumeric(minString) ? Integer.parseInt(minString) : -1;
		if (min == -1) {
			logger.warn("Invalid Parameter for minimun or maximun.");
			return false;
		}
		if (ClassUtils.isAssignable(value.getClass(), Object[].class)) {
			Object[] objValues = (Object[]) value;
			for (Object obj : objValues) {
				length = String.valueOf(obj).length();
				if (length < min) {
					return false;
				}
			}
			return true;
		} else if (value.getClass() == String.class) {
			length = ((String) value).trim().length();
		}
		return length >= min;
	}

}
