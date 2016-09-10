package com.familyan.smarth.manager.manager.sms;

import java.util.Date;
import java.util.Map;


public interface SmsManager {
	
	
	public long sendMessage(String mobile, long templateId, Map<String, Object> params );
	
	public long sendTimelyMessage(Date datetime, String mobile, long templateId, Map<String, Object> params );

	public String sendVoice(String mobile, long templateId, Map<String, Object> params );
}
