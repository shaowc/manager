package com.familyan.smarth.manager.manager.sms;

import com.familyan.smarth.manager.domain.sms.SmsTemplateDO;
import com.familyan.smarth.manager.vo.SmsTemplateVO;
import com.lotus.service.result.PageResult;

import java.util.List;

public interface SmsTemplateManager {
	
	int add(SmsTemplateDO smsTemplateDO);
	
	int modify(SmsTemplateDO smsTemplateDO);
	
	int remove(long id);

	int open(Long id);

	int close(Long id);
	
	SmsTemplateDO getById(long id);
	
	PageResult<List<SmsTemplateDO>> getByPage(SmsTemplateDO smsTemplateDO, int pageSize, int page);

	PageResult<List<SmsTemplateVO>> getSmsTemplateVOByPage(SmsTemplateDO smsTemplateDO, int pageSize, int page);

}
