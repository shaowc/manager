package com.familyan.smarth.manager.manager.sms;

import com.familyan.smarth.manager.domain.sms.SmsChanelDO;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;

import java.util.List;

public interface SmsChanelManager {

	int add(SmsChanelDO smsChanel);
	
	int modify(SmsChanelDO smsChanel);
	
	SmsChanelDO getById(long id);

	List<SmsChanelDO> getAll();

	PageResult<List<SmsChanelDO>> getByPage(Page page);

	List<SmsChanelDO> getByIds(List<Long> ids);
	
}
