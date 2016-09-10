package com.familyan.smarth.manager.domain;


import com.lotus.core.web.cookyjar.Writable;
import com.lotus.core.web.cookyjar.util.WritableUtil;

/**
 * Created by Admin on 2015/8/16.
 */
public class LoginEmployee implements Writable {

    Long id ;
    String empNum; //工号，登录用
    String name; // 名称
    Long version; //密码版本

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpNum() {
        return empNum;
    }

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String write() {
        return WritableUtil.write(
                id + "",
                empNum,
                name,
                version + ""
        );
    }

    @Override
    public void read(String value) {
        String[] values = WritableUtil.read(value);
        id = Long.parseLong(values[0]);
        empNum = values[1];
        name = values[2];
        version = Long.parseLong(values[3]);
    }
}
