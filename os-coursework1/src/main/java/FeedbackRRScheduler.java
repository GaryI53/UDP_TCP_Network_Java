import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Properties;

/**
 * Feedback Round Robin Scheduler
 * 
 * @version 2017
 */
public class FeedbackRRScheduler extends AbstractScheduler {
  // PriorityQueue<Process> priorityQueue = new PriorityQueue<Process>();
  // TODO
  LinkedList<Process> readyQueue1;  //level 1 of priority
  LinkedList<Process> readyQueue2; //level 2 of priority
  LinkedList<Process> readyQueue3; //level 3 of priority

  /**
   * Adds a process to the ready queue.
   * usedFullTimeQuantum is true if process is being moved to ready
   * after having fully used its time quantum.
   */

  public FeedbackRRScheduler() {
    readyQueue1 = new LinkedList<Process>();
    readyQueue2 = new LinkedList<Process>();
    readyQueue3 = new LinkedList<Process>();

  }

  public int getTimeQuantum() {
    return Integer.parseInt(Simulator.timeQuant);

  }

  public void ready(Process process, boolean usedFullTimeQuantum) {


    readyQueue1.offer(process);

  }

  /**
   * Removes the next process to be run from the ready queue
   * and returns it.
   * Returns null if there is no process to run.
   */
  public Process schedule() {

    //Checks priority 1 queue, if timeQuantum complete will demote
    System.out.println("Scheduler selects process " + readyQueue1.peek() + "\n");
    if (readyQueue1.peek() != null) { //if no process waiting will skip checking time quantum

      if (readyQueue1.peek().cpuTime > getTimeQuantum()) {
        readyQueue2.offer(readyQueue1.poll());  //if timeQuantum reached will remove from head, put to rear
      }
    }

    //Checks priority 2 queue, if timeQuantum complete will demote, otherwise will promote to 1
    if (readyQueue1.peek() == null) { //if priority one is empty will check queue 2
      if (readyQueue2.peek() != null) {
        if (readyQueue2.peek().cpuTime > getTimeQuantum() * 2) //checks increased quantum
        {
          readyQueue3.offer(readyQueue2.poll());  //if timeQuantum reached will demote
        } else {
          readyQueue1.offer(readyQueue2.poll());  //if timeQuantum not reached will promote
        }
      } else
        //if both queue priority 1 and 2 queues are empty will check queue 3
        if (readyQueue3.peek() != null) {
          if (readyQueue3.peek().cpuTime > getTimeQuantum() * 4) //checks increased quantum
          {
            readyQueue3.offer(readyQueue3.poll());  //can't be further demoted so added to back of 3
          } else {
            readyQueue2.offer(readyQueue3.poll());  //if timeQuantum not reached will promote
          }
        }
    }

    //System.out.println("ready queue 1 size is = " + readyQueue1.size());
    //System.out.println("ready queue 2 size is = " + readyQueue2.size());
    //System.out.println("ready queue 3 size is = " + readyQueue3.size());
    if (!readyQueue1.isEmpty()) {
      return readyQueue1.poll();
    }
    else if (!readyQueue2.isEmpty())
    {
      return readyQueue2.poll();
    }
    else
      return readyQueue3.poll();

  }
}
