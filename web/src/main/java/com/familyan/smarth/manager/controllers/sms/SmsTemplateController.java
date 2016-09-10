package com.familyan.smarth.manager.controllers.sms;

import com.familyan.smarth.manager.domain.sms.SmsChanelDO;
import com.familyan.smarth.manager.domain.sms.SmsTemplateDO;
import com.familyan.smarth.manager.manager.sms.SmsChanelManager;
import com.familyan.smarth.manager.util.DWZResponse;
import com.familyan.smarth.manager.vo.SmsTemplateVO;
import com.familyan.smarth.manager.manager.sms.SmsManager;
import com.familyan.smarth.manager.manager.sms.SmsTemplateManager;
import com.familyan.smarth.manager.util.DWZPage;
import com.lotus.core.web.security.Security;
import com.lotus.service.result.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Koala on 2015/8/20 0020.
 */
@Controller
@RequestMapping("/sms/template")
public class SmsTemplateController {

    @Autowired
    private SmsTemplateManager smsTemplateManager;
    @Autowired
    private SmsChanelManager smsChanelManager;
    @Autowired
    private SmsManager smsManager;

    @RequestMapping("list")
    @Security(code = "smstemplate:list")
    public String list(DWZPage page, SmsTemplateDO smsTemplateDO, ModelMap result){
        PageResult<List<SmsTemplateVO>> list = smsTemplateManager.getSmsTemplateVOByPage(smsTemplateDO, page.getNumPerPage(), page.getPageNum());
        result.addAttribute("result",list);
        return "sms/template/list";
    }

    @RequestMapping("edit")
    @Security(code="sms:template:edit")
    public String edit(@RequestParam(value = "id",defaultValue = "0")long id,
                       ModelMap result) {
        List<SmsChanelDO> smsChanelDOList = smsChanelManager.getAll();
        result.addAttribute("smsChanels", smsChanelDOList);
        if(id !=  0) {
            SmsTemplateDO smsTemplateDO = smsTemplateManager.getById(id);
            result.addAttribute("smsTemplate",smsTemplateDO);
        }

        return "sms/template/edit";
    }

    @RequestMapping("save")
    @ResponseBody
    @Security(code="sms:template:edit")
    public DWZResponse<?> save(SmsTemplateDO smsTemplateDO) {
        if(smsTemplateDO.getId() == 0) {
            smsTemplateManager.add(smsTemplateDO);
        } else {
            smsTemplateManager.modify(smsTemplateDO);
        }

        DWZResponse dwzResponse = DWZResponse.dialogSuccess("短信模板保存成功");
        dwzResponse.setNavTabId("nav_smstemplate:list");
        return dwzResponse;
    }

    @RequestMapping("open")
    @ResponseBody
    @Security(code="sms:template:edit")
    public DWZResponse<?> open(Long id) {
        smsTemplateManager.open(id);
        DWZResponse dwzResponse = DWZResponse.navTabSuccess("启用模板成功", "nav_smstemplate:list");
        return dwzResponse;
    }

    @RequestMapping("close")
    @ResponseBody
    @Security(code="sms:template:edit")
    public DWZResponse<?> close(Long id) {
        smsTemplateManager.close(id);
        DWZResponse dwzResponse = DWZResponse.navTabSuccess("禁用模板成功", "nav_smstemplate:list");
        return dwzResponse;
    }

    @RequestMapping("testSendDialog")
    @Security(code = "sms:template:testSend")
    public String testSendDialog(Long id,ModelMap result){
        SmsTemplateDO templateDO = smsTemplateManager.getById(id);
        List<String> fields = extractParamField(templateDO.getContent());
        result.put("template",templateDO);
        result.put("fields", fields);
        return "/sms/template/testSendDialog";
    }

    Pattern paramPattern = Pattern.compile("\\$\\{(\\w+)\\}");
    private List<String> extractParamField(String content) {
        if(StringUtils.isBlank(content)){
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();

        Matcher m = paramPattern.matcher(content);
        while (m.find()){
            result.add(m.group(1));
        }
        return result;
    }

    @RequestMapping("testSend")
    @ResponseBody
    @Security(code = "sms:template:testSend")
    public DWZResponse testSend(Long id,String mobile, HttpServletRequest request ){
        if(id == null || StringUtils.isBlank(mobile))
            return DWZResponse.dialogError("参数错误");
        SmsTemplateDO templateDO = smsTemplateManager.getById(id);
        if(templateDO == null)
            return DWZResponse.dialogError("Id 不存在");
        List<String> fields = extractParamField(templateDO.getContent());

        Map<String,Object> params = new HashMap<>();

        for(String field : fields){
            params.put(field,request.getParameter(field));
        }

        long code  = smsManager.sendMessage(mobile,id,params);

        return DWZResponse.dialogSuccess("发送信息短信至 :"+mobile+" 成功，code:"+ code);
    }
}
