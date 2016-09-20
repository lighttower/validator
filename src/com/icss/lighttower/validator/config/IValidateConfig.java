package com.icss.lighttower.validator.config;

import com.icss.lighttower.validator.config.pojo.Configuration;

/**
 * 验证框架的配置器
 * 
 * @ClassName: IValidateConfig
 * @Description: 验证框架的配置器
 * @author s54322/sunyue
 *
 */
public interface IValidateConfig
{
    /**
     * 读取配置
     * 
     * @return 配置对象
     */
    public Configuration readConfiguration();
}
