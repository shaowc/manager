package com.familyan.smarth.manager.domain.sms;
/**
 * @Author 		          代码生成器1.0
 * @see	         	：SMS_BLACK_LIST
 * @CreateTime 	    2015-08-14 15:33:45
 */
public class SmsBlackListDO {
	
	
	/**主键*/
	private long id;
	
	/**手机号*/
	private java.lang.String mobile;
	
	/**原因*/
	private java.lang.String reason;
	
	/**状态-1 删除，1:有效*/
	private int status;
	
	/**记录创建时间*/
	private java.util.Date gmtCreate;
	
	/**记录修改时间*/
	private java.util.Date gmtModified;
	
	
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
	
	public void setReason(java.lang.String reason){
		this.reason = reason;
	}
	
	public java.lang.String getReason(){
		return reason;
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
	
}