package com.familyan.smarth.manager.controllers.sms;

import com.familyan.smarth.manager.util.DWZResponse;
import com.familyan.smarth.manager.domain.sms.SmsBlackListDO;
import com.familyan.smarth.manager.manager.sms.SmsBlackListManager;
import com.familyan.smarth.manager.util.DWZPage;
import com.lotus.core.web.security.Security;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Koala on 2015/8/20 0020.
 */
@Controller
@RequestMapping("sms/black")
public class SmsBlackListController {

    @Autowired
    private SmsBlackListManager smsBlackListManager;

    @RequestMapping("list")
    @Security(code = "smsblacklist:list")
    public String list(DWZPage page, SmsBlackListDO smsBlackListDO, ModelMap result){
        PageResult<List<SmsBlackListDO>> list = smsBlackListManager.getByPage(smsBlackListDO, page.getNumPerPage(), page.getPageNum());
        result.addAttribute("result",list);
        return "sms/black/list";
    }

    @RequestMapping("edit")
    @Security(code = "smsblacklist:edit")
    public String edit(@RequestParam(value = "id",defaultValue = "0")long id,
                       ModelMap result) {
        if(id !=  0) {
            SmsBlackListDO smsBlackListDO = smsBlackListManager.getById(id);
            result.addAttribute("smsBlackList",smsBlackListDO);
        }

        return "sms/black/edit";
    }

    @RequestMapping("save")
    @ResponseBody
    @Security(code = "smsblacklist:edit")
    public DWZResponse<?> save(SmsBlackListDO smsBlackListDO) {
        if(smsBlackListDO.getId() == 0) {
            smsBlackListManager.add(smsBlackListDO);
        } else {
            smsBlackListManager.modify(smsBlackListDO);
        }
        DWZResponse dwzResponse =  DWZResponse.dialogSuccess("黑名单保存成功");
        dwzResponse.setNavTabId("nav_smsblacklist:list");
        return dwzResponse;
    }

    @RequestMapping("remove")
    @ResponseBody
    @Security(code = "smsblacklist:edit")
    public DWZResponse<?> remove(Long id) {
        smsBlackListManager.remove(id);
        DWZResponse dwzResponse =  DWZResponse.navTabSuccess("成功移除黑名单", "nav_smsblacklist:list");
        return dwzResponse;
    }

}
