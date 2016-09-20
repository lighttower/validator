package com.icss.lighttower.validator.config.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证规则
 * 
 * @ClassName: Rule
 * @Description: 验证规则
 * @author s54322/sunyue
 *
 */
public class Rule
{
    /**
     * 规则名称
     */
    private String name;
    /**
     * 验证失败后的消息
     */
    private String message;
    /**
     * 参数
     */
    private Map<String, String> parameters;
    
	public void addParameter(String name, String value)
    {
        this.parameters.put(name, value);
    }

    public String getParameter(String name)
    {
        return this.parameters.get(name);
    }

    public Rule()
    {
        this.parameters = new HashMap<String, String>();
    }

    public Map<String, String> getParameters()
    {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters)
    {
        this.parameters = parameters;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "[ name=" + name + ", message=" + message + " ]";
    }
}