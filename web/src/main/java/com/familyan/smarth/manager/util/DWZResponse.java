package com.familyan.smarth.manager.util;


import com.lotus.core.util.RestResponse;

/**
 * Created by Admin on 2015/8/18.
 */
public class DWZResponse<T> extends RestResponse {

    public static final String CODE_SUCCESS = "1";
    public static final String CODE_ERROR = "2";
    public static final String CODE_TIMEOUT = "3";

    public static final String CALLBACK_TYPE_CLOSE = "closeCurrent";
    public static final String CALLBACK_TYPE_FORWARD = "forward";

    public static DWZResponse<?> SUCCESS = new DWZResponse<Object>(CODE_SUCCESS);
    public static DWZResponse<?> ERROR = new DWZResponse<Object>(CODE_SUCCESS);
    public static DWZResponse<?> TIMEOUT = new DWZResponse<Object>(CODE_SUCCESS);

    public static <T> DWZResponse<T> dialogSuccess(String msg){
        return new DWZResponse<>(CODE_SUCCESS,msg, null,CALLBACK_TYPE_CLOSE,null,null);
    }

    public static <T> DWZResponse<T> dialogError(String msg ){
        return new DWZResponse<>(CODE_ERROR ,msg,null,CALLBACK_TYPE_CLOSE,null,null);
    }

    public static <T> DWZResponse<T> dialogSuccess(String msg,String refreshNavTabId){
        return new DWZResponse<>(CODE_SUCCESS,msg, null,CALLBACK_TYPE_CLOSE,null,refreshNavTabId);
    }

    public static <T> DWZResponse<T> navTabSuccess(String msg,String refreshNavTabId){
        return new DWZResponse<>(CODE_SUCCESS,msg, null,null,null,refreshNavTabId);
    }

    public static <T> DWZResponse<T> navTabError(String msg ){
        return new DWZResponse<>(CODE_ERROR ,msg,null,null,null,null);
    }

    String callbackType;

    String forwardUrl ;//当callbackType = forward 时,forwardUrl 生效

    String navTabId;// 一般是 nav_{code}

    public DWZResponse(String code, String msg, T data) {
        super(code, msg, data);
    }

    public DWZResponse(T data) {
        super(data);
    }

    public DWZResponse(String code, T data) {
        super(code, data);
    }

    /**
     *
     * @param code
     * @param msg
     * @param data
     * @param callbackType
     * @param forwardUrl
     * @param navTabId 需要重新刷新的navTabId
     */
    public DWZResponse(String code,String msg, T data,String callbackType, String forwardUrl,String navTabId){
        super(code, msg, data);
        this.callbackType = callbackType;
        this.forwardUrl = forwardUrl;
        this.navTabId = navTabId;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setNavTabId(String navTabId) {
        this.navTabId = navTabId;
    }

    public String getNavTabId() {
        return navTabId;
    }
}
