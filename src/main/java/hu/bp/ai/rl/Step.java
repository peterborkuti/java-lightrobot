package hu.bp.ai.rl;

public class Step {
    public final int observation;
    public final double reward;
    public final boolean done;

    public Step() {
        this(0, 0, false);
    }

    public Step(int observation, double reward, boolean done) {
        this.observation = observation;
        this.reward = reward;
        this.done = done;
    }

    public String toString() {
        return "{observation:" + observation + ", reward:" + reward + "}";
    }
}
