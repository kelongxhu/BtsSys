package com.scttsc.common.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * Created by _think on 2014/11/16.
 */
public class StoreUtil {
    private static Logger LOG = Logger.getLogger(StoreUtil.class);

    static {
        init();
    }

    /**
     * init Directory
     */
    public static void init() {
        String tmpDir = storeTmpDir();
        File tmpFile = new File(tmpDir);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
//        String formalDir = storeDir();
//        File formalFile = new File(formalDir);
//        if (!formalFile.exists()) {
//            formalFile.mkdirs();
//        }
    }

    /**
     * copy temp file to farmal directory
     *
     * @param uuid
     */
    public static void copyFile(String uuid,String descPath) {
        File tmpFile = new File(storeTmpDir() + "/" + uuid);
        try {
            if (tmpFile != null && tmpFile.exists()) {
                File formalFile = new File(descPath+ "/" + uuid);
                FileUtils.copyFile(tmpFile, formalFile);
                tmpFile.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteFile(String filePath) throws Exception{
    	try {
    		File file = new File(filePath);
    		if(file.exists()&&file.isFile()){
    			file.delete();
    		}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
    }
    
    public static void deleteDir(String dirPath) throws Exception{
    	File file = new File(dirPath);
    	if(file.isDirectory()){
    		for(File file2 : file.listFiles()){
    			if(file2.exists()&&file2.isFile())
    				file2.delete();
    		}
    	}
    }

    /**
     * tmp dir for file
     *
     * @return
     */
    public static String storeTmpDir() {
        String tmpDir = System.getProperty("tmp_dir");
        if (tmpDir == null) {
            tmpDir = System.getProperty("java.io.tmpdir");
        }
        return tmpDir + "/store_tmp/";
    }

    /**
     * formal dir for file
     *
     * @return
     */
    public static String storeDir() {
        String dir = System.getProperty("catalina.home");
        return dir + "/bts-store/charge";
    }
}
