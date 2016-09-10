package com.familyan.smarth.manager.domain.sms;
/**
 * @Author 		          代码生成器1.0
 * @see	         	数据库表名：SMS_TEMPLATE
 * @CreateTime 	    2015-08-14 15:33:45
 */
public class SmsTemplateDO {
	
	
	/**主键*/
	private Long id;
	
	/**通道ID*/
	private Long chanelId;
	
	/**模板名称*/
	private java.lang.String name;
	
	/**模板内容*/
	private java.lang.String content;
	
	/**状态 1：正常，-1：删除 0：禁用*/
	private Integer status;
	
	/**创建时间*/
	private java.util.Date gmtCreate;
	
	/**修改时间*/
	private java.util.Date gmtModify;
	
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return id;
	}
	
	public Long getChanelId() {
		return chanelId;
	}

	public void setChanelId(Long chanelId) {
		this.chanelId = chanelId;
	}

	public void setName(java.lang.String name){
		this.name = name;
	}
	
	public java.lang.String getName(){
		return name;
	}
	
	public void setContent(java.lang.String content){
		this.content = content;
	}
	
	public java.lang.String getContent(){
		return content;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return status;
	}
	
	public void setGmtCreate(java.util.Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}
	
	public java.util.Date getGmtCreate(){
		return gmtCreate;
	}
	
	public void setGmtModify(java.util.Date gmtModify){
		this.gmtModify = gmtModify;
	}
	
	public java.util.Date getGmtModify(){
		return gmtModify;
	}
	
}