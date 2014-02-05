package applications;

public class Task {
    private int machine;
    private int time;

    public int getMachine() {
        return machine;
    }

    public int getTime() {
        return time;
    }

    public Task(int theMachine, int theTime) {
        machine = theMachine;
        time = theTime;
    }
}