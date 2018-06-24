package hu.bp.ai.rl;

import hu.bp.ai.interfaces.Agent;
import hu.bp.ai.interfaces.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Episode {
	public final EpisodeStep[] steps;
	public final List<String> stateActions;
	public final double reward;


	public Episode(EpisodeStep[] steps) {
		this.steps = steps;

		this.stateActions =
				Arrays.stream(steps).map(step -> step.prevState + "," + step.action).
						collect(Collectors.toList());

		this.reward = Arrays.stream(steps).map(step->step.reward).collect(Collectors.summingDouble(Double::valueOf));
	}

	public static Episode generateEpisode(Environment world, Agent agent, int steps) {
		EpisodeStep[] episodeSteps = new EpisodeStep[steps];
		int worldState = world.reset();
		int action = 0;

		int state = 0;

		for (int i = 0; i < steps; i++) {
			int prevState = state;
			action = agent.getAction(prevState);
			Step step = world.step(action);
			state = step.observation;
			episodeSteps[i] = new EpisodeStep(prevState, action, step.reward, state);
		}

		return new Episode(episodeSteps);
	}

	public String toString() {

		return "[Reward:" + this.reward + "]" + Arrays.stream(steps).map(step -> step.toString()).collect(Collectors.joining(","));
	}
}
