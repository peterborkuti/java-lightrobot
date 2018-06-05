package hu.bp.lightrobot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Episode {
	public final EpisodeStep[] steps;
	public final List<Integer> states;


	public Episode(EpisodeStep[] steps) {
		this.steps = steps;

		this.states =
				IntStream.concat(
						Arrays.stream(steps).mapToInt(step -> step.prevState).limit(1),
						Arrays.stream(steps).mapToInt(step -> step.newState)
				).boxed().collect(Collectors.toList());
	}

	public String toString() {
		return Arrays.stream(steps).map(step -> step.toString()).collect(Collectors.joining(","));
	}
}

class EpisodeStep {
	public final int prevState;
	public final int action;
	public final double reward;
	public final int newState;

	public EpisodeStep(int prevState, int action, double reward, int newState) {
		this.prevState = prevState;
		this.action = action;
		this.reward = reward;
		this.newState = newState;
	}

	@Override
	public String toString() {
		return "{" + prevState + "," + action + ">" + newState + "," + reward + "}";
	}
}

public class Robot {
	private static final double DOUBLE_EQUALS_THRESHOLD = 0.00000001;
	private final BulbWorld world;
	private boolean LEDOn;
	private Random rnd = new Random();

	private int[] states = {0, 1, 2, 3};
	private int[] actions = {0, 1};
	private Double[][] policy = {{0.5, 0.5}, {0.5, 0.5}, {0.5, 0.5}, {0.5, 0.5}};
	private Double[][] q = initQ(states.length, actions.length);
	private List<Double> returns[][] = initReturns(states.length, actions.length);

	Robot(BulbWorld world) {
		this.world = world;
	}

	public void act(int numOfEpisodes, int steps) {
		System.out.println("Q:" + matrixToString(q));

		for (int i = 0; i < numOfEpisodes; i++) {
			System.out.println("Step:" + i);
			Episode episode = new Episode(generateEpisode(100));
			System.out.println("Episode:" + episode);

			estimatePolicy(episode);

			/*
			estimateValueFunctionAndUpdateRenturns(episode, value, returns);
			System.out.println("Value:" + arrToString(value));
			System.out.println("Returns:\n" + arrListToString(returns));
			*/
		}
	}

	private void estimatePolicy(Episode episode) {
		double g = 0;

		for (int i = episode.steps.length - 1; i >= 0; i--) {
			EpisodeStep step = episode.steps[i];

			g += step.reward;

			if (!episode.states.subList(0, i).contains(step.prevState)) {
				returns[step.prevState][step.action].add(g);
				q[step.prevState][step.action] = returns[step.prevState][step.action].stream().collect(Collectors.averagingDouble(Double::new));
				int aStar = argmax(q[step.prevState]);

				for (int a: actions) {
					if
				}
			}
		}
	}

	private Double[][] initQ(int rows, int cols) {
		Double[][] q = new Double[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				q[i][j] = rnd.nextDouble();
			}
		}

		return q;

		//return IntStream.range(0, rows).boxed().map(Double::valueOf).map(i->rnd.doubles(cols).toArray()).toArray(Double[][]::new);
	}

	private List<Double>[][] initReturns(int rows, int cols) {
		List<Double> returns[][] = new List[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				returns[i][j] = new ArrayList<Double>();
			}
		}

		return returns;
	}

	private void estimateValueFunctionAndUpdateRenturns(Episode episode, Double[][] value, List<Double>[][] returns) {
		double g = 0;

		for (int i = episode.steps.length - 1; i >= 0; i--) {
			EpisodeStep step = episode.steps[i];

			g += step.reward;

			if (!episode.states.subList(0, i).contains(step.prevState)) {
				returns[step.prevState][step.action].add(g);
				value[step.prevState][step.action] = returns[step.prevState][step.action].stream().collect(Collectors.averagingDouble(Double::new));
			}
		}
	}

	public EpisodeStep[] generateEpisode(int steps) {
		EpisodeStep[] episodeSteps = new EpisodeStep[steps];
		int worldState = world.reset();
		int action = 0;

		int state = getState(worldState, action);

		for (int i = 0; i < steps; i++) {
			int prevState = state;
			action = getGreedyAction(prevState);
			Step step = world.step(action);
			state = getState(step.observation, action);
			episodeSteps[i] = new EpisodeStep(prevState, action, step.reward, state);
		}

		return episodeSteps;
	}

	public int getState(int worldState, int robotState) {
		return worldState + 2 * robotState;
	}

	private static boolean doubleEquals(double a, double b) {
		return (Math.abs(a - b) < DOUBLE_EQUALS_THRESHOLD);
	}

	private int getGreedyAction(int state) {
		if (policy[state][0] > policy[state][1]) {
			return 0;
		}

		if (doubleEquals(policy[state][0], policy[state][1])) {
			return rnd.nextBoolean() ? 1 : 0;
		}

		return 1;
	}

	public String toString() {
		return "[" +
				Arrays.stream(policy).map(Robot::arrToString).collect(Collectors.joining(";")) +
				"]";

	}

	public static <T extends Number> String arrToString(T[] arr) {
		return Arrays.stream(arr).map(String::valueOf).collect(Collectors.joining(","));
	}

	public static <T extends Number> String matrixToString(T[][] matrix) {
		return Arrays.stream(matrix).map(
				l -> Arrays.stream(l).map(String::valueOf).collect(Collectors.joining(","))
		).
				collect(Collectors.joining("\n"));
	}


	public static <T extends Number> String arrListToString(List<T> arr[]) {
		return Arrays.stream(arr).map(
					l -> l.stream().map(String::valueOf).collect(Collectors.joining(","))
				).
				collect(Collectors.joining("\n"));
	}
}
