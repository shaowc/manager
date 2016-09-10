package com.familyan.smarth.manager.file.utils;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Koala on 2015/10/26 0026.
 */
public class MimeUtils {

    private static final String JPEG = "image/jpeg";
    private static final String GIF = "image/gif";
    private static final String PNG = "image/png";
    static{
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
    }
    public static final String getMimeType(String filename) throws Exception{
        InputStream in = null;
        try{
            in = new FileInputStream(filename);
            return getMimeType(new BufferedInputStream(in));
        }finally{
            if(in != null){
                try{
                    in.close();
                }catch(Exception e){}
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static final String getMimeType(InputStream input) throws Exception{
        if(!(input instanceof BufferedInputStream)){
            input = new BufferedInputStream(input);
        }
        Collection<MimeType> mimeTypes = MimeUtil.getMimeTypes(input);
        Iterator<MimeType> iter = mimeTypes.iterator();
        while(iter.hasNext()){
            MimeType mimeType = iter.next();
            return mimeType.toString();
        }

        throw new Exception("unknow mimetype");
    }

}
