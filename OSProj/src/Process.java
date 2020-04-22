//import java.util.concurrent.Semaphore;


public class Process extends Thread {
	
	public int processID;
    ProcessState status=ProcessState.New;	

	
	public Process(int m) {
		processID = m;
	}
	@Override
	public void run() {
		
		switch(processID)
		{
		case 1:process1();break;
		case 2:process2();break;
		case 3:process3();break;
		case 4:process4();break;
		case 5:process5();break;
		}

	}
	
	private void process1() {
		
		OperatingSystem.printText("Enter File Name: ");
		
		OperatingSystem.semaphoreTakeInput.semTakeInputWait(this);
		String fileName=OperatingSystem.TakeInput();
		OperatingSystem.semaphoreTakeInput.semTakeInputPost(this);
		
		String print=OperatingSystem.readFile(fileName);
		
		OperatingSystem.printText(print);
		
		setProcessState(this,ProcessState.Terminated);
	}
	
	private void process2() {
		
		OperatingSystem.printText("Enter File Name: ");
		
		OperatingSystem.semaphoreTakeInput.semTakeInputWait(this);
		String filename= OperatingSystem.TakeInput();
		OperatingSystem.semaphoreTakeInput.semTakeInputPost(this);
		
		OperatingSystem.printText("Enter Data: ");
		
		OperatingSystem.semaphoreTakeInput.semTakeInputWait(this);
		String data= OperatingSystem.TakeInput();
		OperatingSystem.semaphoreTakeInput.semTakeInputPost(this);
		
		OperatingSystem.semaphoreWriteToFile.semWriteWait(this);
		OperatingSystem.writefile(filename,data);
		OperatingSystem.semaphoreWriteToFile.semWritePost(this);
		
		setProcessState(this,ProcessState.Terminated);
	}
	private void process3() {
		int x=0;
		while (x<301)
		{ 
			OperatingSystem.printText(x+"\n");
			x++;
		}
		setProcessState(this,ProcessState.Terminated);
	}
	
	private void process4() {
	
		int x=500;
		while (x<1001)
		{
			OperatingSystem.printText(x+"\n");
			x++;
		}	
		setProcessState(this,ProcessState.Terminated);
	}
	private void process5() {
		
		OperatingSystem.printText("Enter LowerBound: ");
		
		OperatingSystem.semaphoreTakeInput.semTakeInputWait(this);
		String lower= OperatingSystem.TakeInput();
		OperatingSystem.semaphoreTakeInput.semTakeInputPost(this);
		
		OperatingSystem.printText("Enter UpperBound: ");
		
		OperatingSystem.semaphoreTakeInput.semTakeInputWait(this);
		String upper= OperatingSystem.TakeInput();
		OperatingSystem.semaphoreTakeInput.semTakeInputPost(this);
		
		int lowernbr=Integer.parseInt(lower);
		int uppernbr=Integer.parseInt(upper);
		String data="";
		
		while (lowernbr<=uppernbr)
		{
			data+=lowernbr++ +"\n";
		}	
		
		OperatingSystem.semaphoreWriteToFile.semWriteWait(this);
		OperatingSystem.writefile("P5.txt", data);
		OperatingSystem.semaphoreWriteToFile.semWritePost(this);
		
		setProcessState(this,ProcessState.Terminated);
	}
	
	public static void setProcessState(Process p, ProcessState s) {
		 p.status=s;
//		 if (s == ProcessState.Terminated)
//		 {
//			 OperatingSystem.ProcessTable.remove(OperatingSystem.ProcessTable.indexOf(p));
//		 }
	}
	 
	 public static ProcessState getProcessState(Process p) {
		 return p.status;
	 }
}
