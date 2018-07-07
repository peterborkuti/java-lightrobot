package hu.bp.lightrobot;

import hu.bp.ai.agents.TemporalDifferenceAgent;
import hu.bp.ai.interfaces.Environment;
import hu.bp.ai.util.ActionUtil;
import hu.bp.ai.util.MLUtil;

import java.util.stream.IntStream;

public class LightRobotTD extends TemporalDifferenceAgent {
	LightRobotTD(Environment world) {
		super(world, 0.1,0.1);
	}

	@Override
	public int getNumberOfActions() {
		return 2;
	}

	@Override
	public Integer[] getGreedyPolicy() {
		return
		IntStream.range(0, world.getNumberOfStates()).map(
				state-> MLUtil.argMax(policy[state])
		).boxed().toArray(Integer[]::new);
	}

}
