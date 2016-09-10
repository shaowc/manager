package com.familyan.smarth.manager.manager.sms.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.familyan.smarth.manager.domain.sms.SmsChanelDO;
import com.familyan.smarth.manager.manager.sms.SmsSenderFactory;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.familyan.smarth.manager.manager.sms.SmsChanelManager;
import com.familyan.smarth.manager.mapper.sms.SmsChanelMapper;

@Service
public class SmsChanelManagerImpl implements SmsChanelManager {
	
	@Autowired
	private SmsChanelMapper smsChanelMapper;

	@Override
	public int add(SmsChanelDO smsChanel) {
		Date gmtDate = new Date();
		smsChanel.setGmtCreate(gmtDate);
		smsChanel.setGmtModify(gmtDate);
		return smsChanelMapper.insert(smsChanel);
	}

	@Override
	public int modify(SmsChanelDO smsChanel) {
		SmsChanelDO smsChanelDO = smsChanelMapper.findById(smsChanel.getId());
		smsChanelDO.setName(smsChanel.getName());
		smsChanelDO.setChanelProvider(smsChanel.getChanelProvider());
		smsChanelDO.setAccount(smsChanel.getAccount());
		smsChanelDO.setPassword(smsChanel.getPassword());
		smsChanelDO.setModifyUser(smsChanel.getModifyUser());
		smsChanelDO.setGmtModify(new Date());
		return smsChanelMapper.update(smsChanelDO);
	}

	@Override
	public SmsChanelDO getById(long id) {
		return smsChanelMapper.findById(id);
	}

	@Override
	public List<SmsChanelDO> getAll() {
		return smsChanelMapper.findAll();
	}

	@Override
	public List<SmsChanelDO> getByIds(List<Long> ids) {
		return smsChanelMapper.findByChanelIds(ids);
	}

	@Override
	public PageResult<List<SmsChanelDO>> getByPage(Page page) {
		int count = smsChanelMapper.countAll();

		if(count == 0) {
			return PageResult.emptyResult(Collections.<SmsChanelDO>emptyList());
		}

		List<SmsChanelDO> result = smsChanelMapper.findByPage(page.getPageSize(), page.getStart());
		for(SmsChanelDO smsChanelDO : result) {
			smsChanelDO.setBalance(SmsSenderFactory.getSmsSender(smsChanelDO.getChanelProvider()).getBalance(smsChanelDO.getAccount(),smsChanelDO.getPassword()));
		}

		return new PageResult(page.getStart(), page.getPageSize(), count, result);
	}
}
