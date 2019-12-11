
package org.iluwatar.leaderfollower;

public class Work implements Handle {
  public final int distance;
  
  private boolean handled;

  public Work(int distance) {
    this.distance = distance;
  }

  @Override
  public int getPayLoad() {
    return distance;
  }

  @Override
  public void setHandled() {
    this.handled = true;
  }

  @Override
  public boolean isHandled() {
    return this.handled;
  }

}
