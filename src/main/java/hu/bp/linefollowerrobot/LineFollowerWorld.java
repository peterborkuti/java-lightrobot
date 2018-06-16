package hu.bp.linefollowerrobot;

import hu.bp.lightrobot.Environment;
import hu.bp.lightrobot.LightReward;
import hu.bp.lightrobot.Step;

import java.util.Random;

public class LineFollowerWorld implements Environment {

	private boolean lightOn = false;
	private Random rand = new Random();
	private boolean synchron = false;

	public LineFollowerWorld(boolean synchron) {
		this.synchron = synchron;
	}



	@Override
	public int reset() {
		lightOn = rand.nextBoolean();

		return getLightObservation();
	}

	private int getLightObservation() {
		return (lightOn) ? 1 : 0;
	}

	private int getObservation(int robotAction) {
		return getLightObservation() + 2 * robotAction;
	}

	@Override
	public Step step(int action) {
		Step step = new Step(
				getObservation(action),
				(synchron) ?
						LightReward.getRewardWhenLightIsSynchron(action, lightOn) :
						LightReward.getRewardWhenLightIsAsynchron(action, lightOn),
				false);
		lightOn = (Math.random() < 0.01) ? !lightOn : lightOn;

		return step;
	}

	/**
	 * Returns with the number of states of the environment.
	 * The different states are from 0 to getNumberOfStates() - 1
	 *
	 * @return
	 */
	@Override
	public int getNumberOfStates() {
		return 4;
	}

	public String toString() {
		return "Bulb:" + ((lightOn) ? "ON" : "off");
	}
}
