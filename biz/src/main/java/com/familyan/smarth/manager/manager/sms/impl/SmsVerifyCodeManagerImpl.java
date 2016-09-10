package com.familyan.smarth.manager.manager.sms.impl;

import com.familyan.smarth.manager.domain.sms.SmsVerifyCodeDO;
import com.familyan.smarth.manager.manager.sms.SmsVerifyCodeManager;
import com.familyan.smarth.manager.mapper.sms.SmsVerifyCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Koala on 2015/8/29 0029.
 */
@Service
@Transactional(readOnly = false)
public class SmsVerifyCodeManagerImpl implements SmsVerifyCodeManager{

    @Autowired
    private SmsVerifyCodeMapper smsVerifyCodeMapper;

    @Override
    public void add(SmsVerifyCodeDO smsVerifyCodeDO) {
        smsVerifyCodeMapper.insert(smsVerifyCodeDO);
    }

    @Override
    @Transactional(readOnly = true)
    public SmsVerifyCodeDO getByIdentifier(String identifier) {
        return smsVerifyCodeMapper.findByIdentifier(identifier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SmsVerifyCodeDO> getByMobile(String mobile) {
        return smsVerifyCodeMapper.findByMobile(mobile);
    }

    @Override
    public void expire(Long id) {
        smsVerifyCodeMapper.expire(id);
    }

    @Override
    public void expireByMobile(String mobile) {
        smsVerifyCodeMapper.expireByMobile(mobile);
    }

    /**
     * 限制同一手机号10分钟内只能发三次验证码
     * @param mobile
     * @param nowDate
     * @return true:已经超过
     */
    @Override
    public boolean countSmsNumWithTime(String mobile) {
        boolean flag = true;
        List<SmsVerifyCodeDO> list = smsVerifyCodeMapper.countSmsNumWithTime(mobile);
        if(null!=list && !list.isEmpty()){
            if(list.size() >=3 ){
                Date second = list.get(2).getGmtCreate();
                //10分钟之内
                if(System.currentTimeMillis() - second.getTime() > 20*60*1000L){
                    flag = true;
                }else{
                    flag = false;
                }
            }
        }
        return flag;
    }
}
