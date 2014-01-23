package applications;

public class Task {
	private int machine;
	private int time;
	
	public int getMachine() {
		return machine;
	}
	
	public void setMachine(int newMachine) {
		machine = newMachine;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int newTime) {
		time = newTime;
	}

	public Task(int theMachine, int theTime) {
		machine = theMachine;
        time = theTime;
	}
}