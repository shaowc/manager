package com.familyan.smarth.manager.manager.sms.impl;

import com.jianzhou.sdk.BusinessService;
import com.familyan.smarth.manager.manager.sms.SmsSender;
import com.familyan.smarth.manager.manager.sms.SmsSenderFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class JianZhouSmsSender implements SmsSender {

	private static String SIGNATURE = "【淘材库】";
	
	@Override
	public long sendMessage(String account,String password, String mobile, String msgText) {
		msgText = msgText + SIGNATURE;
		BusinessService bs = new BusinessService();
		bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
		long returnValue = bs.sendBatchMessage(account, password, mobile, msgText);
		return returnValue;
	}

	@Override
	public long sendTimelyMessage(Date datetime, String account, String password, String mobile, String msgText) {
		BusinessService bs = new BusinessService();
		bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sendDateTime = sdf.format(datetime);
		long returnValue = bs.sendTimelyMessage(account, password, sendDateTime, mobile, msgText);
		return returnValue;
	}

	@Override
	public int getSendStatus(long returnValue) {
		if(returnValue > 0) {
			return 1;
		}

		return 3;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		SmsSenderFactory.SMS_SENDER_MAP.put(1, this);
	}

	@Override
	public String getBalance(String account, String password) {
		BusinessService bs = new BusinessService();
		bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
		String result = bs.getUserInfo(account, password) ;
		try {
			Document doc = DocumentHelper.parseText(result);
			Element root = doc.getRootElement();
			String remainFee = root.elementText("remainFee");
			return remainFee;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return "未知";
	}

	/**
	 * 发送语音验证码
	 *
	 * @param account
	 * @param password
	 * @param mobile    客户手机号
	 * @param checkCode 6位数字验证码
	 * @return
	 */
	@Override
	public String sendVoice(String account, String password, String mobile, String checkCode) {
		return null;
	}
}
