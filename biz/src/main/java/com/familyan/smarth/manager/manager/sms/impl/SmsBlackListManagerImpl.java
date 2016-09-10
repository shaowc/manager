package com.familyan.smarth.manager.manager.sms.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.familyan.smarth.manager.domain.sms.SmsBlackListDO;
import com.familyan.smarth.manager.manager.sms.SmsBlackListManager;
import com.familyan.smarth.manager.mapper.sms.SmsBlackListMapper;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SmsBlackListManagerImpl implements SmsBlackListManager {

	@Autowired
	private SmsBlackListMapper smsBlackListMapper;
	
	@Override
	public int add(SmsBlackListDO smsBlackList) {
		SmsBlackListDO exist = smsBlackListMapper.findByMobile(smsBlackList.getMobile());
		if(exist != null) {
			return 0;
		}

		Date gmtDate = new Date();
		smsBlackList.setGmtCreate(gmtDate);
		smsBlackList.setGmtModified(gmtDate);
		return smsBlackListMapper.insert(smsBlackList);
	}

	@Override
	public int modify(SmsBlackListDO smsBlackList) {
		SmsBlackListDO exist = smsBlackListMapper.findByMobile(smsBlackList.getMobile());
		if(exist != null && smsBlackList.getId() != exist.getId()) {
			return 0;
		}
		
		SmsBlackListDO sbl = getById(smsBlackList.getId());
		sbl.setMobile(smsBlackList.getMobile());
		sbl.setReason(smsBlackList.getReason());
		sbl.setStatus(smsBlackList.getStatus());
		smsBlackList.setGmtModified(new Date());
		return smsBlackListMapper.update(sbl);
	}

	@Override
	public int remove(long id) {
		return smsBlackListMapper.delete(id);
	}

	@Override
	public SmsBlackListDO getById(long id) {
		return smsBlackListMapper.findById(id);
	}

	@Override
	public SmsBlackListDO getByMobile(String mobile) {
		return smsBlackListMapper.findByMobile(mobile);
	}

	@Override
	public PageResult<List<SmsBlackListDO>> getByPage(SmsBlackListDO smsBlackListDO,int pageSize, int page) {
		Page p = new Page(page, pageSize);
		int count = smsBlackListMapper.countByPage(smsBlackListDO);
		if (count == 0) {
			return PageResult.emptyResult(Collections.<SmsBlackListDO>emptyList());
		}

		List<SmsBlackListDO> result = smsBlackListMapper.findByPage(smsBlackListDO, pageSize, p.getStart());
		return new PageResult<>(p.getStart(), pageSize, count, result);
	}

}
