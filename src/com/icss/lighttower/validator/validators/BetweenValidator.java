package com.icss.lighttower.validator.validators;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.icss.lighttower.validator.IValidator;
import com.icss.lighttower.validator.config.pojo.Rule;

/**
 * 字符长度区间验证器
 * 
 * @ClassName: BetweenValidator
 * @Description: 字符长度区间验证器
 * @author s54322/sunyue
 *
 */
public class BetweenValidator implements IValidator {
	private static final Logger logger = Logger.getLogger(BetweenValidator.class);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean execute(Object value, Class type, Object targetValue, Rule rule) {
		if (value == null) {
			return false;
		}

		int length = 0;
		String minString = rule.getParameter("min");
		int min = StringUtils.isNumeric(minString) ? Integer.parseInt(minString) : -1;
		String maxString = rule.getParameter("max");
		int max = StringUtils.isNumeric(maxString) ? Integer.parseInt(maxString) : -1;
		if (min == -1 || max == -1) {
			logger.warn("Invalid Parameter for minimun or maximun.");
			return false;
		}
		if (ClassUtils.isAssignable(value.getClass(), Object[].class)) {
			Object[] objValues = (Object[]) value;
			for (Object obj : objValues) {
				length = String.valueOf(obj).length();
				if (length < min || length > max) {
					return false;
				}
			}
			return true;
		} else if (value.getClass() == String.class) {
			length = ((String) value).trim().length();
		}
		return length >= min && length <= max;
	}
}
