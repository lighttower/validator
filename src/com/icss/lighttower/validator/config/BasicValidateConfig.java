package com.icss.lighttower.validator.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;

import com.icss.lighttower.validator.IValidator;
import com.icss.lighttower.validator.config.pojo.Configuration;
import com.icss.lighttower.validator.config.pojo.Field;
import com.icss.lighttower.validator.config.pojo.Group;
import com.icss.lighttower.validator.config.pojo.Rule;
import com.icss.lighttower.validator.config.pojo.Validator;

/**
 * 基础验证配置
 * 
 * @ClassName: BasicValidateConfig
 * @Description: 基础验证配置
 * @author s54322/sunyue
 *
 */
public class BasicValidateConfig implements IValidateConfig
{
    /**
     * 日志记录器
     */
    private static final Logger LOG = Logger.getLogger(BasicValidateConfig.class);

    /**
     * 默认验证器
     */
    private static final String DEFAULT_GROUPS = "classpath:rules.lt.xml";

    /**
     * 默认验证器
     */
    private static final String DEFAULT_VALIDATORS = "classpath:validators.lt.xml";
    
    /**
     * 验证器路径
     */
    private String validators;
    /**
     * 规则文件
     */
    public Resource[] rulePaths;

    public Resource[] getRulePaths() {
		return rulePaths;
	}

	public void setRulePaths(Resource[] rulePaths) {
		this.rulePaths = rulePaths;
	}

	/**
     * 规则路径
     */
    private String rules;

    public BasicValidateConfig()
    {
        this(DEFAULT_VALIDATORS, DEFAULT_GROUPS);
    }

    public BasicValidateConfig(String validators, String rules)
    {
        super();
        this.validators = validators;
        this.rules = rules;
    }

    public String getRules()
    {
        return rules;
    }

    public String getValidators()
    {
        return validators;
    }

    /**
     * 实例化验证器
     * 
     * @param v
     *            验证对象
     * @return 验证器
     * @throws ClassNotFoundException
     *             ClassNotFoundException
     * @throws IllegalAccessException
     *             IllegalAccessException
     * @throws InstantiationException
     *             InstantiationException
     */
    @SuppressWarnings("unchecked")
    public IValidator instanceValidator(Validator v) throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Class<IValidator> vclassor = (Class<IValidator>) Class.forName(v.getClassName());
        IValidator validator = vclassor.newInstance();
        return validator;
    }

    /**
     * 初始化校验器
     * 
     * @return Configuration
     */
    public Configuration readConfiguration()
    {
        Configuration configuratoin = new Configuration();
        configuratoin.setGroups(this.readGroups());
        try {
			configuratoin.setValidators(this.readDefaultValidators());
		} catch (IOException e) {
			e.printStackTrace();
		}
        configuratoin.setValidators(this.readValidators());
        return configuratoin;
    }

    /**
     * 读取验证器
     * 
     * @Title: readDefaultValidators
     * @Description: (这里用一句话描述这个方法的作用)
     * @return Map<String,IValidator>
     * @Version v1.0
     * @author s54322/sunyue
     * @throws IOException 
     * @Date 2016年6月8日
     */
    public Map<String, IValidator> readDefaultValidators() throws IOException
    {
        Map<String, IValidator> validators = new HashMap<String, IValidator>();
        if (null != rulePaths)
        {
        	for (Resource resource : rulePaths) 
        	{
        		validators.putAll(readRules(resource.getInputStream()));
			}
        }
        return validators;
    }

    /**
     * 读取组配置
     * 
     * @Title: readGroups
     * @Description: (这里用一句话描述这个方法的作用)
     * @return Map<String,Group>
     * @Version v1.0
     * @author s54322/sunyue
     * @Date 2016年6月8日
     */
    public Map<String, Group> readGroups()
    {
        LOG.info("read validation main file , " + this.getRules());
        Map<String, Group> groups = new HashMap<String, Group>();
        SAXReader reader = null;
        Document document = null;
        try
        {
            reader = new SAXReader();
            for (Resource resource : rulePaths) {
            document = reader.read(resource.getInputStream());
            Element root = document.getRootElement();
            // 读取引入列表
//            List<Element> includeList = root.elements("include");
//            if (includeList != null && !includeList.isEmpty())
//            {
//                Iterator<Element> il = includeList.iterator();
//                while (il.hasNext())
//                {
//                    Element includeE = il.next();
//                    String file = includeE.attributeValue("file");
//                    if (StringUtils.isBlank(file))
//                    {
//                        continue;
//                    }
//                    this.readGroups(groups, file);
//                }
//            }
            this.readGroups(groups, root);
            }
        }
        catch (Exception e)
        {
            LOG.warn(e.getMessage(), e);
        }
        finally
        {
            document = null;
            reader = null;
        }
        return groups;
    }

    /**
     * 读取组配置
     * 
     * @Title: readGroups
     * @Description: (这里用一句话描述这个方法的作用)
     * @param groups
     *            Map<String, Group>
     * @param root
     *            Element
     * @Version v1.0
     * @author s54322/sunyue
     * @Date 2016年6月8日
     */
    @SuppressWarnings("unchecked")
    private void readGroups(Map<String, Group> groups, Element root)
    {
        // 读取Group
        List<Element> groupList = root.elements("group");
        Iterator<Element> gl = groupList.iterator();
        while (gl.hasNext())
        {
            Element groupE = gl.next();
            String gname = groupE.attributeValue("name").trim();

            // 读取字段
            List<Field> fields = new ArrayList<Field>();
            List<Element> fieldList = groupE.elements("field");
            Iterator<Element> fl = fieldList.iterator();
            while (fl.hasNext())
            {
                Element fieldE = fl.next();
                String fname = fieldE.attributeValue("name").trim();
                String type = fieldE.attributeValue("type").trim();
                String label = fieldE.attributeValue("label").trim();
                Field field = new Field();
                field.setName(fname);
                field.setType(type);
                field.setLabel(label);
                // 读取规则
                List<Rule> rules = new ArrayList<Rule>();
                List<Element> ruleList = fieldE.elements("rule");
                Iterator<Element> rl = ruleList.iterator();
                while (rl.hasNext())
                {
                    Element ruleE = rl.next();
                    String rname = ruleE.attributeValue("name").trim();
                    String rmessage = ruleE.elementTextTrim("message");
                    rmessage = rmessage == null ? ruleE.attributeValue("message") : rmessage;
                    Rule rule = new Rule();
                    rule.setName(rname);
                    rule.setMessage(rmessage);

                    List<Element> params = ruleE.elements("param");
                    Iterator<Element> ps = params.iterator();
                    while (ps.hasNext())
                    {
                        Element paramE = ps.next();
                        String pname = paramE.attributeValue("name").trim();
                        String pvalue = paramE.elementTextTrim("value");
                        pvalue = pvalue == null ? paramE.attributeValue("value").trim() : pvalue;
                        rule.addParameter(pname, pvalue);
                    }

                    rules.add(rule);
                }

                field.setRules(rules);
                fields.add(field);
            }
            Group group = new Group();
            group.setName(gname);
            group.setFields(fields);
            groups.put(gname, group);
        }
    }

    /**
     * 读取验证器
     * 
     * @Title: readValidators
     * @Description: (这里用一句话描述这个方法的作用)
     * @return Map<String,IValidator>
     * @Version v1.0
     * @author s54322/sunyue
     * @Date 2016年6月8日
     */
    @SuppressWarnings("unchecked")
    public Map<String, IValidator> readValidators()
    {
        Map<String, IValidator> validators = new HashMap<String, IValidator>();
        SAXReader reader = null;
        Document document = null;
        try
        {
            reader = new SAXReader();
            document = reader.read(getUrl(this.getValidators()));
            Element root = document.getRootElement();
            List<Element> validatorsList = root.elements("validator");
            Iterator<Element> vl = validatorsList.iterator();
            while (vl.hasNext())
            {
                Element validatorE = vl.next();
                String vname = validatorE.attributeValue("name").trim();
                String vclass = validatorE.attributeValue("class").trim();
                Validator v = new Validator();
                v.setName(vname);
                v.setClassName(vclass);
                try
                {
                    IValidator validator = this.instanceValidator(v);
                    validators.put(vname, validator);
                }
                catch (ClassNotFoundException e)
                {
                    continue;
                }
                catch (InstantiationException e)
                {
                    continue;
                }
                catch (IllegalAccessException e)
                {
                    continue;
                }
                finally
                {
                    v = null;
                }
            }
        }
        catch (Exception e)
        {
            LOG.warn(e.getMessage(), e);
        }
        finally
        {
            document = null;
            reader = null;
        }
        return validators;
    }

    public void setRules(String rules)
    {
        this.rules = rules;
    }

    public void setValidators(String validators)
    {
        this.validators = validators;
    }

    /**
     * 
     * @Title: getRulesUrl
     * @Description: (这里用一句话描述这个方法的作用)
     * @param path
     *            String
     * @return URL
     * @throws IOException
     *             URL
     * @Version v1.0
     * @author s54322/sunyue
     * @Date 2016年6月8日
     */
    @SuppressWarnings("deprecation")
    private URL getUrl(String path) throws IOException
    {
        URL url = null;
        if (path.startsWith("classpath"))
        {
            path = path.replaceAll("classpath:", "").replaceAll("classpath*:", "");
        }
        url = this.getClass().getResource("/" + path);
        if (null == url)
        {
            String filePath = this.getClass().getResource("/").getFile();
            String[] paths = filePath.split("/");
            filePath = "";
            for (int i = 0; i < paths.length - 2; i++)
            {
                filePath += paths[i] + File.separator;
            }
            filePath += (path.startsWith(File.separator) ? path : File.separator + path);
            File file = new File(filePath);
            url = file.toURL();
        }
        return url;
    }
    
    private Map<String, IValidator> readRules(InputStream in)
    {
    	Map<String, IValidator> validators = new HashMap<String, IValidator>();
        SAXReader reader = null;
        Document document = null;
        try
        {
            reader = new SAXReader();
            document = reader.read(in);
            Element root = document.getRootElement();
            @SuppressWarnings("unchecked")
			List<Element> validatorsList = root.elements("validator");
            Iterator<Element> vl = validatorsList.iterator();
            while (vl.hasNext())
            {
                Element validatorE = vl.next();
                String vname = validatorE.attributeValue("name").trim();
                String vclass = validatorE.attributeValue("class").trim();
                Validator v = new Validator();
                v.setName(vname);
                v.setClassName(vclass);
                try
                {
                    IValidator validator = this.instanceValidator(v);
                    validators.put(vname, validator);
                }
                catch (ClassNotFoundException e)
                {
                    continue;
                }
                catch (InstantiationException e)
                {
                    continue;
                }
                catch (IllegalAccessException e)
                {
                    continue;
                }
                finally
                {
                    v = null;
                }
            }
        }
        catch (Exception e)
        {
            LOG.warn(e.getMessage(), e);
        }
        finally
        {
            document = null;
            reader = null;
        }
        return validators;
    }
}
