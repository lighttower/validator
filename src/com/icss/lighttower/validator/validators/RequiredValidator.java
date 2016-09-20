package com.icss.lighttower.validator.validators;

import org.apache.commons.lang.ClassUtils;

import com.icss.lighttower.validator.IValidator;
import com.icss.lighttower.validator.config.pojo.Rule;

/**
 * 必填验证器
 * 
 * @ClassName: RequiredValidator
 * @Description: 参数必填
 * @author s54322/sunyue
 *
 */
public class RequiredValidator implements IValidator {

	@SuppressWarnings("rawtypes")
	public boolean execute(Object value, Class type, Object targetValue, Rule rule) {
		if (value == null) {
			return false;
		}
		if (ClassUtils.isAssignable(value.getClass(), Object[].class)) {
			Object[] objValues = (Object[]) value;
			for (Object obj : objValues) {
				if (obj == null || String.valueOf(obj).length() < 0) {
					return false;
				}
			}
			return true;
		} else {
			return value != null && ((String) value).trim().length() > 0;
		}
	}

}
