package com.familyan.smarth.manager.manager.sms.impl;

import java.util.*;

import com.familyan.smarth.manager.domain.sms.SmsChanelDO;
import com.familyan.smarth.manager.vo.SmsTemplateVO;
import com.familyan.smarth.manager.manager.sms.SmsChanelManager;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.familyan.smarth.manager.domain.sms.SmsTemplateDO;
import com.familyan.smarth.manager.manager.sms.SmsTemplateManager;
import com.familyan.smarth.manager.mapper.sms.SmsTemplateMapper;
import org.springframework.util.CollectionUtils;

@Service
public class SmsTemplateManagerImpl implements SmsTemplateManager {
	
	@Autowired
	private SmsTemplateMapper smsTemplateMapper;
	@Autowired
	private SmsChanelManager smsChanelManager;

	@Override
	public int add(SmsTemplateDO smsTemplate) {
		Date gmtDate = new Date();
		smsTemplate.setStatus(1);
		smsTemplate.setGmtCreate(gmtDate);
		smsTemplate.setGmtModify(gmtDate);
		return smsTemplateMapper.insert(smsTemplate);
	}

	@Override
	public int modify(SmsTemplateDO smsTemplate) {
		SmsTemplateDO smsTemplateDO = getById(smsTemplate.getId());
		smsTemplateDO.setGmtModify(new Date());
		smsTemplateDO.setName(smsTemplate.getName());
		smsTemplateDO.setChanelId(smsTemplate.getChanelId());
		smsTemplateDO.setContent(smsTemplate.getContent());
		return smsTemplateMapper.update(smsTemplateDO);
	}

	@Override
	public int remove(long id) {
		return smsTemplateMapper.delete(id);
	}

	@Override
	public int open(Long id) {
		return smsTemplateMapper.updateStatus(id, 1);
	}

	@Override
	public int close(Long id) {
		return smsTemplateMapper.updateStatus(id, 0);
	}

	@Override
	public SmsTemplateDO getById(long id) {
		return smsTemplateMapper.findById(id);
	}

	@Override
	public PageResult<List<SmsTemplateDO>> getByPage(SmsTemplateDO smsTemplateDO, int pageSize, int page) {
		Page p = new Page(page, pageSize);
		int count = smsTemplateMapper.countByPage(smsTemplateDO);
		if (count == 0 ) {
			return PageResult.emptyResult(Collections.<SmsTemplateDO>emptyList());
		}

		List<SmsTemplateDO> result = smsTemplateMapper.findByPage(smsTemplateDO, pageSize, p.getStart());

		return new PageResult<>(p.getStart(), p.getPageSize(), count, result);
	}

	@Override
	public PageResult<List<SmsTemplateVO>> getSmsTemplateVOByPage(SmsTemplateDO smsTemplateDO, int pageSize, int page) {
		PageResult<List<SmsTemplateDO>> doPageResult = getByPage(smsTemplateDO, pageSize, page);
		if(CollectionUtils.isEmpty(doPageResult.getData())) {
			return PageResult.emptyResult(Collections.<SmsTemplateVO>emptyList());
		}

		List<Long> chanelIds = new ArrayList<>();
		List<SmsTemplateVO> voList = new ArrayList<>();
		for(SmsTemplateDO smsTemplate : doPageResult.getData()) {
			chanelIds.add(smsTemplate.getChanelId());
			SmsTemplateVO smsTemplateVO = new SmsTemplateVO();
			BeanUtils.copyProperties(smsTemplate,smsTemplateVO);
			voList.add(smsTemplateVO);
		}

		List<SmsChanelDO> smsChanelDOList = smsChanelManager.getByIds(chanelIds);
		buildSmsTemplateVO(voList, smsChanelDOList);

		return new PageResult<>(doPageResult.getStart(),doPageResult.getLimit(),doPageResult.getTotal(),voList);
	}


	private void buildSmsTemplateVO(List<SmsTemplateVO> smsTemplateVOList, List<SmsChanelDO> smsChanelDOList) {
		Map<Long, SmsChanelDO> chanelDOMap = new HashMap<>();
		for(SmsChanelDO smsChanelDO : smsChanelDOList) {
			chanelDOMap.put(smsChanelDO.getId(), smsChanelDO);
		}

		for(SmsTemplateVO smsTemplateVO : smsTemplateVOList) {
			smsTemplateVO.setSmsChanelDO(chanelDOMap.get(smsTemplateVO.getChanelId()));
		}

	}
}
