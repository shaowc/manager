package com.familyan.smarth.manager.mapper;

import com.familyan.smarth.manager.domain.EmployeePermissionDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Admin on 2015/8/18.
 */
public interface EmployeePermissionMapper {

    @Select("SELECT * FROM MANAGER_EMPLOYEE_PERMISSION WHERE emp_id = #{emp_id}")
    EmployeePermissionDO findByEmpId(Long empId);

    @Insert("INSERT INTO MANAGER_EMPLOYEE_PERMISSION (`emp_id`,`permissions`,`channels`,`menus`,`authorizer`,`gmt_create`,`gmt_modified`) " +
            "VALUES(#{empId},#{permissions},#{channels},#{menus},#{authorizer},now(),now())")
    void insert(EmployeePermissionDO permissionDO);

    @Update("UPDATE MANAGER_EMPLOYEE_PERMISSION  set permissions = #{permissions} , channels = #{channels}, menus = #{menus}," +
            "authorizer =#{authorizer} ,gmt_modified = now() " +
            "WHERE emp_id = #{empId} ")
    void updatePermission(EmployeePermissionDO permissionDO);
}
