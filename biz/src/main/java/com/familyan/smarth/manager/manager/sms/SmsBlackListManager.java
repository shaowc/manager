package com.familyan.smarth.manager.manager.sms;

import com.familyan.smarth.manager.domain.sms.SmsBlackListDO;
import com.lotus.service.result.PageResult;

import java.util.List;

public interface SmsBlackListManager {
	
	int add(SmsBlackListDO smsBlackList);
	
	int modify(SmsBlackListDO smsBlackList);
	
	int remove(long id);
	
	SmsBlackListDO getById(long id);

	public SmsBlackListDO getByMobile(String mobile);
	
	PageResult<List<SmsBlackListDO>> getByPage(SmsBlackListDO smsBlackListDO, int pageSize, int page);
	
}
