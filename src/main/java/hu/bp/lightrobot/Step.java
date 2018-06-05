package hu.bp.lightrobot;

public class Step {
    public final int observation;
    public final double reward;
    public final boolean done;

    public Step(int observation, double reward, boolean done) {
        this.observation = observation;
        this.reward = reward;
        this.done = done;
    }
}
