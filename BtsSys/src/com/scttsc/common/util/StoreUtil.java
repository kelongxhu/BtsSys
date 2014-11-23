package com.scttsc.common.util;

import com.scttsc.business.util.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by _think on 2014/11/16.
 */
public class StoreUtil {
    Logger LOG = Logger.getLogger(StoreUtil.class);

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
            if (tmpFile != null) {
                File formalFile = new File(descPath+ "/" + uuid);
                FileUtils.copyFile(tmpFile, formalFile);
                if(tmpFile.exists()){
                    tmpFile.delete();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
