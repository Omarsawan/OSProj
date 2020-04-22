import java.util.concurrent.ConcurrentLinkedQueue;

public class SemaphoreWritingToFile {
	private volatile ConcurrentLinkedQueue<Process>blockedQueue;
	private volatile boolean available;//if the semaphore is locked ,only the process which locked it can unlock it
	private volatile int processID;//the id of the process which locked the resource
	public SemaphoreWritingToFile() {
		blockedQueue=new ConcurrentLinkedQueue<Process>();
		available=true;
		processID=-1;//when the the resource is available no process is locking it
	}
	@SuppressWarnings("deprecation")
	public void semWriteWait(Process p) {
		if(available) {//the semaphore is available and can be taken
			available=false;
			this.processID=p.processID;
		}
		else {//the semaphore is locked by another process
			p.suspend();
			p.status = ProcessState.Waiting;
			blockedQueue.add(p);
		}
	}
	public void semWritePost(Process p) {
		if(p.processID!=this.processID){//some process which don't have the resource wants to unlock it and it is invalid
			return;
		}
		
		//the process which locked the resource wants to unlock it:
		available=true;
		this.processID=-1;
		if(blockedQueue.size()>0) {//if there are blocked processes that need this resources we can make them return to the ready state
			Process released=blockedQueue.poll();
			OperatingSystem.scheduler.addProcess(released);
		}
	}
	
}
