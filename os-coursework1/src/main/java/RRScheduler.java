import java.util.LinkedList;
import java.util.Properties;

/**
 * Round Robin Scheduler
 *
 * @version 2017
 */

public class RRScheduler extends AbstractScheduler {
  LinkedList<Process> readyQueue;

  // TODO

  public RRScheduler() {
    readyQueue = new LinkedList<Process>();

  }

  public int getTimeQuantum() {
    return Integer.parseInt(Simulator.timeQuant);

  }

  /**
   * Adds a process to the ready queue.
   * usedFullTimeQuantum is true if process is being moved to ready
   * after having fully used its time quantum.
   */
  public void ready(Process process, boolean usedFullTimeQuantum) {

      readyQueue.offer(process);

  }



  /**
   * Removes the next process to be run from the ready queue
   * and returns it.
   * Returns null if there is no process to run.
   */
  public Process schedule() {

    System.out.println("Scheduler selects process " + readyQueue.peek() + "\n");
    if (readyQueue.peek() != null) { //if no process waiting will skip checking time quantum

      if(readyQueue.peek().cpuTime >= getTimeQuantum()) {
        readyQueue.offer(readyQueue.poll());  //if timeQuantum reached will remove from head, put to rear
      }
    }

      return readyQueue.poll();



  }




}
