package com.familyan.smarth.manager.manager.sms;

import com.familyan.smarth.manager.domain.sms.SmsVerifyCodeDO;

import java.util.List;

/**
 * Created by Koala on 2015/8/29 0029.
 */
public interface SmsVerifyCodeManager {

    void add(SmsVerifyCodeDO smsVerifyCodeDO);

    SmsVerifyCodeDO getByIdentifier(String identifier);

    List<SmsVerifyCodeDO> getByMobile(String mobile);

    void expire(Long id);

    void expireByMobile(String mobile);

    boolean countSmsNumWithTime(String mobile);
}
