package com.familyan.smarth.manager.controllers.sms;

import com.familyan.smarth.manager.domain.sms.SmsSendLogDO;
import com.familyan.smarth.manager.manager.sms.SmsSendLogManager;
import com.familyan.smarth.manager.util.DWZPage;
import com.familyan.smarth.manager.vo.SmsSendLogVO;
import com.lotus.core.web.security.Security;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Koala on 2015/8/20 0020.
 */
@Controller
@RequestMapping("sms/log")
public class SmsSendLogController {

    @Autowired
    private SmsSendLogManager smsSendLogManager;

    @InitBinder
    public  void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),true));
    }

    @RequestMapping("list")
    @Security(code = "smssendlog:list")
    public String list(DWZPage page, SmsSendLogVO smsSendLogVO, ModelMap result){
        PageResult<List<SmsSendLogDO>> list = smsSendLogManager.getByPage(smsSendLogVO, page.getNumPerPage(), page.getPageNum());
        result.addAttribute("result",list);
        return "sms/log/list";
    }

}
