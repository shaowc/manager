package com.familyan.smarth.manager.manager.sms.impl;

import cn.b2m.eucp.sdkhttp.SDKClient;
import cn.b2m.eucp.sdkhttp.SDKServiceLocator;
import com.familyan.smarth.manager.manager.sms.SmsSender;
import com.familyan.smarth.manager.manager.sms.SmsSenderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class YiMeiSmsSender implements SmsSender {

	private static String SIGNATURE = "【淘材库】";
	@Value("${yimei.service.address}")
	private String sdkServiceAddress;

	SDKClient sdkClient = null;

	@Override
	public long sendMessage(String account,String password, String mobile, String msgText) {
		if (!msgText.startsWith(SIGNATURE)) {
			msgText = SIGNATURE + msgText;
		}
		String[] mobiles = mobile.split(";");
		long returnValue = 0;
		try {
			returnValue = sdkClient.sendSMS(account, password, "", mobiles, msgText, "", "gbk", 5, 0);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		return returnValue;
	}

	@Override
	public long sendTimelyMessage(Date datetime, String account, String password, String mobile, String msgText) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sendDateTime = sdf.format(datetime);
		if (!msgText.startsWith(SIGNATURE)) {
			msgText = SIGNATURE + msgText;
		}

		String[] mobiles = mobile.split(";");
		long returnValue = 0;
		try {
			returnValue = sdkClient.sendSMS(account, password, sendDateTime, mobiles, msgText, "", "gbk", 5, 0);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		return returnValue;
	}

	@Override
	public int getSendStatus(long returnValue) {

		if(returnValue == 0) {
			return 1;
		}

		return 3;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			sdkClient = new SDKServiceLocator().getSDKService(sdkServiceAddress);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		SmsSenderFactory.SMS_SENDER_MAP.put(2, this);
	}

	@Override
	public String getBalance(String account, String password) {
		try {
			double eachFee = sdkClient.getEachFee(account,password);
			double balance = sdkClient.getBalance(account, password);
			return String.valueOf(Math.floor(balance/eachFee));
		} catch (RemoteException e) {
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
		String result = "0:0";
		try {
			result = sdkClient.sendVoice(account, password, null, new String[]{mobile}, checkCode, null, "GBK", 5, 0);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
