package com.icss.lighttower.validator.config.pojo;

/**
 * 验证器类
 * 
 * @ClassName: Validator
 * @Description: 验证器类
 * @author s54322/sunyue
 *
 */
public class Validator
{
    /**
     * 验证器名称
     */
    private String name;
    /**
     * 验证器全类名
     */
    private String className;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

}
