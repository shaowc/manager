package com.familyan.smarth.manager.domain;

/**
 * Created by Koala on 2015/8/22 0022.
 */
public class XtXzqhDO {

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

}
