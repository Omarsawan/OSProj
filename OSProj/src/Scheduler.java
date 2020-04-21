import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	Queue<Thread> readyQueue;
	public Scheduler() {
		readyQueue = new LinkedList<Thread>();
	}
	public void addProcess(Process p) {
		readyQueue.add(p);
	}
	public void start() {
		while(!readyQueue.isEmpty()) {
			Process p = (Process) readyQueue.poll();
			p.run();
			while(Process.getProcessState(p) != ProcessState.Terminated);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
