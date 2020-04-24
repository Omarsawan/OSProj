import java.util.concurrent.ConcurrentLinkedQueue;

public class SemaphoreTakingInput {
	private volatile ConcurrentLinkedQueue<Process>blockedQueue;
	private volatile boolean available;//if the semaphore is locked ,only the process which locked it can unlock it
	private volatile int processID;//the id of the process which locked the resource
	public SemaphoreTakingInput() {
		blockedQueue=new ConcurrentLinkedQueue<Process>();
		available=true;
		processID=-1;//when the the resource is available no process is locking it
	}
	@SuppressWarnings("deprecation")
	public void semTakeInputWait(Process p) {

		if(!available)
		{
			System.out.println(p.processID +" is Blocked while requiring : " + this.getClass());
			p.status = ProcessState.Waiting;
			blockedQueue.add(p);
			p.suspend();
		}
		this.processID = p.processID;
		available = false;
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
			released.status = ProcessState.Ready;
			//released.resume();
			OperatingSystem.scheduler.addProcess(released);
		}
	}
	
}
