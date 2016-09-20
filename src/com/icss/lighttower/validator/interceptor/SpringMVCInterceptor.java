package com.icss.lighttower.validator.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.icss.lighttower.validator.BasicValidateService;
import com.icss.lighttower.validator.annotation.Valid;
import com.icss.lighttower.validator.exception.ValidatorException;

/**
 * <pre>
 * 1.
 * 	在controller上加入com.icss.lighttower.validator.annotation.@Valid(group="demo.user.validator")注解
 * 2.
	&lt;bean id="config"
		class="com.icss.lighttower.validator.config.BasicValidateConfig"&gt;
		&lt;property name="rulePaths" value="classpath:/validator-rules/**.xml" /&gt;
	&lt;/bean&gt;

	&lt;bean id="vs" class="com.icss.lighttower.validator.BasicValidateService"&gt;
		&lt;property name="config" ref="config" /&gt;
		&lt;property name="messageSource" ref="messageSource" /&gt;
	&lt;/bean&gt;
	&lt;mvc:interceptors&gt;
		&lt;!-- 使用bean定义一个Interceptor，直接定义在mvc:interceptors根下面的Interceptor将拦截所有的请求 
			&lt;bean class="com.host.app.web.interceptor.AllInterceptor"/&gt; --&gt;
		&lt;mvc:interceptor&gt;
			&lt;mvc:mapping path="/api/*" /&gt;
			&lt;!-- 定义在mvc:interceptor下面的表示是对特定的请求才进行拦截的 --&gt;
			&lt;bean
				class="com.icss.lighttower.validator.interceptor.SpringMVCInterceptor"&gt;
				&lt;property name="vs" ref="vs"&gt;&lt;/property&gt;
			&lt;/bean&gt;
		&lt;/mvc:interceptor&gt;
	&lt;/mvc:interceptors&gt;
	&lt;bean id="messageSource"
		class="org.springframework.context.support.ReloadableResourceBundleMessageSource"&gt;
		&lt;property name="defaultEncoding" value="utf-8" /&gt;
		&lt;property name="useCodeAsDefaultMessage" value="true" /&gt;
		&lt;property name="cacheSeconds" value="10"&gt;&lt;/property&gt;
		&lt;property name="basenames"&gt;
			&lt;list&gt;
				&lt;value&gt;classpath:validator-messages/message&lt;/value&gt;
			&lt;/list&gt;
		&lt;/property&gt;
 * </pre>
 * 
 * @author mengqingfeng01@chinasoftinc.com
 *
 */
public class SpringMVCInterceptor implements HandlerInterceptor
{
    /**
     * 验证服务
     */
    private BasicValidateService vs;
    /**
     * 验证失败是否抛出异常
     */
    private boolean throwableException = false;

    public boolean isThrowableException()
    {
        return throwableException;
    }

    public void setThrowableException(boolean throwableException)
    {
        this.throwableException = throwableException;
    }

    public BasicValidateService getVs()
    {
        return vs;
    }

    public void setVs(BasicValidateService vs)
    {
        this.vs = vs;
    }

    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用， SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行， 而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的， 这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 因为是注解到method上的，所以首先要获取这个方法
        Method method = handlerMethod.getMethod();
        // 判断这个方法上是否有这个注解
        if (method.isAnnotationPresent(Valid.class))
        {
            // 如果有这个注解，则获取注解类
            Valid valid = method.getAnnotation(Valid.class);
            Map<String, String> map = vs.validate(request, valid.group());
            if (null != map && !map.isEmpty())
            {
                if (Boolean.TRUE.equals(throwableException))
                {
                    // 'message':'{starttime:{1}在{2}和{3}之间,endtime:结束时间不能小于开始时间}'
                    throw new ValidatorException(map.toString());
                }
                response.getWriter().write(map.toString());
                return false;
            }
        }
        return true;
    }

    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的， 它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行， 也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用， 这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法， Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前， 要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception
    {
        // TODO Auto-generated method stub

    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后， 也就是DispatcherServlet渲染了视图执行，
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception
    {
        // TODO Auto-generated method stub

    }

}
