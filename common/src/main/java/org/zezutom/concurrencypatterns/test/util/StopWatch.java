package org.zezutom.concurrencypatterns.test.util;


public class StopWatch {

    private long startTime = 0;

    private long stopTime = 0;

    private boolean running = false;

    public void start() {
        startTime = System.currentTimeMillis();
        running = true;
    }

    public void stop() {
        stopTime = System.currentTimeMillis();
        running = false;
    }

    public long elapsedTime() {
        return (running) ? System.currentTimeMillis() - startTime : stopTime - startTime;
    }
}
