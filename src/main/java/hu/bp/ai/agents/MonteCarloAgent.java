package hu.bp.ai.agents;

/**
 * Monte Carlo ES (Exploring Starts), for estimating π ≈ π ∗
 *
 * Initialize:
 *    π(s) ∈ A(s) (arbitrarily), for all s ∈ S
 *    Q(s, a) ∈ R (arbitrarily), for all s ∈ S, a ∈ A(s)
 *    Returns(s, a) ← empty list, for all s ∈ S, a ∈ A(s)
 *
 * Loop forever (for each episode):
 *    Choose S[0] ∈ S and A[0] ∈ A(S 0 ) such that all pairs have probability > 0 (exploring starts)
 *    Generate an episode starting from S 0 , A 0 , following π: S 0 , A 0 , R 1 , . . . , S T −1 , A T −1 , R T
 *
 *    G ← 0
 *
 *    Loop for each step of episode, t = T −1, T −2, . . . , 0:
 *      G ← G + R t+1
 *      Unless the pair S t , A t appears in S 0 , A 0 , S 1 , A 1 . . . , S t−1 , A t−1 :
 *        Append G to Returns(S t , A t )
 *        Q(S t ) ← average(Returns(S t , A t ))
 *        π(S t ) ← argmax a Q(S t , a)
 */

import hu.bp.ai.interfaces.Agent;
import hu.bp.ai.interfaces.Environment;
import hu.bp.ai.rl.Episode;
import hu.bp.ai.rl.EpisodeStep;
import hu.bp.ai.util.MLUtil;

import java.util.List;
import java.util.stream.IntStream;

public abstract class MonteCarloAgent implements Agent {
	protected final Environment world;

	protected Double[][] policy;
	protected Double[][] q;
	protected List<Double> returns[][];

	public MonteCarloAgent (Environment world) {
		this.world = world;
		q =  MLUtil.getRandomMatrix(world.getNumberOfStates(), getNumberOfActions());
		returns = MLUtil.getMatrixOfEmptyArrayLists(world.getNumberOfStates(), getNumberOfActions());
		policy = MLUtil.getMatrix(world.getNumberOfStates(), getNumberOfActions(), 1 / getNumberOfActions());
	}

	@Override
	public void act(int numOfEpisodes, int steps) {
		IntStream.range(0, numOfEpisodes).forEach(
			i -> estimatePolicy(Episode.generateEpisode(world, this, steps))
		);
	}

	private void estimatePolicy(Episode episode) {
		double sumOfRewards = 0;

		for (int i = episode.steps.length - 1; i >= 0; i--) {
			EpisodeStep step = episode.steps[i];

			sumOfRewards += step.reward;

			int state = step.prevState;
			int action = step.action;

			List<Double> _returns = returns[state][action];

			if (!episode.stateActions.subList(0, i).contains(state + "," + action)) {
				_returns.add(sumOfRewards);

				q[state][action] = MLUtil.getAverageReturnForAStateAction(_returns);;

				int aStar = MLUtil.argMax(q[state]);

				policy[state] = MLUtil.getEpsilonGreedyPolicy(q[state], aStar, 0.3);
			}
		}
	}

	public String toString() {
		return
				"Q:\n" + MLUtil.matrixToString(q) + "\n" +
				"Returns:\n" + MLUtil.matrixListToString(returns) + "\n" +
				"AVGReturns:\n" + MLUtil.getAverageReturns(returns) + "\n" +
				"Policy:\n" + MLUtil.matrixToString(policy);
	}
}
