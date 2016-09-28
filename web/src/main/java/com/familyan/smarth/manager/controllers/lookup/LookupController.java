package com.familyan.smarth.manager.controllers.lookup;

import com.familyan.smarth.domain.CheckerDTO;
import com.familyan.smarth.manager.util.DWZPage;
import com.familyan.smarth.service.CheckerService;
import com.familyan.smarth.service.MemberService;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Koala on 2015/12/21 0021.
 */
@Controller
@RequestMapping("lookup")
public class LookupController {

    @Autowired
    private CheckerService checkerService;

    @RequestMapping("checkers")
    public String checkers(CheckerDTO checkerDTO, DWZPage dwzPage, ModelMap modelMap) {
        PageResult<List<CheckerDTO>> pageResult = checkerService.findByPage(checkerDTO, new Page(dwzPage.getPageNum(), dwzPage.getNumPerPage()));
        modelMap.put("result", pageResult);
        return "lookup/checkers";
    }



}
