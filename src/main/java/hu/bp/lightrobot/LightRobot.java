package hu.bp.lightrobot;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LightRobot implements Agent {
	private final Environment world;
	private boolean LEDOn;
	private Random rnd = new Random();

	private Double[][] policy;
	private Double[][] q;
	private List<Double> returns[][];
	private double epsilon = 0.4;

	LightRobot(Environment world) {
		this.world = world;
		q =  MLUtil.getRandomMatrix(world.getNumberOfStates(), getNumberOfActions());
		returns = MLUtil.getMatrixOfEmptyArrayLists(world.getNumberOfStates(), getNumberOfActions());
		policy = MLUtil.getMatrix(world.getNumberOfStates(), getNumberOfActions(), 1 / getNumberOfActions());
	}

	@Override
	public void reset() {
		LEDOn = false;
	}

	@Override
	public int getNumberOfActions() {
		return 2;
	}

	@Override
	public void act(int numOfEpisodes, int steps) {
		//System.out.println("Q:" + MLUtil.matrixToString(q));

		for (int i = 0; i < numOfEpisodes; i++) {
			Episode episode = Episode.generateEpisode(world, this,steps);
			System.out.println("Episode(" + i + "):" + episode);

			estimatePolicy(episode);

			//System.out.println("Policy:" + this.toString());
		}
		System.out.println("LastPolicy:" + this.toString());
	}

	private void estimatePolicy(Episode episode) {
		double g = 0;

		for (int i = episode.steps.length - 1; i >= 0; i--) {
			EpisodeStep step = episode.steps[i];

			g += step.reward;

			if (!episode.stateActions.subList(0, i).contains(step.prevState + "," + step.action)) {
				//System.out.println("Update - g:" + g);
				returns[step.prevState][step.action].add(g);

				double averageReturns = returns[step.prevState][step.action].stream().collect(Collectors.averagingDouble(Double::new));
				q[step.prevState][step.action] = averageReturns;

				int aStar = MLUtil.argMax(q[step.prevState]);

				modifyPolicy(step.prevState, aStar);
			}
		}
	}


	private void modifyPolicy(int state, int aStar) {
		double bad = epsilon / getNumberOfActions();
		double good = 1 - epsilon + bad;

		IntStream.range(0, getNumberOfActions()).forEach(
				a -> policy[state][a] = (a == aStar) ? good : bad
		);
	}

	public int getAction(int state) {
		return getEpsilonGreedyAction(state);
	}

	private int getGreedyAction(int state) {
		if (policy[state][0] > policy[state][1]) {
			return 0;
		}

		if (MLUtil.doubleEquals(policy[state][0], policy[state][1])) {
			return rnd.nextBoolean() ? 1 : 0;
		}

		return 1;
	}

	private int getEpsilonGreedyAction(int state) {
		if (rnd.nextDouble() < 0.05) {
			return rnd.nextInt(getNumberOfActions());
		}
		else {
			return getGreedyAction(state);
		}

	}

	public String toString() {
		return "[" +
				Arrays.stream(policy).map(MLUtil::arrToString).collect(Collectors.joining(";")) +
				"]";

	}

	@Override
	public Integer[] getGreedyPolicy() {
		return
		IntStream.range(0, world.getNumberOfStates()).map(
				state->MLUtil.argMax(policy[state])
		).boxed().toArray(Integer[]::new);
	}

}
