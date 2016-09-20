package com.icss.lighttower.validator.config.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证字段
 * 
 * @ClassName: Field
 * @Description:验证字段
 * @author s54322/sunyue
 *
 */
public class Field {
	/**
	 * 验证字段的名称
	 */
	private String name;
	/**
	 * 验证规则列表
	 */
	private List<Rule> rules;

	/**
	 * 验证的数据类型
	 */
	private String type;
	/**
	 * 格式，（日期类型）
	 */
	private String format;
	/**
	 * 国际化文件标签
	 */
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 添加规则，暂不不处理去重
	 * 
	 * @param rule
	 *            规则
	 */
	public void addRule(Rule rule) {
		rules.add(rule);
	}

	public Field() {
		this.rules = new ArrayList<Rule>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	@Override
	public String toString() {
		return "[ name=" + name + ", rules=" + rules + " ]";
	}
}
