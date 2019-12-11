package com.iluwatar.leaderfollower;

import org.iluwatar.leaderfollower.HandleSet;
import org.iluwatar.leaderfollower.Work;
import org.junit.Assert;
import org.junit.Test;

import java.net.ServerSocket;

public class HandleSetTest {

  @Test
  public void testFireEvent() throws InterruptedException {

    HandleSet handleSet = new HandleSet();
    handleSet.fireEvent(new Work(10));
    handleSet.fireEvent(new Work(20));
    Assert.assertTrue(handleSet.getQueue().size() == 2);
  }

  @Test
  public void testGetEvent() throws InterruptedException {
    HandleSet handleSet = new HandleSet();
    handleSet.fireEvent(new Work(10));
    Work work = handleSet.getPayLoad();
    Assert.assertTrue(work.distance == 10);
    Assert.assertTrue(handleSet.getQueue().size() == 0);
  }

}
