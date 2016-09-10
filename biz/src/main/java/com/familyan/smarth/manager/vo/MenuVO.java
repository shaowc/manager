package com.familyan.smarth.manager.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2015/8/18.
 */
public class MenuVO {
    Long id;
    String code;
    String name;
    String domain;
    String url;
    Long parentId;
    Integer type;
    Boolean show;
    List<MenuVO> childrens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuVO> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<MenuVO> childrens) {
        this.childrens = childrens;
    }

    public void addChildren(MenuVO menuVO){
        if(childrens == null )
            childrens = new ArrayList<>();
        childrens.add(menuVO);
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }
}
