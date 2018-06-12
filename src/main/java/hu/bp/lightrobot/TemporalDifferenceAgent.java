package hu.bp.lightrobot;

import java.util.List;

public abstract class TemporalDifferenceAgent implements Agent {
	protected final Environment world;

	protected Double[][] policy;
	protected Double[][] q;
	protected List<Double> returns[][];

	protected double stepSize = 0.1;
	protected double epsilon = 0.1;

	TemporalDifferenceAgent(Environment world, double stepSize, double epsilon) {
		this.world = world;
		this.stepSize = stepSize;
		this.epsilon = epsilon;
		q =  MLUtil.getRandomMatrix(world.getNumberOfStates(), getNumberOfActions());
		returns = MLUtil.getMatrixOfEmptyArrayLists(world.getNumberOfStates(), getNumberOfActions());
		policy = MLUtil.getMatrix(world.getNumberOfStates(), getNumberOfActions(), 1 / getNumberOfActions());
	}

	@Override
	public void act(int numOfEpisodes, int steps) {
		//Double[] valueFunction = prediction(numOfEpisodes, steps, policy);

		//System.out.println("Q:" + MLUtil.matrixToString(q));

		for (int i = 0; i < numOfEpisodes; i++) {
			Episode episode = Episode.generateEpisode(world, this,steps);
			System.out.println("Episode(" + i + "):" + episode);

			estimatePolicy(episode);

			//System.out.println("Policy:" + this.toString());
		}
		System.out.println("LastPolicy:" + this.toString());
	}

	private Double[] prediction(int episodeNum, int stepsInEpisode, Double[][] policy, double learningRate, double discount) {
		Double[] V = MLUtil.getRandomArray(world.getNumberOfStates());

		int worldState = world.reset();
		int action = 0;

		int state = 0;

		for (int i = 0; i < stepsInEpisode; i++) {
			int prevState = state;
			action = getAction(prevState, episodeNum * stepsInEpisode + i);
			Step step = world.step(action);
			state = step.observation;
			double R = step.reward;
			V[prevState] += learningRate *(R + discount * V[state] - V[prevState]);
		}

		return V;
	}

	private int getNumberOfStates() {
		return world.getNumberOfStates() * getNumberOfActions();
	}

	private void controlSARSA(int numOfEpsiodes, int stepsInEpizode, double learningRate, double discount) {
		Double[][] Q = MLUtil.getRandomMatrix(getNumberOfStates(), getNumberOfActions());

		for (int i = 0; i < numOfEpsiodes; i++) {
			int worldState = world.reset();

			int state = worldState;
			int action = getAction(state, i * stepsInEpizode);

			for (int j = 0; j < stepsInEpizode; j++) {
				Step step = world.step(action);
				double R = step.reward;
				int newState = step.observation + world.getNumberOfStates() * action;
				int aStart = MLUtil.argMax()
				policy[newState] = MLUtil.getEpsilonGreedyPolicy(policy[newState], )
				int newAction = getAction(newState, i * stepsInEpizode + j);

				Q[state][action] += learningRate * (R + discount * Q[newState][newAction] - Q[state][action]);

				state =newState; action = newAction;
			}
		}


	}

	private int getAction(int state, int timeStep) {
		return ActionUtil.timedEpsilonGreedy(policy[state], timeStep);
	}

	private void estimatePolicy(Episode episode) {
		double g = 0;

		for (int i = episode.steps.length - 1; i >= 0; i--) {
			EpisodeStep step = episode.steps[i];

			g += step.reward;

			if (!episode.stateActions.subList(0, i).contains(step.prevState + "," + step.action)) {
				returns[step.prevState][step.action].add(g);

				double averageReturns = MLUtil.getAverageReturnForAStateAction(returns[step.prevState][step.action]);

				q[step.prevState][step.action] = averageReturns;

				int aStar = MLUtil.argMax(q[step.prevState]);

				policy[step.prevState] = MLUtil.getEpsilonGreedyPolicy(policy[step.prevState], aStar, 0.3);
			}
		}

		System.out.println(this);
	}

	public String toString() {
		return
				"Q:" + MLUtil.matrixToString(q) + "\n" +
						"Returns:" + MLUtil.matrixListToString(returns) + "\n" +
						"AVGReturns:" + MLUtil.getAverageReturns(returns) + "\n" +
						"Policy:" + MLUtil.matrixToString(policy);
	}


}
