import java.util.LinkedList;
import java.util.Properties;

/**
 * Ideal Shortest Job First Scheduler
 * 
 * @version 2017
 */
public class IdealSJFScheduler extends AbstractScheduler {

  LinkedList<Process> readyQueue;
  // TODO

  public IdealSJFScheduler() {
    readyQueue = new LinkedList<Process>();
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

    int shortestBurst = 0;
    int nextShortestIndex = 0;

    System.out.println("Scheduler selects process " + readyQueue.peek());

    if (readyQueue.peek() != null) { //Will not compare next burst if ready queue empty
      shortestBurst = readyQueue.peek().getNextBurst();
      for (int i = 0; i < readyQueue.size(); i++) {

        if (shortestBurst > readyQueue.get(i).getNextBurst()) //compares all next bursts to find shortest
        {
          shortestBurst = readyQueue.get(i).getNextBurst();
          nextShortestIndex = i;
        }
      }
      readyQueue.offer(readyQueue.remove(nextShortestIndex));  //removes shortest next burst and adds to front
    }
  return readyQueue.poll();
}
  }
