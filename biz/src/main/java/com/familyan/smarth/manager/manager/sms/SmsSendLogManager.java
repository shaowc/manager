package com.familyan.smarth.manager.manager.sms;

import com.familyan.smarth.manager.domain.sms.SmsSendLogDO;
import com.familyan.smarth.manager.vo.SmsSendLogVO;
import com.lotus.service.result.PageResult;

import java.util.List;

public interface SmsSendLogManager {
	
	int add(SmsSendLogDO smsSendLog);

	PageResult<List<SmsSendLogDO>> getByPage(SmsSendLogVO smsSendLogVO, int pageSize, int page);
	
}
