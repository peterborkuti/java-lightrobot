package hu.bp.ai.interfaces;

import hu.bp.ai.rl.Step;

public interface Environment {
	/**
	 * Resets the environment and returns the observer (initial) state after the reset
	 * @return
	 */
	public default int reset() {
		return 0;
	};

	/**
	 * Change the environment according to the input action
	 * @param action
	 * @return with the Step object after the action
	 */
	public default Step step(int action) {
		return new Step(0, 0, false);
	};

	/**
	 * Returns with the number of states of the environment.
	 * The different states are from 0 to getNumberOfStates() - 1
	 * @return
	 */
	public default int getNumberOfStates() {
		return 1;
	};
}
