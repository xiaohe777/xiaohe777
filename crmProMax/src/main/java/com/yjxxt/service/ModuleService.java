package com.yjxxt.service;

import com.yjxxt.base.BaseService;
import com.yjxxt.mapper.ModuleMapper;
import com.yjxxt.mapper.PermissionMapper;
import com.yjxxt.pojo.Module;
import com.yjxxt.dto.TreeDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ModuleService extends BaseService<Module,Integer> {


    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private PermissionMapper permissionMapper;


    /**
     * 查询所有资源信息
     * @return 返回list
     */
    public List<TreeDto> queryAllModules(Integer roleId) {
        //查角色有哪些权限
        List<Integer> mIds = permissionMapper.selectModuleIdByRoleId(roleId);
        //查所有的权限
        List<TreeDto> treeDto = moduleMapper.queryAllModules();
        //遍历
        for (TreeDto t:treeDto) {
            //如果所有权限里包含角色有的权限，则设置checked
            if(mIds.contains(t.getId())) {
                t.setChecked(true);
            }
        }
        //返回
        return treeDto;
    }
}
