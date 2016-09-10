package com.familyan.smarth.manager.domain.sms;
/**
 * @Author 		          代码生成器1.0
 * @see	         	：SMS_CHANEL
 * @CreateTime 	    2015-08-15 11:33:50
 */
public class SmsChanelDO {
	
	
	/**主键*/
	private long id;
	
	/**通道名称*/
	private java.lang.String name;
	
	/**1:建周*/
	private int chanelProvider;
	
	/**通道账户*/
	private java.lang.String account;
	
	/**通道密码*/
	private java.lang.String password;
	
	/**创建人Id*/
	private long createUser;
	
	/**修改人Id*/
	private long modifyUser;
	
	/**创建时间*/
	private java.util.Date gmtCreate;
	
	/**修改时间*/
	private java.util.Date gmtModify;

	private String balance;
	
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}
	
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	public java.lang.String getName(){
		return name;
	}
	
	public void setChanelProvider(int chanelProvider){
		this.chanelProvider = chanelProvider;
	}
	
	public int getChanelProvider(){
		return chanelProvider;
	}
	
	public void setAccount(java.lang.String account){
		this.account = account;
	}
	
	public java.lang.String getAccount(){
		return account;
	}
	
	public void setPassword(java.lang.String password){
		this.password = password;
	}
	
	public java.lang.String getPassword(){
		return password;
	}
	
	public void setCreateUser(long createUser){
		this.createUser = createUser;
	}
	
	public long getCreateUser(){
		return createUser;
	}
	
	public void setModifyUser(long modifyUser){
		this.modifyUser = modifyUser;
	}
	
	public long getModifyUser(){
		return modifyUser;
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

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
}