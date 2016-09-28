package com.familyan.smarth.manager.controllers.smarth;

import com.familyan.smarth.domain.PacketDTO;
import com.familyan.smarth.manager.domain.LoginEmployee;
import com.familyan.smarth.manager.util.CurrencyUtil;
import com.familyan.smarth.manager.util.DWZPage;
import com.familyan.smarth.manager.util.DWZResponse;
import com.familyan.smarth.service.PacketService;
import com.lotus.core.web.security.Security;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import com.lotus.service.result.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by shaowenchao on 16/9/10.
 */
@RequestMapping("packet")
@Controller
public class PacketController {

    @Autowired
    private PacketService packetService;

    @RequestMapping("list")
    public String list(LoginEmployee loginEmployee, PacketDTO packetDTO, DWZPage dwzPage, ModelMap modelMap) {
        PageResult<List<PacketDTO>> pageResult = packetService.findByPage(packetDTO, new Page(dwzPage.getPageNum(), dwzPage.getNumPerPage()), "id desc");
        modelMap.put("result", pageResult);
        return "smarth/packet/list";
    }

    @RequestMapping("edit")
    public String edit(LoginEmployee loginEmployee, Integer id, ModelMap modelMap) {
        PacketDTO packetDTO = packetService.findById(id);
        modelMap.put("packet", packetDTO);
        if(packetDTO != null) {
            String content = packetDTO.getContent();
            String[] contents = content.split(",");
            modelMap.put("contents", contents);
        }

        return "smarth/packet/edit";
    }

    @RequestMapping("save")
    @ResponseBody
    public DWZResponse save(LoginEmployee loginEmployee, PacketDTO packetDTO, String packetPrice) {

        if(StringUtils.isBlank(packetDTO.getContent())) {
            return DWZResponse.dialogError("请填写体检项目");
        }
        packetDTO.setPrice(getCent(packetPrice));
        packetService.save(packetDTO);
        return DWZResponse.dialogSuccess("保存成功");
    }

    Integer getCent(String price){
        BigDecimal f  = new BigDecimal(price);
        return f.multiply(new BigDecimal("100")).intValue();
    }

    @RequestMapping("buy")
    public String buy(LoginEmployee loginEmployee, Integer id, ModelMap modelMap) {
        edit(loginEmployee, id, modelMap);
        return "smarth/packet/buy";
    }

    @RequestMapping("tobuy")
    @ResponseBody
    public DWZResponse tobuy(LoginEmployee loginEmployee, Integer packetId, Long memberId) {
        Result result = packetService.buy(memberId, packetId);
        if (!result.isSuccess()) {
            return DWZResponse.dialogError(result.getMsg());
        }
        return DWZResponse.dialogSuccess("指配成功");
    }
}
