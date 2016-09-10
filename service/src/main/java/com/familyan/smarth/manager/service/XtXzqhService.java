package com.familyan.smarth.manager.service;

import com.familyan.smarth.manager.domain.XtXzqhDTO;

import java.util.List;
import java.util.Map;

public interface XtXzqhService {
	
	public String getAddressById(Integer provinceId,Integer cityId,Integer districtId);

	XtXzqhDTO findByOldCode(Integer oldCode);

	Map<Integer, XtXzqhDTO> findByOldCodes(List<Integer> oldCodes);

	XtXzqhDTO findByCode(Integer code);

	List<XtXzqhDTO> getByDisplayParentCode(int displayParentCode);

}
