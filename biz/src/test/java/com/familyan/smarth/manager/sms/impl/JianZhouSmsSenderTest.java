package com.familyan.smarth.manager.sms.impl;

import com.jianzhou.sdk.BusinessService;
import com.familyan.smarth.manager.manager.sms.impl.JianZhouSmsSender;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

/**
 * Created by Administrator on 2015/9/10 0010.
 */
public class JianZhouSmsSenderTest {

    private JianZhouSmsSender smsSender = new JianZhouSmsSender();

    @Test
    public void testSendMessage() {

        // 注释代码，以免skipTests=false时，群发大量短信

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
                if(count % 3000 == 0) {
                    String mobile = sb.substring(0, sb.length() - 1);
                    long rs = smsSender.sendMessage("sdk_caizhugg", "88141019", mobile, "尊敬的用户：您好！您报名活动奖励的价值25元健康环保甲醛检测盒0元包邮，即将要过期啦，赶快下载APP免费兑换吧！（http://m.app.taocaiku.com）账号是手机号，初始密码是手机号后6位。退订回T【淘材库】");
                    System.out.println("平台返回值：" + rs);
                    sb.delete(0, sb.length());
                }
            }

            if (sb.length() > 0) {
                String mobile = sb.substring(0, sb.length() - 1);
                long rs = smsSender.sendMessage("sdk_caizhugg", "88141019", mobile, "尊敬的用户：您好！您报名活动奖励的价值25元健康环保甲醛检测盒0元包邮，即将要过期啦，赶快下载APP免费兑换吧！（http://m.app.taocaiku.com）账号是手机号，初始密码是手机号后6位。退订回T【淘材库】");
                System.out.println("平台返回值：" + rs);
            }

            br.close();
            reader.close();
            System.out.println("************* send messages finish *********");

        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        //smsSender.sendMessage("sdk_caizhugg", "88141019", "15088603572", "尊敬的用户，您的验证码为${verifyCode}，本验证码5分钟内有效，感谢您使用");

    }



    @Test
    public void testGetBalance() {
        BusinessService bs = new BusinessService();
        bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
        String result = bs.getUserInfo("sdk_caizhusw","88141019");
        try {
            Document doc = DocumentHelper.parseText(result);
            Element root = doc.getRootElement();
            String remainFee = root.elementText("remainFee");
            System.out.println(remainFee);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("未知");
        }

    }

}
