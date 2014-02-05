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

	public boolean isIdle() {
		return jobQ.isEmpty();
	}

	public void incrementNumTasks() {
		numTasks++;
	}


	public void setActiveJobFromQueue() {
		activeJob = (Job) jobQ.remove();
	}

	public void addToJobQueue(Job job) {
		jobQ.put(job);
	}

	public void setTotalWaitTime(int currentTime) {
		totalWait = totalWait + currentTime - activeJob.getArrivalTime();
	}
}
