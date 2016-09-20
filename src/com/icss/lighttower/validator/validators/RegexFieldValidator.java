package com.icss.lighttower.validator.validators;

import java.util.regex.Pattern;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import com.icss.lighttower.validator.IValidator;
import com.icss.lighttower.validator.config.pojo.Rule;

/**
 * 正则匹配
 * 
 * @ClassName: RegexFieldValidator
 * @Description: 正则匹配
 * @author s54322/sunyue
 *
 */
public class RegexFieldValidator implements IValidator {

	private String regex;

	private Boolean caseSensitive = true;

	@SuppressWarnings("rawtypes")
	public boolean execute(Object value, Class type, Object targetValue, Rule rule) {
		if (value == null) {
			return false;
		}
		if (caseSensitive) {
			regex = rule.getParameter("regex");
		}
		if (StringUtils.isBlank(regex)) {
			return true;
		}
		if (ClassUtils.isAssignable(value.getClass(), Object[].class)) {
			Object[] objValues = (Object[]) value;
			for (Object obj : objValues) {
				if (obj == null || !Pattern.matches(regex, String.valueOf(obj))) {
					return false;
				}
			}
			return true;
		}
		return Pattern.matches(regex, String.valueOf(value));
	}

	/**
	 * @return the regex
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * @param regex
	 *            the regex to set
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}

	/**
	 * @return the caseSensitive
	 */
	public Boolean getCaseSensitive() {
		return caseSensitive;
	}

	/**
	 * @param caseSensitive
	 *            the caseSensitive to set
	 */
	public void setCaseSensitive(Boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

}
