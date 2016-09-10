package com.familyan.smarth.manager.manager.sms;

import java.util.Date;

import org.springframework.beans.factory.InitializingBean;

public interface SmsSender extends InitializingBean {

	public long sendMessage(String account,String password, String mobile, String msgText);
	
	public long sendTimelyMessage(Date datetime, String account,String password, String mobile, String msgText);

	public int getSendStatus(long returnValue);

	public String getBalance(String account, String password);

	/**
	 * 发送语音验证码
	 * @param account
	 * @param password
	 * @param mobile 客户手机号
	 * @param checkCode 6位数字验证码
	 * @return
	 */
	public String sendVoice(String account,String password, String mobile, String checkCode);
	
}
