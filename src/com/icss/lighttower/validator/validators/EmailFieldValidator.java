/**
 * @Title: EmailFieldValidator.java
 * @Package com.icss.lighttower.validation.validators
 * @Description: TODO(用一句话描述该文件做什么)
 * @author s54322/sunyue
 * @date 2016年6月7日 下午4:51:23
 * @version V1.0
 */
package com.icss.lighttower.validator.validators;

/**
 * @ClassName: EmailFieldValidator
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author s54322/sunyue
 * 
 */
public class EmailFieldValidator extends RegexFieldValidator
{

    public static final String EMAIL_ADDRESS_PATTERN = "\\b^['_a-z0-9-\\+]+(\\.['_a-z0-9-\\+]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2}|aero|arpa|asia|biz|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|nato|net|org|pro|tel|travel|xxx)$\\b";

    /**
     * @Title: EmailFieldValidator
     * @Description: EmailFieldValidator构造函数
     * 
     */
    public EmailFieldValidator()
    {
        setRegex(EMAIL_ADDRESS_PATTERN);
        setCaseSensitive(false);
    }

}
