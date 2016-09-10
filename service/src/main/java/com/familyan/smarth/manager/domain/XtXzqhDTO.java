package com.familyan.smarth.manager.domain;

import java.io.Serializable;

/**
 * Created by Koala on 2015/12/7 0007.
 */
public class XtXzqhDTO implements Serializable {

    private static final long serialVersionUID = -4286136864734465627L;

    /**行政区划编码**/
    private int code;

    /**行政区划名称**/
    private String name;

    /**行政区划等级**/
    private int level;

    /**上级行政区划编码**/
    private int parentCode;

    /**是否显示*/
    private int display;

    private int displayLevel;

    private int displayParentCode;

    private Integer oldCode;
    private String oldName;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentCode() {
        return parentCode;
    }

    public void setParentCode(int parentCode) {
        this.parentCode = parentCode;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public int getDisplayLevel() {
        return displayLevel;
    }

    public void setDisplayLevel(int displayLevel) {
        this.displayLevel = displayLevel;
    }

    public int getDisplayParentCode() {
        return displayParentCode;
    }

    public void setDisplayParentCode(int displayParentCode) {
        this.displayParentCode = displayParentCode;
    }

    public Integer getOldCode() {
        return oldCode;
    }

    public void setOldCode(Integer oldCode) {
        this.oldCode = oldCode;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
}
