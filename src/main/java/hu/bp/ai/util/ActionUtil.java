package hu.bp.ai.util;

import hu.bp.ai.util.MLUtil;

import java.util.Random;

public class ActionUtil {
	public static final Random rnd = new Random();

	/**
	 * Returns a random int between 0 and numOfActions - 1
	 * @param numOfActions
	 * @return
	 */
	public static int random(int numOfActions) {
		return rnd.nextInt(numOfActions);
	}

	/**
	 * Returns with the index of the greatest value in the given array
	 * @param policyForTheGivenState
	 * @return
	 */
	public static int greedy(Double[] policyForTheGivenState) {
		return MLUtil.argMax(policyForTheGivenState);
	}

	/**
	 * Returns the random action with epsilon probability and with
	 * the greedy action with 1 - epsilon probability
	 * @param policyForTheGivenState
	 * @param epsilon
	 * @return
	 */
	public static int epsilonGreedy(Double[] policyForTheGivenState, double epsilon) {
		return
				(rnd.nextDouble() < epsilon) ?
						random(policyForTheGivenState.length) :
						greedy(policyForTheGivenState);
	}

	/**
	 * Returns with epsilonGreedy action, where epsilon = 1 / timeStep.
	 *
	 * This chooses random actions at the beginning than rather chooses greedy action
	 * as time goes by.
	 *
	 * @param policyForTheGivenState
	 * @param timeStep
	 * @return
	 */
	public static int timedEpsilonGreedy(Double[] policyForTheGivenState, int timeStep) {
		double epsilon = (timeStep == 0) ? 1 : 1.0 / timeStep;

		return epsilonGreedy(policyForTheGivenState, epsilon);
	}
}
