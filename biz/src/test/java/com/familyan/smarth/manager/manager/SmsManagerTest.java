package com.familyan.smarth.manager.manager;

import com.familyan.smarth.manager.manager.sms.SmsManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-beans.xml"})
public class SmsManagerTest {
    @Autowired
    SmsManager smsManager;

    @Test
    public void testSendMessage() {
        /*Map<String, Object> params = new HashMap<>();
        params.put("verifyCode", "123456");
        long returnValue = smsManager.sendMessage("15088603572",91002451, params);
        System.out.println("Return Value: " + returnValue);
        returnValue = smsManager.sendMessage("15088603572",1, params);
        System.out.println("Return Value: " + returnValue);*/
    }
}
