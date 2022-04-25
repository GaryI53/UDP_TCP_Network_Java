import java.util.LinkedList;
import java.util.Properties;

/**
 * Ideal Shortest Job First Scheduler
 *
 * @version 2017
 */
public class SJFScheduler extends AbstractScheduler {

  LinkedList<Process> readyQueue;
  // TODO

  public SJFScheduler() {
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

  public int initialBurstEstimate() {
    return Integer.parseInt(Simulator.initialBurstEstimate);
  }

  public float alphaBurstEstimate() {
    return Float.parseFloat(Simulator.alphaBurstEstimate);
  }


  /**
   * Removes the next process to be run from the ready queue
   * and returns it.
   * Returns null if there is no process to run.
   */
  public Process schedule() {


    float shortestBurst = 0;
    int nextShortestIndex = 0;

    System.out.println("Scheduler selects process " + readyQueue.peek());

    if (readyQueue.peek() != null) { //Will not compare next burst if ready queue empty

      readyQueue.peek().setExponentialBurstTn((readyQueue.peek().getRecentBurst() * alphaBurstEstimate()) + ((1-alphaBurstEstimate())*initialBurstEstimate()));
      shortestBurst = readyQueue.peek().getExponentialBurstTn();

      for (int i = 0; i < readyQueue.size(); i++) {

        readyQueue.get(i).setExponentialBurstTn((readyQueue.get(i).getRecentBurst() * alphaBurstEstimate()) + ((1-alphaBurstEstimate())*initialBurstEstimate()));

        if (shortestBurst > readyQueue.get(i).getExponentialBurstTn()) {

          shortestBurst = readyQueue.get(i).getExponentialBurstTn();
          nextShortestIndex = i;
        }


       // }
      }
      readyQueue.offer(readyQueue.remove(nextShortestIndex));  //removes shortest next burst and adds to front
     // System.out.println("shortest added to front" + readyQueue.get(nextShortestIndex));
    }
    return readyQueue.poll();
  }
}
