package com.familyan.smarth.manager.vo;

import com.familyan.smarth.manager.domain.sms.SmsChanelDO;
import com.familyan.smarth.manager.domain.sms.SmsTemplateDO;

public class SmsTemplateVO  extends SmsTemplateDO {

	private SmsChanelDO smsChanelDO;

	public SmsChanelDO getSmsChanelDO() {
		return smsChanelDO;
	}

	public void setSmsChanelDO(SmsChanelDO smsChanelDO) {
		this.smsChanelDO = smsChanelDO;
	}
}