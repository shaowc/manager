package com.familyan.smarth.manager.controllers;

import com.familyan.smarth.manager.domain.XtXzqhDO;
import com.familyan.smarth.manager.manager.XtXzqhManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Koala on 2015/8/22 0022.
 */
@Controller
@RequestMapping("/xt/xzqh")
public class XtXzqhController {

    @Autowired
    private XtXzqhManager xtXzqhManager;

    @RequestMapping("getByParentCode")
    @ResponseBody
        public String[][] getByParentCode(int displayLevel, int parentCode){

        if(0 == parentCode) {
            return getEmptyResult(displayLevel);
        }

        List<XtXzqhDO> xtXzqhDOList = xtXzqhManager.getByDisplayParentCode(parentCode);
        if(xtXzqhDOList.isEmpty()) {
            return getEmptyResult(displayLevel);
        }
        String[][] areaArr = new String[xtXzqhDOList.size()+1][];
        XtXzqhDO xtXzqhDO = xtXzqhManager.getByCode(parentCode);
        if(xtXzqhDO.getDisplayLevel() == 1) {
            areaArr[0] = new String[]{"0","所有城市"};
        } else if(xtXzqhDO.getDisplayLevel() == 2) {
            areaArr[0] = new String[]{"0","所有区县"};
        }
        for(int i = 0 ; i< xtXzqhDOList.size(); i++) {
            XtXzqhDO xtXzqh = xtXzqhDOList.get(i);
            String[] str = new String[2];
            str[0] = String.valueOf(xtXzqh.getCode());
            str[1] = xtXzqh.getName();
            areaArr[i+1] = str;
        }

        return areaArr;
    }

    private String[][] getEmptyResult(int displayLevel) {
        if(displayLevel == 1) {
            return new String[][]{{"0","所有省份"}};
        } else if(displayLevel == 2) {
            return new String[][]{{"0","所有城市"}};
        } else if(displayLevel == 3) {
            return new String[][]{{"0","所有区县"}};
        }

        return null;
    }

}
