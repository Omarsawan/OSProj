import java.util.concurrent.ConcurrentLinkedQueue;

public class SemaphoreReadData {
	private volatile ConcurrentLinkedQueue<Process> blockedQueue;
	private volatile boolean available;//if the semaphore is locked ,only the process which locked it can unlock it
	private volatile int processID;//the id of the process which locked the resource
	
	public SemaphoreReadData(){
		blockedQueue = new ConcurrentLinkedQueue<Process>();
		available = true;
		processID = -1;
	}
	
	@SuppressWarnings("deprecation")
	public void SemReadWait(Process p)
	{
		if(this.available)
		{
			this.processID = p.processID;
			available = false;
		}
		else
		{
			p.suspend();
			p.status = ProcessState.Waiting;
			blockedQueue.add(p);
		}
	}
	
	
	public void SemReadPost(Process p)
	{
			if(this.processID != p.processID)
			{
				return;
			}
			available = true;
			processID = -1;
			if(!blockedQueue.isEmpty())
			{
				OperatingSystem.scheduler.addProcess(blockedQueue.poll());
			}
	}
}
