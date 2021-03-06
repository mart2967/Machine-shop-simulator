/** machine shop simulation */

package applications;

import utilities.MyInputStream;
import exceptions.MyInputException;

public class MachineShopSimulator {

    public static final String NUMBER_OF_MACHINES_MUST_BE_AT_LEAST_1 = "number of machines must be >= 1";
    public static final String NUMBER_OF_MACHINES_AND_JOBS_MUST_BE_AT_LEAST_1 = "number of machines and jobs must be >= 1";
    public static final String CHANGE_OVER_TIME_MUST_BE_AT_LEAST_0 = "change-over time must be >= 0";
    public static final String EACH_JOB_MUST_HAVE_AT_LEAST_1_TASK = "each job must have >= 1 task";
    public static final String BAD_MACHINE_NUMBER_OR_TASK_TIME = "bad machine number or task time";

    // data members of MachineShopSimulator
    private static int timeNow; // current time
    private static int numMachines; // number of machines
    private static int numJobs; // number of jobs
    //private static EventList eList; // pointer to event list
    private static Machine[] machines; // array of machines
    private static int largeTime; // all machines finish before this

    // methods
    /**
     * move theJob to machine for its next task
     * 
     * @return false iff no next task
     */
    static boolean moveToNextMachine(Job theJob) {
        if (theJob.hasNoTasks()) {// no next task
            System.out.println("Job " + theJob.getId() + " has completed at "
                    + timeNow + " Total wait was "
                    + (timeNow - theJob.getLength()));
            return false;
        }
        // get machine for next task
        int p = ((Task) theJob.getTaskQ().getFrontElement()).getMachine();  
        // put on machine p's wait queue
        machines[p].addToJobQueue(theJob);
        theJob.setArrivalTime(timeNow);
        // if p idle, schedule immediately
        if (nextEventTime(p) == largeTime) {// machine is idle
            changeState(p);
        }
        return true;
    }

    /**
     * change the state of theMachine
     * 
     * @return last job run on this machine
     */
    static Job changeState(int theMachine) {// Task on theMachine has finished,
        // schedule next one.
        Job lastJob;
        Machine currentMachine = machines[theMachine];
        if (currentMachine.getActiveJob() == null) {// in idle or change-over
            // state
            lastJob = null;
            // wait over, ready for new job
            if (currentMachine.isIdle()){ // no waiting job
                currentMachine.setFinishTime(largeTime);
            }
            else {// take job off the queue and work on it
                currentMachine.workOnJob(timeNow);
                currentMachine.setFinishTime(timeNow + currentMachine.getActiveJob().removeNextTask());
            }
        } else {// task has just finished on machine[theMachine]
            // schedule change-over time
            lastJob = currentMachine.getActiveJob();
            currentMachine.setActiveJob(null);
            currentMachine.setFinishTime(timeNow + currentMachine.getChangeTime());
        }

        return lastJob;
    }

    public static int nextFinishingMachine() {
        // find first machine to finish, this is the
        // machine with smallest finish time
        int p = 0; // index fix
        for (int i = 1; i < machines.length; i++) {
            if (machines[i].getFinishTime() < machines[p].getFinishTime()) {// i finishes earlier
                p = i;
            }
        }
        return p;
    }
    
    public static int nextEventTime(int theMachine) {
        return machines[theMachine].getFinishTime();
    }
    
    public void setFinishTime(int theMachine, int theTime) {
        machines[theMachine].setFinishTime(theTime);
    }
    
    /** input machine shop data */
    static void inputData() {
        // define the input stream to be the standard input stream
        MyInputStream keyboard = new MyInputStream();

        System.out.println("Enter number of machines and jobs");
        numMachines = keyboard.readInteger();
        numJobs = keyboard.readInteger();
        if (numMachines < 1 || numJobs < 1){
            throw new MyInputException(NUMBER_OF_MACHINES_AND_JOBS_MUST_BE_AT_LEAST_1);
        }
        // create event and machine queues
        //eList = new EventList(numMachines, largeTime);
        machines = new Machine[numMachines];
        for (int i = 0; i < numMachines; i++){
            machines[i] = new Machine();
        }
        // input the change-over times
        System.out.println("Enter change-over times for machines");
        for (int j = 0; j < numMachines; j++) {
            int ct = keyboard.readInteger();
            if (ct < 0){
                throw new MyInputException(CHANGE_OVER_TIME_MUST_BE_AT_LEAST_0);
            }
            machines[j].setChangeTime(ct);
        }

        // input the jobs
        for (int i = 0; i < numJobs; i++) {
            System.out.println("Enter number of tasks for job " + (i + 1));
            int tasks = keyboard.readInteger(); // number of tasks
            int firstMachine = 0; // machine for first task
            if (tasks < 1) {
                throw new MyInputException(EACH_JOB_MUST_HAVE_AT_LEAST_1_TASK);
            }
            // create the job
            Job theJob = new Job(i+1); 
            System.out.println("Enter the tasks (machine, time)"
                    + " in process order");
            for (int j = 0; j < tasks; j++) {// get tasks for job i
                int theMachine = keyboard.readInteger();
                int theTaskTime = keyboard.readInteger();
                if (theMachine < 1 || theMachine > numMachines || theTaskTime < 1) {
                    throw new MyInputException(BAD_MACHINE_NUMBER_OR_TASK_TIME);
                }
                if (j == 0) {
                    firstMachine = theMachine-1; // job's first machine
                }
                theJob.addTask(theMachine-1, theTaskTime); // add to
            } // task queue
            machines[firstMachine].addToJobQueue(theJob);
        }
    }

    /** load first jobs onto each machine */
    static void startShop() {
        for (int p = 0; p < numMachines; p++){
            changeState(p);
        }
    }

    /** process all jobs to completion */
    static void simulate() {
        while (numJobs > 0) {// at least one job left
            int nextToFinish = nextFinishingMachine();
            timeNow = nextEventTime(nextToFinish);
            // change job on machine nextToFinish
            Job theJob = changeState(nextToFinish);
            // move theJob to its next machine
            // decrement numJobs if theJob has finished
            if (theJob != null && !moveToNextMachine(theJob)) {
                numJobs--;
            }
        }
    }

    /** output wait times at machines */
    static void outputStatistics() {
        System.out.println("Finish time = " + timeNow);
        for (int p = 0; p < numMachines; p++) { 
            System.out.println("Machine " + (p+1) + " completed " + machines[p].getNumTasks() + " tasks");
            System.out.println("The total wait time was " + machines[p].getTotalWait());
            System.out.println();
        }
    }

    /** entry point for machine shop simulator */
    public static void main(String[] args) {
        largeTime = Integer.MAX_VALUE;
        /*
         * It's vital that we (re)set this to 0 because if the simulator is
         * called multiple times (as happens in the acceptance tests), because
         * timeNow is static it ends up carrying over from the last time it was
         * run. I'm not convinced this is the best place for this to happen,
         * though.
         */
        timeNow = 0;
        inputData(); // get machine and job data
        startShop(); // initial machine loading
        simulate(); // run all jobs through shop
        outputStatistics(); // output machine wait times
    }
}
