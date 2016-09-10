package com.familyan.smarth.manager.manager.sms.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.familyan.smarth.manager.domain.sms.SmsSendLogDO;
import com.familyan.smarth.manager.vo.SmsSendLogVO;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.familyan.smarth.manager.manager.sms.SmsSendLogManager;
import com.familyan.smarth.manager.mapper.sms.SmsSendLogMapper;

@Service
public class SmsSendLogManagerImpl implements SmsSendLogManager {
	
	@Autowired
	private SmsSendLogMapper smsSendLogMapper;

	@Override
	public int add(SmsSendLogDO smsSendLog) {
		Date gmtNow = new Date();
		smsSendLog.setGmtCreate(gmtNow);
		smsSendLog.setGmtModified(gmtNow);
		return smsSendLogMapper.insert(smsSendLog);
	}

	@Override
	public PageResult<List<SmsSendLogDO>> getByPage(SmsSendLogVO smsSendLogVO, int pageSize, int page) {
		Page p = new Page(page, pageSize);
		int count = smsSendLogMapper.countByPage(smsSendLogVO);
		if(count == 0) {
			return PageResult.emptyResult(Collections.<SmsSendLogDO>emptyList());
		}

		List<SmsSendLogDO> result = smsSendLogMapper.findByPage(smsSendLogVO, pageSize, p.getStart());
		return new PageResult<>(p.getStart(), p.getPageSize(), count, result);
	}
}
