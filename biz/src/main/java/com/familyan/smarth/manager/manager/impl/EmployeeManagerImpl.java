package com.familyan.smarth.manager.manager.impl;

import com.familyan.smarth.manager.domain.EmployeeDO;
import com.familyan.smarth.manager.domain.EmployeePermissionDO;
import com.familyan.smarth.manager.manager.EmployeeManager;
import com.familyan.smarth.manager.mapper.EmployeeMapper;
import com.familyan.smarth.manager.mapper.EmployeeOperationMapper;
import com.familyan.smarth.manager.mapper.EmployeePermissionMapper;
import com.familyan.smarth.manager.util.PermissionUtil;
import com.familyan.smarth.manager.domain.EmployeeOperationDO;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 2015/8/17.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeManagerImpl implements EmployeeManager {

    @Autowired
    EmployeeMapper mapper;

    @Autowired
    EmployeePermissionMapper employeePermissionMapper;

    @Autowired
    EmployeeOperationMapper employeeOperationMapper;

    @Override
    public EmployeeDO findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public EmployeeDO findByEmpNumAndPassword(String empNum, String password) {
        return mapper.findByEmpNumAndPassword(empNum,password);
    }

    @Override
    @Transactional(readOnly = false)
    public Date changePassword(Long id, String newPwd) {
        Date version = new Date();
        mapper.changePassword(id,newPwd,version);
        return version;
    }

    @Override
    public PageResult<List<EmployeeDO>> findByPage(Page p) {
        List<EmployeeDO> list = mapper.findByPage(p.getStart(),p.getPageSize());
        Integer count = mapper.countAll();
        return new PageResult<>(p.getStart(),p.getPageSize(),count,list);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(EmployeeDO employeeDO) {
        if(employeeDO.getId() == null){
            mapper.insert(employeeDO);
        }
        else
            mapper.update(employeeDO);
    }

    @Override
    public Set<Long> findEmployeePermissionById(Long id) {
        EmployeePermissionDO permissionDO =  employeePermissionMapper.findByEmpId(id);
        if(permissionDO != null){
            Set<Long> all = PermissionUtil.split(permissionDO.getPermissions());
            all.addAll(PermissionUtil.split(permissionDO.getChannels()));
            all.addAll(PermissionUtil.split(permissionDO.getMenus()));
            return all;
        }
        return Collections.EMPTY_SET;
    }

    @Override
    @Transactional(readOnly = false)
    public void saveEmployeePermission(EmployeePermissionDO permissionDO) {
        EmployeePermissionDO old = employeePermissionMapper.findByEmpId(permissionDO.getEmpId());
        if(old != null){
            employeePermissionMapper.updatePermission(permissionDO);
        }
        else
            employeePermissionMapper.insert(permissionDO);
    }

    @Override
    public PageResult<List<EmployeeOperationDO>> findOperationsByEmpId(Long empId, Page page) {

        int count = employeeOperationMapper.countByEmpId(empId);
        if (count == 0) {
            return PageResult.emptyResult(Collections.<EmployeeOperationDO>emptyList());
        }
        List<EmployeeOperationDO> operationDOs = employeeOperationMapper.findByEmpId(empId,page.getStart(),page.getPageSize());
        return new PageResult<>(page.getStart(), page.getPageSize(), count, operationDOs);
    }

    @Override
    public EmployeeDO findByEmpNum(String empNum) {
        return mapper.findByEmpNum(  empNum);
    }

    @Override
    @Transactional(readOnly = false)
    public void saveEmployeeOperation(EmployeeOperationDO op) {
        employeeOperationMapper.insert(op);
    }

    @Override
    public EmployeePermissionDO findEmployeePermissionBeanById(Long employee) {
        return employeePermissionMapper.findByEmpId(employee);
    }
}
