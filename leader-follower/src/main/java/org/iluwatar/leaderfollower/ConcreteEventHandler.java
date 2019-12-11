
package org.iluwatar.leaderfollower;

public class ConcreteEventHandler implements EventHandler {

  @Override
  public void handleEvent(Handle handle) {
    System.out.println("Doing the work");
    int distance = handle.getPayLoad();
    handle.setHandled();
    System.out.println("Travelled the distance " + distance);
  }

}
