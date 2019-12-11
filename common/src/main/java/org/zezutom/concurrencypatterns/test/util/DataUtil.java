package org.zezutom.concurrencypatterns.test.util;

import java.io.File;


public class DataUtil {

    private DataUtil() {}

    public static File getFile(String filename) {
    	System.out.println(System.getProperty("user.dir") + "\\data\\");
        return new File(System.getProperty("user.dir") + "\\data\\" + filename);
    }
}
