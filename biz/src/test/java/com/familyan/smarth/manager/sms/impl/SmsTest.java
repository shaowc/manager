package com.familyan.smarth.manager.sms.impl;

import com.familyan.smarth.manager.manager.sms.SmsManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2015/9/2 0002.
 * 是是是
 */
public class SmsTest  {

    @Autowired
    private SmsManager smsManager;
    @Test
    public void testSendMessage(){
        try {
            System.out.println(2);
            Thread.sleep(2000);
            System.out.println(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
