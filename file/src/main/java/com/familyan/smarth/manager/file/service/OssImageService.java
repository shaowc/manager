package com.familyan.smarth.manager.file.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shaowenchao on 6/17/16.
 */
public class OssImageService {

    private String uploadUrl;

    private String dateForm = "image";
    private String fileBodyKey = "file";
    private String stringBodyKey = "type";

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public void setDateForm(String dateForm) {
        this.dateForm = dateForm;
    }

    public void setFileBodyKey(String fileBodyKey) {
        this.fileBodyKey = fileBodyKey;
    }

    public void setStringBodyKey(String stringBodyKey) {
        this.stringBodyKey = stringBodyKey;
    }

    /**
     * 上传图片
     * @param inputStream
     * @return
     */
    public String upload(InputStream inputStream) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        String tempFilePath = LocalFileService.saveTempFile(inputStream);

        File tempFile = new File(tempFilePath);
        HttpClient httpclient = httpClientBuilder.build();
        HttpPost httppost = new HttpPost(uploadUrl);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

        FileBody fileBody = new FileBody(tempFile);
        StringBody typeBody = new StringBody(dateForm, ContentType.MULTIPART_FORM_DATA);
        multipartEntityBuilder.addPart(fileBodyKey, fileBody);
        multipartEntityBuilder.addPart(stringBodyKey, typeBody);


        httppost.setEntity(multipartEntityBuilder.build());

        HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                //上传成功
                HttpEntity respEntity = response.getEntity();

                String result = result = EntityUtils.toString(respEntity);
                JSONObject jsonObject = JSON.parseObject(result);
                return jsonObject.getString("filename");

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        return null;
    }

}
