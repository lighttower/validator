package com.icss.lighttower.validator.validators;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.icss.lighttower.validator.IValidator;
import com.icss.lighttower.validator.config.pojo.Rule;

/**
 * 最大长度验证器
 * 
 * @ClassName: MaxValidator
 * @Description:最大长度验证器
 * @author s54322/sunyue
 *
 */
public class MaxValidator implements IValidator {
	private static final Logger logger = Logger.getLogger(MaxValidator.class);

	@SuppressWarnings("rawtypes")
	public boolean execute(Object value, Class type, Object targetValue, Rule rule) {
		if (value == null) {
			return false;
		}

		int length = 0;
		String maxString = rule.getParameter("value");
		int max = StringUtils.isNumeric(maxString) ? Integer.parseInt(maxString) : -1;
		if (max == -1) {
			logger.warn("Invalid Parameter for minimun or maximun.");
			return false;
		}
		if (ClassUtils.isAssignable(value.getClass(), Object[].class)) {
			Object[] objValues = (Object[]) value;
			for (Object obj : objValues) {
				length = String.valueOf(obj).length();
				if (length > max) {
					return false;
				}
			}
			return true;
		} else if (value.getClass() == String.class) {
			length = ((String) value).trim().length();
		}
		return length <= max;
	}

}
