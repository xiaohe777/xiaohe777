package com.yjxxt.controller;

import com.yjxxt.base.BaseController;
import com.yjxxt.service.ModuleService;
import com.yjxxt.dto.TreeDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;



    /**
     * 查询所有资源信息
     * @return 返回list
     */
    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeDto> queryAllModules(Integer roleId){
        return moduleService.queryAllModules(roleId);
    }

}
