package com.familyan.smarth.manager.controllers.sms;

import com.familyan.smarth.manager.domain.LoginEmployee;
import com.familyan.smarth.manager.domain.sms.SmsChanelDO;
import com.familyan.smarth.manager.util.DWZResponse;
import com.familyan.smarth.manager.manager.sms.SmsChanelManager;
import com.familyan.smarth.manager.util.DWZPage;
import com.lotus.core.web.security.Security;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2015/8/19 0019.
 */
@Controller
@RequestMapping("sms/chanel")
public class SmsChanelController {

    @Autowired
    private SmsChanelManager smsChanelManager;

    @RequestMapping("list")
    @Security(code = "smschanel:list")
    public String list(DWZPage page, ModelMap result) {
        Page p = new Page(page.getPageNum(),page.getNumPerPage());
        PageResult<List<SmsChanelDO>> list = smsChanelManager.getByPage(p);
        result.addAttribute("result",list);
        return "sms/chanel/list";

    }

    @RequestMapping("edit")
    @Security(code = "smschanel:edit")
    public String edit(@RequestParam(value = "id",defaultValue = "0")long id,
        ModelMap result) {
        if(id !=  0) {
            SmsChanelDO smsChanelDO = smsChanelManager.getById(id);
            result.addAttribute("smsChanel",smsChanelDO);
        }

        return "sms/chanel/edit";
    }

    @RequestMapping("save")
    @ResponseBody
    @Security(code = "smschanel:edit")
    public DWZResponse<?> save(SmsChanelDO smsChanelDO, LoginEmployee loginEmployee) {
        if(smsChanelDO.getId() == 0) {
            smsChanelDO.setCreateUser(loginEmployee.getId());
            smsChanelDO.setModifyUser(loginEmployee.getId());
            smsChanelManager.add(smsChanelDO);
        } else {
            smsChanelDO.setModifyUser(loginEmployee.getId());
            smsChanelManager.modify(smsChanelDO);
        }

        DWZResponse dwzResponse = DWZResponse.dialogSuccess("成功保存短信通道");
        dwzResponse.setNavTabId("nav_smschanel:list");
        return dwzResponse;
    }


}
