package hu.bp.ai.agents;

import hu.bp.ai.interfaces.AbstractAgent;
import hu.bp.ai.interfaces.Environment;
import hu.bp.ai.rl.Episode;
import hu.bp.ai.rl.EpisodeStep;
import hu.bp.ai.rl.Step;
import hu.bp.ai.util.ActionUtil;
import hu.bp.ai.util.MLUtil;

public abstract class TemporalDifferenceAgent extends AbstractAgent {
	public TemporalDifferenceAgent(Environment world, double stepSize, double epsilon) {
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

	/**
	 * Sarsa (on-policy TD control) for estimating Q ≈ q ∗
	 *
	 * Algorithm parameters: step size α ∈ (0, 1], small ε > 0
	 * Initialize Q(s, a), for all s ∈ S + , a ∈ A(s), arbitrarily except that Q(terminal , ·) = 0
	 * Loop for each episode:
	 *   Initialize S
	 *   Choose A from S using policy derived from Q (e.g., ε-greedy)
	 *   Loop for each step of episode:
	 *      Take action A, observe R, S 0
	 *      Choose A 0 from S 0 using
	 *       policy derived from Q (e.g.,  ε-greedy)
	 *      Q(S, A) ← Q(S, A) + α R + γQ(S 0 , A 0 ) − Q(S, A)
	 *      S ← S 0 ; A ← A 0 ;
	 *  until S is terminal

	 * @param numOfEpsiodes
	 * @param stepsInEpizode
	 * @param learningRate
	 * @param discount
	 */
	public Double[][] controlSARSA(int numOfEpsiodes, int stepsInEpizode, double learningRate, double discount) {
		Double[][] Q = MLUtil.getMatrix(world.getNumberOfStates(), getNumberOfActions(), 0);

		int state = world.reset();

		int action = getAction(state, 0, Q[state]);

		for (int i = 0; i < stepsInEpizode * numOfEpsiodes; i++) {
			Step step = world.step(action);

			double R = step.reward;
			int newState = step.observation;

			System.out.println("(" +state+","+action+") -> "+R);

			int newAction = getAction(newState, i, Q[newState]);
			System.out.println("Policy:" + MLUtil.arrToString(getGreedyPolicy()));

			System.out.print("Q(" + state + "," + action + "):" + Q[state][action] + "->");
			Q[state][action] += learningRate * (R + discount * Q[newState][newAction] - Q[state][action]);
			System.out.println(Q[state][action]);

			state = newState; action = newAction;
		}

		return Q;
	}

	/**
	 * Q-learning (off-policy TD control) for estimating π ≈ π ∗
	 * Algorithm parameters: step size α ∈ (0, 1], small ε > 0
	 * Initialize Q(s, a), for all s ∈ S + , a ∈ A(s), arbitrarily except that Q(terminal , ·) = 0
	 * Loop for each episode:
	 *   Initialize S
	 *   Loop for each step of episode:
	 *     Choose A from S using policy derived from Q (e.g., ε-greedy)
	 *     Take action A, observe  R, S 0

	 *     Q(S, A) ← Q(S, A) + α R + γ max a Q(S 0 , a) − Q(S, A)
	 *     0
	 *     S ← S
	 *  until S is terminal
	 *
	 * @param numOfEpsiodes
	 * @param stepsInEpizode
	 * @param learningRate
	 * @param discount
	 */
	public Double[][] controlQLearning(int numOfEpsiodes, int stepsInEpizode, double learningRate, double discount) {
		Double[][] Q = MLUtil.getMatrix(world.getNumberOfStates(), getNumberOfActions(), 0);

		int state = world.reset();

		for (int i = 0; i < stepsInEpizode * numOfEpsiodes; i++) {
			int action = getAction(state, i, Q[state]);

			Step step = world.step(action);

			double R = step.reward;
			int newState = step.observation;

			System.out.println("(" +state+","+action+") -> "+R);

			System.out.println(i + ".step. Policy:" + MLUtil.arrToString(getGreedyPolicy()));

			System.out.print("Q(" + state + "," + action + "):" + Q[state][action] + "->");
			Q[state][action] += learningRate * (R + discount * MLUtil.getMax(Q[newState]) - Q[state][action]);
			System.out.println(Q[state][action]);

			state = newState;
		}

		return Q;
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
