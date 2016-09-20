package com.icss.lighttower.validator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证处理管理器
 * 
 * @ClassName: IValidateService
 * @Description: 验证器接口
 * @author s54322/sunyue
 * @param <T>
 *
 */
public interface IValidateService
{
    /**
     * 初始化
     */
    void init();

    /**
     * 对某个对象执行按组验证操作
     * 
     * @param request
     *            执行对象
     * @param groupName
     *            验证组
     * @return 返回验证结果，如果数量为0，则表示正确无误
     */
    Map<String, String> validate(HttpServletRequest request, String groupName);
}
