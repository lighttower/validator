package com.icss.lighttower.validator;

import com.icss.lighttower.validator.config.pojo.Rule;

/**
 * 校验框架中的执行接口 该接口为重要接口，处理每个验证的处理方法
 * 
 * @ClassName: IValidator
 * @Description: 校验框架中的执行接口 该接口为重要接口，处理每个验证的处理方法
 * @author s54322/sunyue
 *
 */
public interface IValidator {

	/**
	 * 要处理的对象
	 * 
	 * @param value
	 *            要判断的值
	 * @param type
	 *            对象类型
	 * @param rule
	 *            对象规则
	 * @return 是否成功
	 */
	@SuppressWarnings("rawtypes")
	boolean execute(Object value, Class type, Object targetValue,Rule rule);
}
