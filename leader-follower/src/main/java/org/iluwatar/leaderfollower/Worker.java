
package org.iluwatar.leaderfollower;

import java.util.List;

public class Worker implements Runnable {

  private final HandleSet handleSet;
  private List<Worker> workers;
  public final long id;
  private final WorkStation workstation;
  private final ConcreteEventHandler concreteEventHandler;

  /**
   * 
   * Constructor to create a worker which will take work from the work station.
   */
  public Worker(HandleSet queue, List<Worker> workers, long id, WorkStation workstation,
      ConcreteEventHandler concreteEventHandler) {
    super();
    this.handleSet = queue;
    this.workers = workers;
    this.id = id;
    this.workstation = workstation;
    this.concreteEventHandler = concreteEventHandler;
  }

  /**
   * Become the leader, and notify others
   */
  public void becomeLeader() {
    synchronized (workstation) {
      workstation.notifyAll();
    }
  }



  @Override
  public void run() {
    while (!Thread.interrupted()) {
      try {
        if (workstation.getLeader() != null && !workstation.getLeader().equals(this)) {
          System.out.println("ID " +id + " is follower");
          synchronized (workstation) {
            workstation.wait();
          }

        }
        //
        if(handleSet.getQueue().size()>0){
        workers.remove(this);
        System.out.println("Leader: " + id);
        Work work = handleSet.getPayLoad();
        work.setHandled();
        if (workers.size() > 0) {
          workstation.getWorkers().get(0).becomeLeader();
          workstation.setLeader(workstation.getWorkers().get(0));
          //System.out.println("Id " +workstation.getLeader().id +" Became Leader");
        } else {
          workstation.setLeader(null);
        }
        synchronized (workstation) {
          workstation.notifyAll();
        }
        concreteEventHandler.handleEvent(work);
        Thread.sleep(100);
        System.out.println("The Worker with the ID " + id + " completed the task");
        workstation.addWorker(this);}
        else{
          if(workstation.getLeader()==null){
            if (workers.size() > 0) {
              workstation.getWorkers().get(0).becomeLeader();
              workstation.setLeader(workstation.getWorkers().get(0));
              System.out.println("Id " +workstation.getLeader().id +" Became Leader");
            } else {
              workstation.setLeader(null);
            }
            synchronized (workstation) {
              workstation.notifyAll();
            }
          }
        }
      } catch (InterruptedException e) {
        System.out.println("Worker intreuppted");
        return;
      }
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    return result;
  }

  /**
   * Overridden equals method
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Worker other = (Worker) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }

}
