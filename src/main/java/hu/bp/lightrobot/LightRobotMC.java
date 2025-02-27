package hu.bp.lightrobot;

import hu.bp.ai.agents.MonteCarloAgent;
import hu.bp.ai.interfaces.Environment;
import hu.bp.ai.util.ActionUtil;
import hu.bp.ai.util.MLUtil;

import java.util.stream.IntStream;

public class LightRobotMC extends MonteCarloAgent {
	LightRobotMC(Environment world) {
		super(world);
	}

	@Override
	public int getNumberOfActions() {
		return 2;
	}

	public int getAction(int state, int timeStep) {
		return ActionUtil.timedEpsilonGreedy(policy[state], timeStep);
	}


	@Override
	public Integer[] getGreedyPolicy() {
		return
		IntStream.range(0, world.getNumberOfStates()).map(
				state-> MLUtil.argMax(policy[state])
		).boxed().toArray(Integer[]::new);
	}

}
