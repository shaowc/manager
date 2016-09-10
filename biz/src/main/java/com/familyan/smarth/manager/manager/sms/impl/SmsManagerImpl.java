package com.familyan.smarth.manager.manager.sms.impl;

import java.io.StringWriter;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.familyan.smarth.manager.domain.sms.SmsChanelDO;
import com.familyan.smarth.manager.domain.sms.SmsSendLogDO;
import com.familyan.smarth.manager.domain.sms.SmsTemplateDO;
import com.familyan.smarth.manager.manager.sms.*;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class SmsManagerImpl implements SmsManager {

	@Autowired
	private SmsSendLogManager smsSendLogManager;
	@Autowired
	private SmsTemplateManager smsTemplateManager;
	@Autowired
	private SmsChanelManager smsChanelManager;
	@Autowired
	private SmsBlackListManager smsBlackListManager;

	private Logger logger = Logger.getLogger(SmsManagerImpl.class);

	@Override
	public long sendMessage(String mobile, long templateId,	Map<String, Object> params) {
		return sendInternal(mobile, templateId, params, null);
	}

	@Override
	public long sendTimelyMessage(Date datetime, String mobile,	long templateId, Map<String, Object> params) {
		return sendInternal(mobile, templateId, params, datetime);
	}

	@Override
	public String sendVoice(String mobile, long templateId, Map<String, Object> params) {
		Pattern pattern = Pattern.compile("^1\\d{10}$");
		Matcher matcher = pattern.matcher(mobile);
		if(StringUtils.isEmpty(mobile) || !matcher.matches()) {
			// 短信黑名单用户，不发送短信
			SmsSendLogDO smsSendLog = getSmsSendLogWhenSysError(mobile, templateId, -9004, "非法手机号，不发送短信");
			smsSendLogManager.add(smsSendLog);
			logger.error("非法手机号，不发送短信");
			return String.valueOf(-9004);
		}

		if(smsBlackListManager.getByMobile(mobile) != null) {
			// 短信黑名单用户，不发送短信
			SmsSendLogDO smsSendLog = getSmsSendLogWhenSysError(mobile, templateId, -9000, "短信黑名单用户，禁发短信");
			smsSendLogManager.add(smsSendLog);
			logger.error("短信黑名单用户，不发送短信");
			return String.valueOf(-9000);
		}

		SmsTemplateDO smsTemplate = smsTemplateManager.getById(templateId);
		// 模板不存在
		if(smsTemplate == null) {
			SmsSendLogDO smsSendLog = getSmsSendLogWhenSysError(mobile, templateId, -9001, "模板不存在");
			smsSendLogManager.add(smsSendLog);
			return String.valueOf(-9001);
		}

		if(smsTemplate.getStatus() != 1) {
			SmsSendLogDO smsSendLogDO = getSmsSendLogWhenSysError(mobile, templateId, -9006, "模板已被禁用，不发送短信");
			smsSendLogManager.add(smsSendLogDO);
			return String.valueOf(-9006);
		}

		// 通道不存在
		SmsChanelDO smsChanel = smsChanelManager.getById(smsTemplate.getChanelId());
		if(smsChanel == null) {
			SmsSendLogDO smsSendLog = getSmsSendLogWhenSysError(mobile, templateId, -9002, "无效模板，所用短信通道不存在");
			smsSendLogManager.add(smsSendLog);
			return String.valueOf(-9002);
		}

		String content = null;
		try {
			content = compileTemplate(smsTemplate, params);
		} catch(Exception e) {
			// 模板参数设置错误
			SmsSendLogDO smsSendLog = getSmsSendLogWhenSysError(mobile, templateId, -9003, "模板参数设置错误");
			smsSendLogManager.add(smsSendLog);
			return String.valueOf(-9003);
		}

		SmsSender smsSender = SmsSenderFactory.getSmsSender(smsChanel.getChanelProvider());
		String returnValue = "0";
		try {
				returnValue = smsSender.sendVoice(smsChanel.getAccount(), smsChanel.getPassword(), mobile, String.valueOf(params.get("verifyCode")));
		} catch (Exception e) {
			e.printStackTrace();
			// 接口调用失败
			SmsSendLogDO smsSendLog = getSmsSendLogWhenSysError(mobile, templateId, -9005, content);
			smsSendLog.setChanelProvider(smsChanel.getChanelProvider());
			smsSendLog.setChanelId(smsChanel.getId());
			smsSendLog.setChanelAccount(smsChanel.getAccount());
			smsSendLogManager.add(smsSendLog);
			logger.error("调用短信接口失败");
			return String.valueOf(-9005);
		}
		if(returnValue.startsWith("0:") || returnValue.startsWith("0")){
			returnValue = "0";
		}
		logger.debug("平台返回值：" + returnValue);
		SmsSendLogDO smsSendLog = new SmsSendLogDO(mobile, templateId, returnValue, content);
		smsSendLog.setStatus(returnValue.equals("0")?1:0);
		smsSendLog.setChanelProvider(smsChanel.getChanelProvider());
		smsSendLog.setChanelId(smsChanel.getId());
		smsSendLog.setChanelAccount(smsChanel.getAccount());
		smsSendLogManager.add(smsSendLog);
		return returnValue;
	}

	private long sendInternal(String mobile, long templateId,	Map<String, Object> params, Date datetime) {

		Pattern pattern = Pattern.compile("^1\\d{10}$");
		Matcher matcher = pattern.matcher(mobile);
		if(StringUtils.isEmpty(mobile) || !matcher.matches()) {
			// 短信黑名单用户，不发送短信
			SmsSendLogDO smsSendLog = getSmsSendLogWhenSysError(mobile, templateId, -9004, "非法手机号，不发送短信");
			smsSendLogManager.add(smsSendLog);
			logger.error("非法手机号，不发送短信");
			return -9004;
		}

		if(smsBlackListManager.getByMobile(mobile) != null) {
			// 短信黑名单用户，不发送短信
			SmsSendLogDO smsSendLog = getSmsSendLogWhenSysError(mobile, templateId, -9000, "短信黑名单用户，禁发短信");
			smsSendLogManager.add(smsSendLog);
			logger.error("短信黑名单用户，不发送短信");
			return -9000;
		}

		SmsTemplateDO smsTemplate = smsTemplateManager.getById(templateId);
		// 模板不存在
		if(smsTemplate == null) {
			SmsSendLogDO smsSendLog = getSmsSendLogWhenSysError(mobile, templateId, -9001, "模板不存在");
			smsSendLogManager.add(smsSendLog);
			return -9001;
		}

		if(smsTemplate.getStatus() != 1) {
			SmsSendLogDO smsSendLogDO = getSmsSendLogWhenSysError(mobile, templateId, -9006, "模板已被禁用，不发送短信");
			smsSendLogManager.add(smsSendLogDO);
			return -9006;
		}

		// 通道不存在
		SmsChanelDO smsChanel = smsChanelManager.getById(smsTemplate.getChanelId());
		if(smsChanel == null) {
			SmsSendLogDO smsSendLog = getSmsSendLogWhenSysError(mobile, templateId, -9002, "无效模板，所用短信通道不存在");
			smsSendLogManager.add(smsSendLog);
			return -9002;
		}

		String content = null;
		try {
			content = compileTemplate(smsTemplate, params);
		} catch(Exception e) {
			// 模板参数设置错误
			SmsSendLogDO smsSendLog = getSmsSendLogWhenSysError(mobile, templateId, -9003, "模板参数设置错误");
			smsSendLogManager.add(smsSendLog);
			return -9003;
		}

		SmsSender smsSender = SmsSenderFactory.getSmsSender(smsChanel.getChanelProvider());
		long returnValue = 0;
		try {
			if (datetime == null) {
				returnValue = smsSender.sendMessage(smsChanel.getAccount(), smsChanel.getPassword(), mobile, content);
			} else {
				returnValue = smsSender.sendTimelyMessage(datetime, smsChanel.getAccount(), smsChanel.getPassword(), mobile, content);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 接口调用失败
			SmsSendLogDO smsSendLog = getSmsSendLogWhenSysError(mobile, templateId, -9005, content);
			smsSendLog.setChanelProvider(smsChanel.getChanelProvider());
			smsSendLog.setChanelId(smsChanel.getId());
			smsSendLog.setChanelAccount(smsChanel.getAccount());
			smsSendLogManager.add(smsSendLog);
			logger.error("调用短信接口失败");
			return -9005;
		}

		logger.debug("平台返回值：" + returnValue);
		SmsSendLogDO smsSendLog = new SmsSendLogDO(mobile, templateId, String.valueOf(returnValue), content);
		smsSendLog.setStatus(smsSender.getSendStatus(returnValue));
		smsSendLog.setChanelProvider(smsChanel.getChanelProvider());
		smsSendLog.setChanelId(smsChanel.getId());
		smsSendLog.setChanelAccount(smsChanel.getAccount());
		smsSendLogManager.add(smsSendLog);
		return returnValue;
	}

	private String compileTemplate(SmsTemplateDO smsTemplate,	Map<String, Object> params ){
		VelocityEngine ve = new VelocityEngine();
		ve.init();
		String conent = smsTemplate.getContent();
		VelocityContext vc = new VelocityContext();
		// 设置参数
		if(params != null ){
			for(String key : params.keySet()) {
				vc.put(key, params.get(key));
			}
		}
		StringWriter writer = new StringWriter();
		ve.evaluate(vc, writer, "DEBUG", conent);
		String msgText = writer.toString();
		return msgText;

	}

	private SmsSendLogDO getSmsSendLogWhenSysError(String mobile, long templateId, long statusCode, String content) {
		SmsSendLogDO smsSendLog = new SmsSendLogDO();
		smsSendLog.setMobile(mobile);
		smsSendLog.setTemplateId(templateId);
		Date now = new Date();
		smsSendLog.setSendTime(now);
		smsSendLog.setGmtCreate(now);
		smsSendLog.setGmtModified(now);
		smsSendLog.setReturnValue(String.valueOf(statusCode));

		if(!StringUtils.isEmpty(content)) {
			smsSendLog.setContent(content);
		}

		// 系统错误
		smsSendLog.setStatus(2);
		return smsSendLog;

	}
	

}
