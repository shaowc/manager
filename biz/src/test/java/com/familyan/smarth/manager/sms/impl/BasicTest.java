package com.familyan.smarth.manager.sms.impl;

import com.familyan.smarth.manager.domain.sms.SmsVerifyCodeDO;
import com.familyan.smarth.manager.mapper.sms.SmsVerifyCodeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-beans.xml"})
public class BasicTest {

	@Autowired
	private SmsVerifyCodeMapper smsVerifyCodeMapper;
	
	@Test
	public void foo() {
		SmsVerifyCodeDO smsVerifyCodeDO = smsVerifyCodeMapper.findByIdentifier("1nymat");
		System.out.println(smsVerifyCodeDO);

	}

}
