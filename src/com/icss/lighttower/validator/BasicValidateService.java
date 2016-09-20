package com.icss.lighttower.validator;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.icss.lighttower.validator.config.IValidateConfig;
import com.icss.lighttower.validator.config.pojo.Configuration;
import com.icss.lighttower.validator.config.pojo.Field;
import com.icss.lighttower.validator.config.pojo.Group;
import com.icss.lighttower.validator.config.pojo.Rule;

/**
 * 基础验证服务
 * 
 * @ClassName: BasicValidateService
 * @Description:基础验证服务
 * @author s54322/sunyue
 *
 */
public class BasicValidateService implements IValidateService
{
    /**
     * 日志记录器
     */
    private static final Logger LOG = Logger.getLogger(BasicValidateService.class);

    /**
     * 验证器配置
     */
    private IValidateConfig config;

    /**
     * 配置
     */
    private Configuration configuration;

    /**
     * 是否初始化
     */
    private boolean isInited;
    /**
     * 读取国际化配置文件
     */
    private ReloadableResourceBundleMessageSource messageSource;
    /**
     * 国际化key
     */
    private String localParam = "langType";

    public String getLocalParam()
    {
        return localParam;
    }

    public void setLocalParam(String localParam)
    {
        this.localParam = localParam;
    }

    public ReloadableResourceBundleMessageSource getMessageSource()
    {
        return messageSource;
    }

    public void setMessageSource(ReloadableResourceBundleMessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    public BasicValidateService()
    {
    }

    public BasicValidateService(IValidateConfig config)
    {
        this.setConfig(config);
    }

    public IValidateConfig getConfig()
    {
        return config;
    }

    public Configuration getConfiguration()
    {
        return configuration;
    }

    /**
     * 初始化
     */
    @Override
    public void init()
    {
        // 读取配置
        this.configuration = config.readConfiguration();

        // 设定已经被初始化
        this.isInited = true;
    }

    public void setConfig(IValidateConfig config)
    {
        this.config = config;
    }

    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }

    /*
     * (非 Javadoc)
     * 
     * @Title: validate
     * 
     * @Description: TODO(描述方法作用)
     * 
     * @param object
     * 
     * @param groupName
     * 
     * @return
     * 
     * @see com.icss.lighttower.validation.IValidateService#validate(java.lang. Object, java.lang.String)
     * 
     * @Version v1.0
     * 
     * @author s54322/sunyue
     * 
     * @Date 2016年6月8日
     */
    @Override
    public Map<String, String> validate(HttpServletRequest request, String groupName)
    {
        // 判断是否被初始化
        if (!this.isInited)
        {
            this.init();
        }

        Map<String, String> results = new LinkedHashMap<String, String>();
        Group group = this.configuration.getGroup(groupName);
        if (group == null)
        {
            return results;
        }

        Map<String, IValidator> validators = this.configuration.getValidators();
        if (validators == null || validators.isEmpty())
        {
            return results;
        }

        List<Field> fields = group.getFields();
        if (fields == null || fields.isEmpty())
        {
            return results;
        }
        Iterator<Field> fs = fields.iterator();
        while (fs.hasNext())
        {
            Field field = fs.next();
            String fname = field.getName();
            Class<?> type = null;
            List<Rule> rules = field.getRules();
            if (rules == null || rules.isEmpty())
            {
                continue;
            }
            try
            {
                type = Class.forName(field.getType());
            }
            catch (Exception e)
            {
                LOG.warn(e.getMessage(), e);
            }
            finally
            {
                Iterator<Rule> rs = rules.iterator();
                while (rs.hasNext())
                {
                    Rule rule = rs.next();
                    String rname = rule.getName();
                    IValidator validator = validators.get(rname);
                    if (validator == null)
                    {
                        continue;
                    }
                    String toName = rule.getParameter("target");
                    boolean r = validator.execute(request.getParameterValues(fname), type, request.getParameter(toName),
                            rule);
                    if (!r)
                    {

                        results.put(fname, messageSource.getMessage(rule.getMessage(),
                                generateParameter(field.getLabel(), rule, request), getLocale(request)));
                        break;
                    }
                }
            }
        }
        return results;
    }

    private Locale getLocale(HttpServletRequest request)
    {
        Locale locale = null;
        try
        {
            locale = new Locale(request.getParameter(localParam));
        }
        catch (Exception e)
        {
            locale = new Locale("");
        }
        return locale;
    }

    private Object[] generateParameter(String fieldKey, Rule rule, HttpServletRequest request)
    {
        String rname = rule.getName();
        Object[] obj = null;
        String i18nMinName = null;
        String i18nMaxName = null;
        switch (rname)
        {
            case "required":
                String i18nRequiredFieldName = messageSource.getMessage(fieldKey, new Object[] {}, getLocale(request));
                obj = new Object[] { i18nRequiredFieldName };
                break;
            case "min":
                String i18nMinFieldName = messageSource.getMessage(fieldKey, new Object[] {}, getLocale(request));
                obj = new Object[] { i18nMinFieldName, rule.getParameter("value") };
                break;
            case "max":
                String i18nMaxFieldName = messageSource.getMessage(fieldKey, new Object[] {}, getLocale(request));
                obj = new Object[] { i18nMaxFieldName, rule.getParameter("value") };
                break;
            case "timestampLessEqual":
            case "timestampCreaterEqual":
                String i18nFieldName = messageSource.getMessage(fieldKey, new Object[] {}, getLocale(request));
                String i18nTargetName = messageSource.getMessage(rule.getParameter("target"), new Object[] {},
                        getLocale(request));
                obj = new Object[] { i18nFieldName, i18nTargetName };
                break;
            case "between":
                String i18nBetweenFieldName = messageSource.getMessage(fieldKey, new Object[] {}, getLocale(request));
                i18nMinName = messageSource.getMessage(rule.getParameter("min"), new Object[] {}, getLocale(request));
                i18nMaxName = messageSource.getMessage(rule.getParameter("max"), new Object[] {}, getLocale(request));
                obj = new Object[] { i18nBetweenFieldName, i18nMinName, i18nMaxName };
                break;
            default:
                obj = new Object[] {};
                break;
        }
        return obj;
    }
}
