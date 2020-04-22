import java.util.LinkedList;
import java.util.Queue;

public class SemaphoreTakingInput {
	Queue<Process>blockedQueue;
	boolean available;//if the semaphore is locked ,only the process which locked it can unlock it
	int processID;//the id of the process which locked the resource
	public SemaphoreTakingInput() {
		blockedQueue=new LinkedList<Process>();
		available=true;
		processID=-1;//when the the resource is available no process is locking it
	}
	public void semTakeInputWait(Process p) {
		if(available) {//the semaphore is available and can be taken
			available=false;
			this.processID=p.processID;
		}
		else {//the semaphore is locked by another process
			p.suspend();
			blockedQueue.add(p);
		}
	}
	public void semTakeInputPost(Process p) {
		if(p.processID!=this.processID){//some process which don't have the resource wants to unlock it and it is invalid
			return;
		}
		
		//the process which locked the resource wants to unlock it:
		available=true;
		this.processID=-1;
		if(blockedQueue.size()>0) {//if there are blocked processes that need this resources we can make them return to the ready state
			Process released=blockedQueue.poll();
			OperatingSystem.scheduler.readyQueue.add(released);
		}
	}
	
}
