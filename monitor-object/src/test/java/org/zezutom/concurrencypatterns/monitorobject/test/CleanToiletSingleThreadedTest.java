package org.zezutom.concurrencypatterns.monitorobject.test;

import org.junit.Before;
import org.junit.Test;
import org.zezutom.concurrencypatterns.monitorobject.CleanToilet;
import org.zezutom.concurrencypatterns.monitorobject.Toilet;
import org.zezutom.concurrencypatterns.monitorobject.ToiletFloodedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CleanToiletSingleThreadedTest {

    private Toilet toilet;

    @Before
    public void init() {
        toilet = new CleanToilet();
    }

    @Test
    public void acquireVacantToilet() throws ToiletFloodedException {
        assertTrue(toilet.enter());
        assertTrue(toilet.isOccupied());
    }

    @Test
    public void leaveOccupiedToilet() throws ToiletFloodedException {
        toilet.enter();
        toilet.quit();
        assertFalse(toilet.isOccupied());
    }
}
