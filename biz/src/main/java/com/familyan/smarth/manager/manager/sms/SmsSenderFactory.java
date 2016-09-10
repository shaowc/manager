package com.familyan.smarth.manager.manager.sms;

import java.util.HashMap;
import java.util.Map;

public class SmsSenderFactory {
	
	public static Map<Integer, SmsSender> SMS_SENDER_MAP = new HashMap<Integer, SmsSender>();
	
	public static SmsSender getSmsSender(int chanelProvider) {
		return SMS_SENDER_MAP.get(chanelProvider);
	} 

}
