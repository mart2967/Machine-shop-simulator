package applications;

import dataStructures.LinkedQueue;

public class Machine {
	// data members
    LinkedQueue jobQ; // queue of waiting jobs for this machine
    int changeTime; // machine change-over time
    int totalWait; // total delay at this machine
    int numTasks; // number of tasks processed on this machine
    Job activeJob; // job currently active on this machine

    // constructor
    public Machine() {
        jobQ = new LinkedQueue();
    }

	public LinkedQueue getJobQ() {
		return jobQ;
	}

	public void setJobQ(LinkedQueue jobQ) {
		this.jobQ = jobQ;
	}

	public int getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(int changeTime) {
		this.changeTime = changeTime;
	}

	public int getTotalWait() {
		return totalWait;
	}

	public void setTotalWait(int totalWait) {
		this.totalWait = totalWait;
	}

	public int getNumTasks() {
		return numTasks;
	}

	public void setNumTasks(int numTasks) {
		this.numTasks = numTasks;
	}

	public Job getActiveJob() {
		return activeJob;
	}

	public void setActiveJob(Job activeJob) {
		this.activeJob = activeJob;
	}
}
