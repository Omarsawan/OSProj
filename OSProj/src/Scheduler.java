import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler {
	private volatile ConcurrentLinkedQueue<Thread> readyQueue;
	public Scheduler() {
		readyQueue = new ConcurrentLinkedQueue<Thread>();
	}
	public void addProcess(Process p) {
		readyQueue.add(p);
	}
	public void start() {
		while(!readyQueue.isEmpty()) {
			Process p = (Process) readyQueue.poll();
			p.start();
			while(Process.getProcessState(p) != ProcessState.Terminated);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
