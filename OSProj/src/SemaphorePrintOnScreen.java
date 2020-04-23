import java.util.concurrent.ConcurrentLinkedQueue;

public class SemaphorePrintOnScreen {
	private volatile ConcurrentLinkedQueue<Process> blockedQueue;
	private volatile boolean available;//if the semaphore is locked ,only the process which locked it can unlock it
	private volatile int processID;//the id of the process which locked the resource
	
	public SemaphorePrintOnScreen() {
		blockedQueue = new ConcurrentLinkedQueue<Process>();
		available = true;
		processID = -1;
	}
	
	@SuppressWarnings("deprecation")
	public void SemPrintWait(Process p)
	{
		if(available)
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

	public void SemPrintPost(Process p)
	{
		if(this.processID != p.processID)
		{
			return;
		}
		available = true;
		processID = -1;
		if(!blockedQueue.isEmpty())
		{
			Process released=blockedQueue.poll();
			released.status = ProcessState.Ready;
			OperatingSystem.scheduler.addProcess(released);
		}
		
	}


}
