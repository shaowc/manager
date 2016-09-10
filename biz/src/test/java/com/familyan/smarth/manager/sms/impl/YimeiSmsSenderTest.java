package com.familyan.smarth.manager.sms.impl;

import com.familyan.smarth.manager.manager.sms.impl.YiMeiSmsSender;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2015/9/10 0010.
 */

public class YimeiSmsSenderTest extends BasicTest{

    @Autowired
    private YiMeiSmsSender smsSender ;

    @Test
    public void testSendMessage() {
        // 注释代码，以免skipTests=false时，群发大量短信

        /*System.out.println("************* YiMeiSenderTest ***************");
        long returnValue = smsSender.sendMessage("8SDK-EMY-6699-RDQPT", "tck...110011", "15088603572", "您的验证码为：234");
        System.out.println("************* YiMeiSenderTest ReturnValue :" + returnValue);*/
    }

    @Test
    public void testGetBalance() {
        System.out.println("*************getBalance****************");
        System.out.println(smsSender.getBalance("8SDK-EMY-6699-RDQPT", "tck...110011"));
    }


    @Test
    public void testBatchSend() {
        /*Resource resource = new ClassPathResource("smsmobile.txt");
        try {
            File file = resource.getFile();
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String str = null;
            StringBuilder sb = new StringBuilder();
            System.out.println("************* send messages start *********");
            int count = 0;
            while((str = br.readLine()) != null) {
                count++;
                sb.append(str);
                sb.append(";");
                if(count % 200 == 0) {
                    String mobile = sb.substring(0, sb.length() - 1);
                    System.out.println(mobile);
                    long rs = smsSender.sendMessage("8SDK-EMY-6699-RDQPT", "tck...110011", mobile, "装修注意！3天倒计时！G20前最后的市场放价促销，51没赶上，还有58！这个周末我去佳好佳！（http://dwz.cn/39yXN6）");
                    System.out.println("平台返回值：" + rs);
                    sb.delete(0, sb.length());
                }
            }

            if (sb.length() > 0) {
                String mobile = sb.substring(0, sb.length() - 1);
                System.out.println(mobile);
                long rs = smsSender.sendMessage("8SDK-EMY-6699-RDQPT", "tck...110011", mobile, "装修注意！3天倒计时！G20前最后的市场放价促销，51没赶上，还有58！这个周末我去佳好佳！（http://dwz.cn/39yXN6）");
                System.out.println("平台返回值：" + rs);
            }

            br.close();
            reader.close();
            System.out.println("************* send messages finish *********");

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


}
