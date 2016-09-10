package com.familyan.smarth.manager.domain.sms;

import java.util.Date;

/**
 * @Author 		          代码生成器1.0
 * @see	         	：SMS_SEND_LOG
 * @CreateTime 	    2015-08-18 11:48:23
 */
public class SmsSendLogDO {

	/**主键*/
	private long id;
	
	/**手机号*/
	private java.lang.String mobile;
	
	/**短信内容*/
	private java.lang.String content;
	
	/**模板ID*/
	private long templateId;
	
	/**发送时间*/
	private java.util.Date sendTime;
	
	/**返回值*/
	private java.lang.String returnValue;
	
	/**1:发送平台成功  2：系统异常，未发给送短信平台*/
	private int status;
	
	/**记录创建时间*/
	private java.util.Date gmtCreate;
	
	/**记录修改时间*/
	private java.util.Date gmtModified;

	/**发送通道提供商**/
	private Integer chanelProvider;

	/**发送通道**/
	private Long chanelId;

	/**通道账号**/
	private String chanelAccount;

	public SmsSendLogDO() {

	}

	public SmsSendLogDO(String mobile, long templateId, String returnValue, String content) {
		this.mobile = mobile;
		this.templateId = templateId;
		this.returnValue = returnValue;
		this.content = content;
		Date now = new Date();
		this.setSendTime(now);
		this.setGmtCreate(now);
		this.setGmtModified(now);
	}

	public void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}
	
	public void setMobile(java.lang.String mobile){
		this.mobile = mobile;
	}
	
	public java.lang.String getMobile(){
		return mobile;
	}
	
	public void setContent(java.lang.String content){
		this.content = content;
	}
	
	public java.lang.String getContent(){
		return content;
	}
	
	public void setTemplateId(long templateId){
		this.templateId = templateId;
	}
	
	public long getTemplateId(){
		return templateId;
	}
	
	public void setSendTime(java.util.Date sendTime){
		this.sendTime = sendTime;
	}
	
	public java.util.Date getSendTime(){
		return sendTime;
	}
	
	public void setReturnValue(java.lang.String returnValue){
		this.returnValue = returnValue;
	}
	
	public java.lang.String getReturnValue(){
		return returnValue;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void setGmtCreate(java.util.Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}
	
	public java.util.Date getGmtCreate(){
		return gmtCreate;
	}
	
	public void setGmtModified(java.util.Date gmtModified){
		this.gmtModified = gmtModified;
	}
	
	public java.util.Date getGmtModified(){
		return gmtModified;
	}

	public Long getChanelId() {
		return chanelId;
	}

	public void setChanelId(Long chanelId) {
		this.chanelId = chanelId;
	}

	public Integer getChanelProvider() {
		return chanelProvider;
	}

	public void setChanelProvider(Integer chanelProvider) {
		this.chanelProvider = chanelProvider;
	}

	public String getChanelAccount() {
		return chanelAccount;
	}

	public void setChanelAccount(String chanelAccount) {
		this.chanelAccount = chanelAccount;
	}
}