package hu.bp.lightrobot;

import hu.bp.ai.rl.EpisodeStep;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpisodeStepTest {
	@Test
	public void testCreateObject() {
		EpisodeStep es = new EpisodeStep(0, 1, 2, 3);

		assertEquals(0, es.prevState);
		assertEquals(1, es.action);
		assertEquals(2, es.reward);
		assertEquals(3, es.newState);
	}

}