package hu.bp.lightrobot;

import java.util.Random;

public class BulbWorld {
	private boolean lightOn = false;
	private Random rand = new Random();

	public int reset() {
		lightOn = rand.nextBoolean();

		return getLightObservation();
	}

	private int getLightObservation() {
		return (lightOn) ? 1 : 0;
	}

	public Step step(int action) {
		Step step = new Step(getLightObservation(), LightReward.getRewardWhenLightIsSynchron(action, lightOn), false);
		lightOn = (Math.random() < 0.1) ? !lightOn : lightOn;

		return step;
	}

	public String toString() {
		return "Bulb:" + ((lightOn) ? "ON" : "off");
	}
}
