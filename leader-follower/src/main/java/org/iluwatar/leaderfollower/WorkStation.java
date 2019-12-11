package org.iluwatar.leaderfollower;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents the Workstation for the leader Follower pattern. Contains a leader and a list of idle workers. Contains a
 * leader who is responsible for receiving work when it arrives. This class also provides a mechanism to set the leader.
 * A worker once he completes his task will add himself back to the station.
 */
public class WorkStation {
  private Worker leader;
  private List<Worker> workers = new CopyOnWriteArrayList<>();
  private ExecutorService executorService = Executors.newFixedThreadPool(4);

  public WorkStation(ExecutorService executorService) {
    this.executorService = executorService;
  }

  /**
   * Start the work. Add workers and then dispatch new work to be processed by the set of workers
   */
  public void startWork() throws InterruptedException {
    int i = 1;
    HandleSet handleSet = new HandleSet();
    ConcreteEventHandler concreteEventHandler = new ConcreteEventHandler();
    while (i <= 4) {
      Worker worker = new Worker(handleSet, workers, i, this, concreteEventHandler);
      this.workers.add(worker);
      i++;
    }
    System.out.println(this.workers.size());
    this.leader = this.workers.get(0);
    executorService.submit(this.workers.get(0));
    executorService.submit(this.workers.get(1));
    executorService.submit(this.workers.get(2));
    executorService.submit(this.workers.get(3));
    Random rand = new Random(1000);
    int j = 0;
    while (j < 4) {
      handleSet.fireEvent(new Work(10*(j+1)));
      j++;
    }
    handleSet.fireEvent(new Work(30));
    Thread.sleep(1000);
  }

  public Worker getLeader() {
    return this.leader;
  }

  public void setLeader(Worker leader) {
    this.leader = leader;
  }

  /**
   * Add a worker to the work station.
   */
  public void addWorker(Worker worker) {
    if (this.workers.size() <= 0) {
      workers.add(worker);
    }
  }

  public List<Worker> getWorkers() {
    return workers;
  }
}
