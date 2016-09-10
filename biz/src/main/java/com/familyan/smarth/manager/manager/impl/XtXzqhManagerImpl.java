package com.familyan.smarth.manager.manager.impl;

import com.familyan.smarth.manager.domain.XtXzqhDO;
import com.familyan.smarth.manager.manager.XtXzqhManager;
import com.familyan.smarth.manager.mapper.XtXzqhMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2015/8/22 0022.
 */
@Service
public class XtXzqhManagerImpl implements XtXzqhManager{

    private static Map<Integer, XtXzqhDO> OLD_CODE_MAP = new HashMap<>();

    @Autowired
    private XtXzqhMapper xtXzqhMapper;

    @Override
    public XtXzqhDO getByCode(int code) {
        if(code < 100000) {
           return xtXzqhMapper.findByOldCode(code);
        }

        return xtXzqhMapper.findByCode(code);
    }

    @Override
    public List<XtXzqhDO> getByLevel(int level) {
        return xtXzqhMapper.findByLevel(level);
    }

    @Override
    public List<XtXzqhDO> getByParentCode(int parentCode) {
        return xtXzqhMapper.findByParentCode(parentCode);
    }

    @Override
    public List<XtXzqhDO> getByDisplayParentCode(int displayParentCode) {
        return xtXzqhMapper.findByDisplayParentCode(displayParentCode);
    }

    @Override
    public List<XtXzqhDO> findCodeByName(String name,int displayParentCode) {
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("displayParentCode",displayParentCode);
        List<XtXzqhDO> code = xtXzqhMapper.findCodeByName(map);
        return code == null || code.size() <= 0 ? Collections.EMPTY_LIST : code;
    }

    @Override
    public XtXzqhDO findByOldCode(Integer oldCode) {
        if(OLD_CODE_MAP.containsKey(oldCode)) {
            return OLD_CODE_MAP.get(oldCode);
        }

        XtXzqhDO xtXzqhDO = xtXzqhMapper.findByOldCode(oldCode);
        OLD_CODE_MAP.put(oldCode, xtXzqhDO);
        return xtXzqhDO;
    }

    @Override
    public List<XtXzqhDO> findByOldCodes(List<Integer> oldCodes) {
        if(oldCodes == null || oldCodes.isEmpty()) {
            return Collections.emptyList();
        }
        List<XtXzqhDO> xtXzqhDOs = new ArrayList<>();
        for(Integer oldCode : oldCodes) {
            XtXzqhDO xtXzqhDO = findByOldCode(oldCode);
            if(xtXzqhDO != null) {
                xtXzqhDOs.add(xtXzqhDO);
            }
        }

        return xtXzqhDOs;
    }
}
