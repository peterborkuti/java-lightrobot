package hu.bp.lightrobot;

import hu.bp.ai.interfaces.Agent;
import hu.bp.ai.interfaces.Environment;
import hu.bp.ai.rl.Episode;
import hu.bp.ai.rl.EpisodeStep;
import hu.bp.ai.rl.Step;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EpisodeTest {
	@Mock
	Agent agent;

	@Mock
	Environment env;

	@Test
	public void testStateActionsField() {
		EpisodeStep es1 = new EpisodeStep(0, 1, 0, 2);
		EpisodeStep es2 = new EpisodeStep(3, 4, 0, 5);

		EpisodeStep[] steps = {es1, es2};

		Episode e = new Episode(steps);

		assertEquals(2, e.stateActions.size());


		assertEquals("0,1", e.stateActions.get(0));
		assertEquals("3,4", e.stateActions.get(1));
	}

	@Test
	public void testGenerateEpisode() {
		when(env.step(anyInt())).thenReturn(new Step());
		Episode e = Episode.generateEpisode(env, agent, 2);

		assertEquals(2, e.steps.length);
		verify(env).reset();
		verify(env, times(2)).step(0);
		verify(agent, times(2)).getAction(0);

	}

}