package com.familyan.smarth.manager.file;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.OSSObject;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/10/30 0030.
 */
public class OssClientTest {

    @Test
    public void testGetObject() {
        OSSClient ossClient = new OSSClient("http://oss.aliyuncs.com","3qdUWFyec6laq9q7","uRAu0DKuqWFop46GWqWoXhRZQ4HcCs");
        try {
            OSSObject ossObject = ossClient.getObject("tck-file", "heeh");
            System.out.println(ossObject);
            InputStream inputStream = ossObject.getObjectContent();
            byte[] r = new byte[(int)ossObject.getObjectMetadata().getContentLength()];
            try {
                inputStream.read(r);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(new String(r));
        } catch (Exception e) {
            System.out.println("xxx");
        }



    }

    public static void main(String[] args) {
        OSSClient ossClient = new OSSClient("http://oss.aliyuncs.com","3qdUWFyec6laq9q7","uRAu0DKuqWFop46GWqWoXhRZQ4HcCs");
        try {
            OSSObject ossObject = ossClient.getObject("tck-file", "heeh");
            System.out.println(ossObject);
            InputStream inputStream = ossObject.getObjectContent();
            byte[] r = new byte[(int)ossObject.getObjectMetadata().getContentLength()];
            try {
                inputStream.read(r);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(new String(r));
        } catch (Exception e) {
            System.out.println("xxx");
        }
    }
}
