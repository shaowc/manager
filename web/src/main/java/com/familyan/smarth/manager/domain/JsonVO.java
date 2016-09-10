package com.familyan.smarth.manager.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Koala on 2015/10/28 0028.
 */
public class JsonVO {
    private boolean success;
    private String message;
    private Map<String, Object> data;
    public JsonVO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public JsonVO(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public JsonVO addData(String key, Object value){
        if(data == null){
            data = new HashMap<>();
        }
        data.put(key, value);
        return this;
    }
}
