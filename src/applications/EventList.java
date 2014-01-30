package applications;

public class EventList {
	// data members
	private int[] finishTime; // finish time array

	public int[] getFinishTime() {
		return finishTime;
	}



	// constructor
	public EventList(int theNumMachines, int theLargeTime) {

		if (theNumMachines < 1)
			throw new IllegalArgumentException(MachineShopSimulator.NUMBER_OF_MACHINES_MUST_BE_AT_LEAST_1);
		finishTime = new int[theNumMachines + 1];

		// all machines are idle, initialize with
		// large finish time
		for (int i = 1; i <= theNumMachines; i++) {
			finishTime[i] = theLargeTime;
		}
	}

	/** @return machine for next event */
	public int nextEventMachine() {
		// find first machine to finish, this is the
		// machine with smallest finish time
		int p = 1;
		for (int i = 2; i < finishTime.length; i++) {
			if (finishTime[i] < finishTime[p]) {// i finishes earlier
				p = i;
			}
		}
		return p;
	}

	public int nextEventTime(int theMachine) {
		return finishTime[theMachine];
	}

	public void setFinishTime(int theMachine, int theTime) {
		finishTime[theMachine] = theTime;
	}
}
