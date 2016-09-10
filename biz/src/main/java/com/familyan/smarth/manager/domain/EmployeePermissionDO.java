package com.familyan.smarth.manager.domain;

import java.util.Date;

/**
 * Created by Admin on 2015/8/18.
 */
public class EmployeePermissionDO {
    Long empId;//员工id
    String permissions;//权限id列表 , 以,分隔
    String channels; //员工包含的频道列表， 以, 分隔
    String menus;    //员工包含的一级菜单列表，以, 分隔
    Long authorizer;//授权人 emp_id
    Date gmtCreate; //授权时间
    Date gmtModified;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Long getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(Long authorizer) {
        this.authorizer = authorizer;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }
}
