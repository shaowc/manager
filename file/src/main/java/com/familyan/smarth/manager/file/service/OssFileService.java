package com.familyan.smarth.manager.file.service;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.familyan.smarth.manager.file.utils.MimeUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Koala on 2015/10/26 0026.
 */
public class OssFileService {

    private final Logger logger = Logger.getLogger(OssFileService.class);

    private OSSClient ossClient;
    private String bucketName;

    public void setOssClient(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String saveFile(String filepath) {
        String filename = UUID.randomUUID().toString().replaceAll("-","");
        return saveFile(filepath, filename);
    }

    public String saveFile(String filepath, String filename) {
        InputStream mimeTypeInput = null;
        InputStream input = null;
        try {
            File file = new File(filepath);
            mimeTypeInput = new FileInputStream(file);
            ObjectMetadata objectMeta = new ObjectMetadata();
            objectMeta.setContentLength(file.length());
            objectMeta.setCacheControl("max-age=2592000");//1个月缓存
            String contentType = MimeUtils.getMimeType(mimeTypeInput);
            // 可以在metadata中标记文件类型
            objectMeta.setContentType(contentType);
            input = new FileInputStream(file);
            ossClient.putObject(bucketName, filename, input, objectMeta);
        } catch (Exception e) {
            throw new RuntimeException("保存文件失败",e);
        } finally{
            if(mimeTypeInput != null){
                try {
                    mimeTypeInput.close();
                } catch (IOException e) {
                }
            }
            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }

        return filename;
    }

    public String saveFile(InputStream inputStream,int contentLength,String filename){
        try {
            ObjectMetadata objectMeta = new ObjectMetadata();
            objectMeta.setContentLength(contentLength);
            objectMeta.setCacheControl("max-age=2592000");//1个月缓存
            String contentType = MimeUtils.getMimeType(inputStream);
            // 可以在metadata中标记文件类型
            objectMeta.setContentType(contentType);
            ossClient.putObject(bucketName, filename, inputStream, objectMeta);
        } catch (Exception e) {
            throw new RuntimeException("保存文件失败",e);
        } finally{
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return filename;
    }

    public void removeFile(String filename) {
        ossClient.deleteObject(bucketName, filename);
    }

    public OSSObject getFileObject(String filename) {
        return ossClient.getObject(bucketName, filename);
    }
}
