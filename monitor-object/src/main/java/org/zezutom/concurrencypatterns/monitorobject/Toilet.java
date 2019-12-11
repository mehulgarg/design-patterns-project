package org.zezutom.concurrencypatterns.monitorobject;


public interface Toilet {

    boolean enter();

    void quit();

    boolean isOccupied();
}
