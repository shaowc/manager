package com.familyan.smarth.manager.service.sms.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.familyan.smarth.manager.domain.sms.SmsVerifyCodeDO;
import com.familyan.smarth.manager.manager.sms.SmsVerifyCodeManager;
import com.familyan.smarth.manager.service.SmsService;
import com.familyan.smarth.manager.util.UUIDUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.familyan.smarth.manager.manager.sms.SmsManager;
import com.familyan.smarth.manager.util.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SmsServiceImpl implements SmsService {

	Cache<String,String> cache;

	Cache<String,AtomicInteger> codeCounter;

	@Autowired
	private SmsManager smsManager;
	@Autowired
	private SmsVerifyCodeManager smsVerifyCodeManager;

	public static final String IDENTITY_DICTIONARY = "abcdefghijklmnopqrstuvwxyz0123456789";

	public static final int CODE_NUM_COUNT = 6;

	public static final int VOICE_CODE_NUM_COUNT = 4;

	public static final int IDENTITY_NUM_COUNT = 6;

	public SmsServiceImpl() {
		//最多缓存一分钟
		cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(10000).build();

		codeCounter = CacheBuilder.newBuilder().expireAfterWrite(12,TimeUnit.HOURS).maximumSize(10000).build();
	}

	@Override
	public long sendMessage(String mobile, long templateId,
			Map<String, Object> params) {
		return smsManager.sendMessage(mobile, templateId, params);
	}

	@Override
	public long sendTimelyMessage(String mobile, long templateId,
			Map<String, Object> params, Date datetime) {
		return smsManager.sendTimelyMessage(datetime, mobile, templateId, params);
	}

	@Override
	public String sendSmsVerifyCode(String mobile) {

		try {
			AtomicInteger co = codeCounter.get(mobile, new Callable<AtomicInteger>() {
                @Override
                public AtomicInteger call() throws Exception {
                    return new AtomicInteger(0);
                }
            });
			if(co.getAndIncrement() > 5){
				throw new RuntimeException("max send per 12 hour!");
			}
		} catch (ExecutionException e) {
			//e.printStackTrace();
		}
		// 先验证缓存
		String identifier = cache.getIfPresent(mobile);
		if(!StringUtils.isEmpty(identifier)) {
			return identifier;
		}

		// 校验数据库 该手机是否有未作废的短信验证码，有的话直接返回原验证码的identifier
		List<SmsVerifyCodeDO> codes = smsVerifyCodeManager.getByMobile(mobile);
		for (SmsVerifyCodeDO smsVerifyCodeDO : codes) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(smsVerifyCodeDO.getGmtCreate());
			cal.add(Calendar.MINUTE, 1);
			if(!cal.getTime().before(new Date())) {
				return smsVerifyCodeDO.getIdentifier();
			}
		}

		String code = RandomUtils.generateRandomNumber(CODE_NUM_COUNT);
		String identity = UUIDUtils.uuid();
		// 先放缓存，再插数据库
		cache.put(mobile, identity);
		SmsVerifyCodeDO smsVerifyCodeDO = new SmsVerifyCodeDO();
		Date gmtDate = new Date();
		smsVerifyCodeDO.setGmtModify(gmtDate);
		smsVerifyCodeDO.setGmtCreate(gmtDate);
		smsVerifyCodeDO.setCode(code);
		smsVerifyCodeDO.setIdentifier(identity);
		smsVerifyCodeDO.setMobile(mobile);
		smsVerifyCodeDO.setStatus(0);

		// 作废该手机过期的验证码
		smsVerifyCodeManager.expireByMobile(mobile);
		smsVerifyCodeManager.add(smsVerifyCodeDO);
		Map<String, Object> params = new HashMap<>();
		params.put("verifyCode", code);
		sendMessage(mobile, 1, params);
		return identity;
	}

	@Override
	public boolean validateSmsVerifyCode(String identifier, String code) {
		if(StringUtils.isEmpty(identifier) || StringUtils.isEmpty(code)) {
			return false;
		}

		identifier = identifier.replaceAll("\"","");

		SmsVerifyCodeDO smsVerifyCodeDO = smsVerifyCodeManager.getByIdentifier(identifier);
		if(smsVerifyCodeDO == null) {
			return false;
		}

		// 过期
		if(smsVerifyCodeDO.getStatus() == 1){
			return false;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(smsVerifyCodeDO.getGmtCreate());
		cal.add(Calendar.MINUTE, 5);
		if(cal.getTime().before(new Date())) {
			smsVerifyCodeManager.expire(smsVerifyCodeDO.getId());
			return false;
		}

		if(!code.equals(smsVerifyCodeDO.getCode())) {
			return false;
		}

		return true;
	}

	@Override
	public boolean validateSmsVerifyCode(String identifier, String code, String mobile) {
		if(StringUtils.isEmpty(identifier) || StringUtils.isEmpty(code)) {
			return false;
		}

		identifier = identifier.replaceAll("\"","");

		SmsVerifyCodeDO smsVerifyCodeDO = smsVerifyCodeManager.getByIdentifier(identifier);
		if(smsVerifyCodeDO == null) {
			return false;
		}

		// 过期
		if(smsVerifyCodeDO.getStatus() == 1){
			return false;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(smsVerifyCodeDO.getGmtCreate());
		cal.add(Calendar.MINUTE, 5);
		if(cal.getTime().before(new Date())) {
			smsVerifyCodeManager.expire(smsVerifyCodeDO.getId());
			return false;
		}

		if(!code.equals(smsVerifyCodeDO.getCode())) {
			return false;
		}

		if(!smsVerifyCodeDO.getMobile().equals(mobile)) {
			return false;
		}

		return true;
	}

	/**
	 * 发送语音验证码
	 *
	 * @param mobile
	 * @return
	 */
	@Override
	public String sendVoiceVerifyCode(String mobile) {
		// 先验证缓存
		String identifier = cache.getIfPresent(mobile);
		if(!StringUtils.isEmpty(identifier)) {
			return identifier;
		}

		// 校验数据库 该手机是否有未作废的短信验证码，有的话直接返回原验证码的identifier
		List<SmsVerifyCodeDO> codes = smsVerifyCodeManager.getByMobile(mobile);
		for (SmsVerifyCodeDO smsVerifyCodeDO : codes) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(smsVerifyCodeDO.getGmtCreate());
			cal.add(Calendar.MINUTE, 1);
			if(!cal.getTime().before(new Date())) {
				return smsVerifyCodeDO.getIdentifier();
			}
		}

		String code = RandomUtils.generateRandomNumber(VOICE_CODE_NUM_COUNT);
		String identity = UUIDUtils.uuid();
		// 先放缓存，再插数据库
		cache.put(mobile, identity);
		SmsVerifyCodeDO smsVerifyCodeDO = new SmsVerifyCodeDO();
		Date gmtDate = new Date();
		smsVerifyCodeDO.setGmtModify(gmtDate);
		smsVerifyCodeDO.setGmtCreate(gmtDate);
		smsVerifyCodeDO.setCode(code);
		smsVerifyCodeDO.setIdentifier(identity);
		smsVerifyCodeDO.setMobile(mobile);
		smsVerifyCodeDO.setStatus(0);

		// 作废该手机过期的验证码
		smsVerifyCodeManager.expireByMobile(mobile);
		smsVerifyCodeManager.add(smsVerifyCodeDO);
		Map<String, Object> params = new HashMap<>();
		params.put("verifyCode", code);
		smsManager.sendVoice(mobile, 4, params);
		return identity;
	}

	/**
	 * 短信发送数量限制
	 *
	 * @param mobile
	 * @param nowDate
	 * @return
	 */
	@Override
	public boolean countSmsNumWithTime(String mobile) {
		return smsVerifyCodeManager.countSmsNumWithTime(mobile);
	}
}
