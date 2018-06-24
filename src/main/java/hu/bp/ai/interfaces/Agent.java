package hu.bp.ai.interfaces;

public interface Agent {
	/**
	 * The number of actions. The valid actions are numbers from 0 to getNumberOfActions() -1
	 * @return
	 */
	public default int getNumberOfActions() {
		return 1;
	};

	/**
	 * The robot's action based on the state
	 * @param state
	 * @return
	 */
	public default int getAction(int state) {
		return 0;
	};

	/**
	 * Resets the robot
	 */
	public default void reset() {};

	/**
	 * Agent should generate numOfEpisodes episode with stepsInOneEpisode and learn by episodes
	 * @param numOfEpisodes
	 * @param stepsInOneEpisode
	 */
	public default void act(int numOfEpisodes, int stepsInOneEpisode) {};

	/**Returns with the int[] greedy policy which 1 for an action if it is
	 * the most valuable action for a given state.
	 *
	 * @return greedyActionForState(state)=int[state]
	 */
	public Integer[] getGreedyPolicy();
}
