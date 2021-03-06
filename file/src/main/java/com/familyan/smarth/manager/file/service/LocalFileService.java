package com.familyan.smarth.manager.file.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Koala on 2015/10/28 0028.
 */
public class LocalFileService {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String getTempDir() {
        String tempDir = System.getProperty("java.io.tmpdir");
        if(tempDir == null || tempDir.equals("")) {
            tempDir = System.getProperty("user.home") + File.separator + "temp";
        }
        System.out.println("tempdir :" + tempDir);
        return  tempDir;
    }

    public static String saveTempFile(String filename, InputStream is) {
        String tempDir = getTempDir();
        File dir = new File(tempDir);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        String tempFilePath = tempDir + File.separator + filename;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            bis = new BufferedInputStream(is);
            byte[] buf  = new byte[1024];
            int size = 0;
            fos = new FileOutputStream(tempFilePath);
            while ( (size = bis.read(buf)) != -1)
                fos.write(buf, 0, size);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null)  {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return  tempFilePath;
    }

    public static String saveTempFile(InputStream inputStream) {
        String datetime = DATE_FORMAT.format(new Date());
        String tempFilename = datetime + String.valueOf(Math.random()).replace("0.", "");
        return saveTempFile(tempFilename, inputStream);
    }



    public static void main(String[] args) {
        System.out.println(System.getProperty("user.home"));
        System.out.println(String.valueOf(Math.random()).replace("0.", ""));
    }
}
