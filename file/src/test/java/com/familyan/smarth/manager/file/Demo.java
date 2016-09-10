package com.familyan.smarth.manager.file;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.OSSObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Koala on 2015/10/26 0026.
 */
public class Demo {

    public static void main(String[] args) {
        OSSClient ossClient = new OSSClient("http://oss.aliyuncs.com","3qdUWFyec6laq9q7","uRAu0DKuqWFop46GWqWoXhRZQ4HcCs");
        // 获取用户的Bucket列表
        /*List<Bucket> buckets = ossClient.listBuckets();

        // 遍历Bucket
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName());
        }


        File file = new File("E:/a.txt");
        InputStream inputStream = null;
        InputStream input = null;
        try {
            inputStream = new FileInputStream(file);
            ObjectMetadata objectMeta = new ObjectMetadata();
            objectMeta.setContentLength(file.length());
            objectMeta.setCacheControl("max-age=2592000");//1个月缓存
            String contentType = MimeUtils.getMimeType(inputStream);
            input = new FileInputStream(file);
            System.out.println(contentType);
            String key = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(key);
            // 可以在metadata中标记文件类型
            objectMeta.setContentType(contentType);
            ossClient.putObject("tck-file", key, input, objectMeta);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/


        OSSObject ossObject = ossClient.getObject("tck-file", "heeh");
        InputStream inputStream = ossObject.getObjectContent();
        byte[] r = new byte[(int)ossObject.getObjectMetadata().getContentLength()];
        try {
            inputStream.read(r);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(new String(r));


    }

}
