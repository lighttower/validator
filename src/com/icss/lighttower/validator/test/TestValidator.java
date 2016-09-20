package com.icss.lighttower.validator.test;

import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;

import com.icss.lighttower.validator.BasicValidateService;

public class TestValidator {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:applicationContext-validator.xml");
		BasicValidateService vs = (BasicValidateService) applicationContext.getBean("vs");
		vs.init();
		applicationContext.close();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("POST");
		request.addParameter("password", "123345");
		request.addParameter("passwordOne", "123345");
		request.addParameter("email", "a123345@13.cc");
		request.addParameter("locale", "zh_CN");
		Map<String, String> map = vs.validate(request, "user.validate");
		System.out.println(map);
	}
}
