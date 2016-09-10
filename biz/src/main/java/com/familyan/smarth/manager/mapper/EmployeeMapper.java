package com.familyan.smarth.manager.mapper;

import com.familyan.smarth.manager.domain.EmployeeDO;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2015/8/17.
 */
public interface EmployeeMapper {

    @Select("SELECT * FROM MANAGER_EMPLOYEE WHERE id = #{id}")
    EmployeeDO findById(Long id);

    @Select("SELECT * FROM MANAGER_EMPLOYEE WHERE emp_num = #{empNum} and password = #{password}")
    EmployeeDO findByEmpNumAndPassword(@Param("empNum")String empNum,@Param("password")String password);

    @Select("SELECT * FROM MANAGER_EMPLOYEE ORDER BY id DESC  LIMIT #{start},#{limit} ")
    List<EmployeeDO> findByPage(@Param("start")int start, @Param("limit")int limit);

    @Select("SELECT count(1) FROM MANAGER_EMPLOYEE")
    Integer countAll();

    @Select("<script>" +
            "SELECT * FROM MANAGER_EMPLOYEE WHERE id IN " +
            "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"+
            "   #{item}"+
            "</foreach> "+
            "</script>")
    List<EmployeeDO> findByEmpIds(List<Long> ids);

    @Update("UPDATE MANAGER_EMPLOYEE SET password = #{newPwd} , pwd_version = #{version} WHERE id = #{id}")
    void changePassword(@Param("id")Long id,@Param("newPwd") String newPwd, @Param("version")Date version);

    @Insert("insert into MANAGER_EMPLOYEE (`real_name`,`nick_name`,`emp_num`,`gender`,`birthday`,`address`,`mobile`,`email`,`password`,`province_id`,`city_id`,`district_id`,`job_title`,`entry_date`,`pwd_version`,`status`,`gmt_create`,`gmt_modified`)" +
            "VALUES (#{realName},#{nickName}, #{empNum},#{gender},#{birthday},#{address},#{mobile},#{email},#{password},#{provinceId},#{cityId},#{districtId},#{jobTitle},#{entryDate},#{pwdVersion},#{status},now(),now())")
    @SelectKey(keyColumn = "id",keyProperty = "id",before = false, statement = "SELECT LAST_INSERT_ID()" ,resultType = Long.class)
    void insert(EmployeeDO employeeDO);

    @Update("<script>" +
            "UPDATE MANAGER_EMPLOYEE <set>" +
            "<if test='realName != null  '> `real_name` = #{realName},</if>" +
            "<if test='nickName != null  '> `nick_name` = #{nickName},</if>" +
            "<if test='empNum != null  '> `emp_num` = #{empNum},</if>" +
            "<if test='gender != null  '> `gender` = #{gender},</if>" +
            "<if test='birthday != null' > `birthday` = #{birthday},</if>" +
            "<if test='address != null   '> address = #{address},</if>" +
            "<if test='mobile != null   '> mobile = #{mobile},</if>" +
            "<if test='email != null  '> email = #{email},</if>" +
            "<if test='jobTitle != null  '> job_title = #{jobTitle},</if>" +
            "<if test='entryDate != null   '> entry_date = #{entryDate},</if>" +
            "<if test='status != null   '> status = #{status}</if>" +
            " </set>" +
            "WHERE id = #{id};"+
            "</script>")
    void update(EmployeeDO employeeDO);

    @Select("SELECT * FROM MANAGER_EMPLOYEE WHERE emp_num = #{empNum}  ")
    EmployeeDO findByEmpNum(String empNum);
}
