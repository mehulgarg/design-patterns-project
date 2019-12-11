
package org.iluwatar.leaderfollower;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
  public static void main(String[] args) throws InterruptedException {
    ExecutorService exec = Executors.newFixedThreadPool(4);
    WorkStation station = new WorkStation(exec);
    station.startWork();

    exec.awaitTermination(20, TimeUnit.SECONDS);
    exec.shutdownNow();
  }
}
