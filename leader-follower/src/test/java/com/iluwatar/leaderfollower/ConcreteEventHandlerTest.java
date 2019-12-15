
package com.iluwatar.leaderfollower;

import org.iluwatar.leaderfollower.ConcreteEventHandler;
import org.iluwatar.leaderfollower.Work;
import org.junit.Assert;
import org.junit.Test;

public class ConcreteEventHandlerTest {

  @Test
  public void testEventHandling() throws InterruptedException {
    ConcreteEventHandler concreteEventHandler = new ConcreteEventHandler();
    Work handle = new Work(10);
    concreteEventHandler.handleEvent(handle);
    Assert.assertTrue(handle.isHandled());
  }

}
