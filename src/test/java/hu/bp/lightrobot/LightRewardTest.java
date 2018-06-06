package hu.bp.lightrobot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LightRewardTest {

	@Test
	void getRewardWhenLightIsSynchron() {
		assertEquals(1, LightReward.getRewardWhenLightIsSynchron(0, false));
		assertEquals(1, LightReward.getRewardWhenLightIsSynchron(1, true));
		assertEquals(-1, LightReward.getRewardWhenLightIsSynchron(0, true));
		assertEquals(-1, LightReward.getRewardWhenLightIsSynchron(1, false));
	}

	@Test
	void getRewardWhenLightIsAsynchron() {
		assertEquals(-1, LightReward.getRewardWhenLightIsAsynchron(0, false));
		assertEquals(-1, LightReward.getRewardWhenLightIsAsynchron(1, true));
		assertEquals(1, LightReward.getRewardWhenLightIsAsynchron(0, true));
		assertEquals(1, LightReward.getRewardWhenLightIsAsynchron(1, false));
	}
}