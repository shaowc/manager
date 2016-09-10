package com.familyan.smarth.manager.service.impl;

import com.familyan.smarth.manager.domain.XtXzqhDTO;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.familyan.smarth.manager.domain.XtXzqhDO;
import com.familyan.smarth.manager.manager.XtXzqhManager;
import com.familyan.smarth.manager.service.XtXzqhService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class XtXzqhServiceImpl implements XtXzqhService {

	@Autowired
	private XtXzqhManager xtXzqhManager;
	@Override
	public String getAddressById(Integer provinceId, Integer cityId,
			Integer districtId){
		XtXzqhDO xtXzqhDO = null;
		String address = "";
//		if(provinceId != null){
			xtXzqhDO = xtXzqhManager.getByCode(provinceId);
		if (xtXzqhDO != null) {
			address += xtXzqhDO.getName();
		}
//		}
		if(cityId != null){
			xtXzqhDO = xtXzqhManager.getByCode(cityId);
			if (xtXzqhDO != null) {
				address += xtXzqhDO.getName();
			}
		}
		if(districtId != null){
			xtXzqhDO = xtXzqhManager.getByCode(districtId);
			if (xtXzqhDO != null) {
				address += xtXzqhDO.getName();
			}
		}
		return address;
	}

	@Override
	public XtXzqhDTO findByOldCode(Integer oldCode) {
		XtXzqhDO xtXzqhDO = xtXzqhManager.findByOldCode(oldCode);
		if(xtXzqhDO == null) {
			return null;
		}

		XtXzqhDTO xtXzqhDTO = new XtXzqhDTO();
		BeanUtils.copyProperties(xtXzqhDO, xtXzqhDTO);
		return xtXzqhDTO;
	}

	@Override
	public Map<Integer, XtXzqhDTO> findByOldCodes(List<Integer> oldCodes) {
		List<XtXzqhDO> xtXzqhDOs = xtXzqhManager.findByOldCodes(oldCodes);
		if(xtXzqhDOs.isEmpty()) {
			return Collections.emptyMap();
		}

		List<XtXzqhDTO> list = new ArrayList<>();
		for(XtXzqhDO xtXzqhDO : xtXzqhDOs) {
			XtXzqhDTO xtXzqhDTO = new XtXzqhDTO();
			BeanUtils.copyProperties(xtXzqhDO, xtXzqhDTO);
			list.add(xtXzqhDTO);
		}

		Map<Integer, XtXzqhDTO> map = Maps.uniqueIndex(list, new Function<XtXzqhDTO, Integer>() {
			@Override
			public Integer apply(XtXzqhDTO input) {
				return input.getOldCode();
			}
		});

		return map;
	}

	@Override
	public XtXzqhDTO findByCode(Integer code) {
		XtXzqhDO xtXzqhDO = xtXzqhManager.getByCode(code);
		if(xtXzqhDO == null) {
			return null;
		}

		XtXzqhDTO xtXzqhDTO = new XtXzqhDTO();
		BeanUtils.copyProperties(xtXzqhDO, xtXzqhDTO);
		return xtXzqhDTO;
	}

	@Override
	public List<XtXzqhDTO> getByDisplayParentCode(int displayParentCode) {
		List<XtXzqhDO> doList = xtXzqhManager.getByDisplayParentCode(displayParentCode);
		if (doList.isEmpty()) {
			return Collections.emptyList();
		}
		List<XtXzqhDTO> dtoList = new ArrayList<>(doList.size());
		for (XtXzqhDO xtXzqhDO : doList) {
			XtXzqhDTO dto = new XtXzqhDTO();
			BeanUtils.copyProperties(xtXzqhDO, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}
}
