package hu.bp.ai.rl;

public class EpisodeStep {
	public final int prevState;
	public final int action;
	public final double reward;
	public final int newState;

	public EpisodeStep(int prevState, int action, double reward, int newState) {
		this.prevState = prevState;
		this.action = action;
		this.reward = reward;
		this.newState = newState;
	}

	@Override
	public String toString() {
		return "{" + prevState + "," + action + ">" + newState + ":" + reward + "}";
	}
}
