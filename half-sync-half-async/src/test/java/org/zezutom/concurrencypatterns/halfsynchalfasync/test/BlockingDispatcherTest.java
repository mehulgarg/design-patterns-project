package org.zezutom.concurrencypatterns.halfsynchalfasync.test;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zezutom.concurrencypatterns.halfsynchalfasync.BlockingDispatcher;
import org.zezutom.concurrencypatterns.halfsynchalfasync.BlockingDispatcher;
import org.zezutom.concurrencypatterns.test.util.DataUtil;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class BlockingDispatcherTest {

    public static final String IMAGE = "audrey_hepburn.jpg";

    public static final String OUT_TEST = "audrey-test.txt";

    public static final String OUT_ORIGINAL = "audrey.txt";

    private BlockingDispatcher app = new BlockingDispatcher();

    @After
    public void cleanUp() {
        final File asciiFile = DataUtil.getFile(OUT_TEST);
        if (asciiFile.exists()) asciiFile.delete();
    }

    @Test
    public void asciiArtRocks() throws IOException {
    	final File imageFile = DataUtil.getFile(IMAGE);
        assertTrue(app.convertToAscii(imageFile.getAbsolutePath(), OUT_TEST));
        assertTrue(FileUtils.contentEquals(DataUtil.getFile(OUT_ORIGINAL), DataUtil.getFile(OUT_TEST)));
    }

}
