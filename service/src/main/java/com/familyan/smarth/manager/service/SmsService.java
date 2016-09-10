package com.familyan.smarth.manager.service;

import java.util.Date;
import java.util.Map;

public interface SmsService {
	
	/**
	 * 发送短信，支持群发
	 * 
	 * @param mobile  手机号，群发时手机号以;隔开，如 15088888888;15088888889
	 * @param templateId  短信模板ID
	 * @param params 模板参数，key为参数名，value为参数值
	 * @return 大于0表示成功发送至短信平台，值为平台任务ID，小于0 发送失败了
	 * 
	 */
	public long sendMessage(String mobile, long templateId, Map<String, Object> params);
	
	/**
	 * 定时发送短信，支持群发
	 * 
	 * @param mobile 手机号，群发时手机号以;隔开，如 15088888888;15088888889
	 * @param templateId 短信模板ID
	 * @param params 模板参数，key为参数名，value为参数值
	 * @param datetime 定时发送时间
	 * @return 大于0表示成功发送至短信平台，值为平台任务ID，小于0 发送失败了
	 * 
	 */
	public long sendTimelyMessage(String mobile, long templateId, Map<String, Object> params, Date datetime);

	/**
	 * 发送短信验证码
	 *
	 * @param mobile 发送的手机号
	 * @return 验证码的唯一标识
	 */
	public String sendSmsVerifyCode(String mobile);

	/**
	 * 验证短信验证码
	 * 已废弃，未验证发验证码时的手机号
	 *
	 * @param identifier 发送短信验证码时返回的验证码标识
	 * @param code 用户输入的验证码值
	 * @return
	 * @see
	 */
	@Deprecated
	public boolean validateSmsVerifyCode(String identifier, String code);

	/**
	 * 验证短信验证码
	 *
	 * @param identifier 发送短信验证码时返回的验证码标识
	 * @param code 用户输入的验证码值
	 * @param mobile 用户手机号
	 * @return
	 */
	public boolean validateSmsVerifyCode(String identifier, String code, String mobile);

	/**
	 * 发送语音验证码
	 * @param mobile
	 * @return
	 */
	public String sendVoiceVerifyCode(String mobile);

	/**
	 * 短信发送数量限制
	 * @param mobile
	 * @param nowDate
	 * @return
	 */
	boolean countSmsNumWithTime(String mobile);

}
