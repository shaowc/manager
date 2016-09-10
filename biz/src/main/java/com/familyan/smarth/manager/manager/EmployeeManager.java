package com.familyan.smarth.manager.manager;

import com.familyan.smarth.manager.domain.EmployeeDO;
import com.familyan.smarth.manager.domain.EmployeeOperationDO;
import com.familyan.smarth.manager.domain.EmployeePermissionDO;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 2015/8/17.
 */
public interface EmployeeManager {

    /**
     * 按id查询员工信息
     * @param id
     * @return
     */
    EmployeeDO findById(Long id );

    /**
     * 按工号以及密码查询员工信息
     * @param empNum
     * @param password
     * @return
     */
    EmployeeDO findByEmpNumAndPassword(String empNum,String password);

    /**
     * 修改密码
     * @param id
     * @param newPwd
     * @return
     */
    Date changePassword(Long id, String newPwd);

    /**
     * 简单分页查询
     * @param p
     * @return
     */
    PageResult<List<EmployeeDO>> findByPage(Page p);

    /**
     * 保存用户账号信息
     * @param employeeDO
     */
    void save(EmployeeDO employeeDO);

    /**
     * 按员工id查询权限列表
     * @param id
     * @return
     */
    Set<Long> findEmployeePermissionById(Long id);

    /**
     * 保存用户权限
     * @param permissionDO
     */
    void saveEmployeePermission(EmployeePermissionDO permissionDO);

    /**
     * 按员工id 查询用户操作记录
     * @param empId
     * @return
     */
    PageResult<List<EmployeeOperationDO>> findOperationsByEmpId(Long empId,Page page);

    /**
     * 按工号查询员工
     * @param empNum
     * @return
     */
    EmployeeDO findByEmpNum(String empNum);

    /**
     * 保存用户操作日志
     * @param op
     */
    void saveEmployeeOperation(EmployeeOperationDO op);

    /**
     * 查询用户权限
     * @param employee
     * @return
     */
    EmployeePermissionDO findEmployeePermissionBeanById(Long employee);
}
