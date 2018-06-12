package hu.bp.lightrobot;

import java.util.List;

public abstract class TemporalDifferenceAgent extends AbstractAgent {
	TemporalDifferenceAgent(Environment world, double stepSize, double epsilon) {
		super(world, stepSize, epsilon);
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

	public Double[] prediction(int episodeNum, int stepsInEpisode, Double[][] policy, double learningRate, double discount) {
		Double[] V = MLUtil.getRandomArray(world.getNumberOfStates());

		int worldState = world.reset();
		int action = 0;

		int state = 0;

		for (int i = 0; i < stepsInEpisode; i++) {
			int prevState = state;
			//action = getAction(prevState, episodeNum * stepsInEpisode + i);
			Step step = world.step(action);
			state = step.observation;
			double R = step.reward;
			V[prevState] += learningRate *(R + discount * V[state] - V[prevState]);
		}

		return V;
	}

	public void controlSARSA(int numOfEpsiodes, int stepsInEpizode, double learningRate, double discount) {
		Double[][] Q = MLUtil.getRandomMatrix(world.getNumberOfStates(), getNumberOfActions());

		int state = world.reset();

		int action = getAction(state, 0, Q[state]);

		for (int i = 0; i < stepsInEpizode * numOfEpsiodes; i++) {
			Step step = world.step(action);

			double R = step.reward;
			int newState = step.observation;

			System.out.println("(" +state+","+action+") -> "+R);

			int newAction = getAction(newState, i, Q[newState]);

			Q[state][action] += learningRate * (R + discount * Q[newState][newAction] - Q[state][action]);

			state = newState; action = newAction;
		}
	}

	private int getAction(int state, int timeStep, Double[] qForState) {
		int aStar = MLUtil.argMax(qForState);
		policy[state] = MLUtil.getEpsilonGreedyPolicy(policy[state], aStar, 0.3);
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

				//q[step.prevState][step.action] = averageReturns;

				//int aStar = MLUtil.argMax(q[step.prevState]);

				//policy[step.prevState] = MLUtil.getEpsilonGreedyPolicy(policy[step.prevState], aStar, 0.3);
			}
		}

		System.out.println(this);
	}

}
