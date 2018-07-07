package hu.bp.ai.rl;

public class QLearningStepState {
	public final Double[][] Q;
	public final int numOfEpisodes;
	public final int stepsInEpizode;
	public final double learningRate;
	public final double discount;
	public int state;
	public int i;

	public QLearningStepState(Double[][] q, int numOfEpisodes, int stepsInEpizode, double learningRate, double discount, int state) {
		Q = q;
		this.numOfEpisodes = numOfEpisodes;
		this.stepsInEpizode = stepsInEpizode;
		this.learningRate = learningRate;
		this.discount = discount;
		this.state = state;
		this.i = 0;
	}

	public boolean isContinue() {
		return i < stepsInEpizode * numOfEpisodes;
	}
}
