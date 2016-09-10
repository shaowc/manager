package com.familyan.smarth.manager.mapper;

import com.familyan.smarth.manager.domain.EmployeeOperationDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Admin on 2015/8/18.
 */
public interface EmployeeOperationMapper {

    @Insert("INSERT INTO MANAGER_EMPLOYEE_OPERATION (`emp_id`,`emp_num`,`emp_name`,`code`,`ip`,`domain`,`url`,`params`,`gmt_create`) " +
            "VALUES(#{empId},#{empNum},#{empName},#{code},#{ip},#{domain},#{url},#{params},now())" )
    void insert(EmployeeOperationDO operation);

    @Select("<script>" +
            "SELECT * FROM MANAGER_EMPLOYEE_OPERATION " +
            "<where>" +
            "<if test='empId != null '>emp_id = #{empId} </if>  " +
            "</where>" +
            "ORDER BY gmt_create DESC limit #{start},#{limit}" +
            "</script>")
    List<EmployeeOperationDO> findByEmpId(@Param("empId")Long empId,@Param("start")int start,@Param("limit")int limit);

    @Select("<script>" +
            "SELECT count(1) FROM MANAGER_EMPLOYEE_OPERATION " +
            "<where><if test='empId != null'>emp_id = #{empId}</if></where>" +
            "</script>")
    int countByEmpId(@Param("empId")Long empId);
}
