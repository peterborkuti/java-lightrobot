package hu.bp.ai.util;

import hu.bp.ai.interfaces.DoubleComparator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MLUtil {
	/**
	 * Returns the index of the maximal element in the list or 0 if the array is empty
	 * @param list
	 * @return
	 */
	public static int argMax(List<? extends Number> list) {
		return
				IntStream.range(0, list.size()).boxed().
						max(Comparator.comparingDouble(ix -> list.get(ix).doubleValue())).
						orElse(-1);
	}

	/**
	 * Returns the index of the maximal element in the array or 0 if the array is empty
	 * @param array
	 * @return
	 */
	public static int argMax(Number[] array) {
		return  argMax(Arrays.asList(array));
	}

	/**
	 * Creates a Double[][] filled with a given value
	 * @param rows
	 * @param cols
	 * @return
	 */
	public static Double[][] getMatrix(int rows, int cols, double value) {
		Double[][] q = new Double[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				q[i][j] = value;
			}
		}

		return q;
	}

	/**
	 * Creates a Double[][] filled with random numbers
	 * @param rows
	 * @param cols
	 * @return
	 */
	public static Double[][] getRandomMatrix(int rows, int cols) {
		Double[][] q = new Double[rows][cols];

		for (int i = 0; i < rows; i++) {
			q[i] = getRandomArray(cols);
		}

		return q;
	}

	public static Double[] getRandomArray(int length) {
		Random rnd = new Random();

		return rnd.doubles(length).boxed().toArray(Double[]::new);
	}

	/**
	 * Creates an improved policy based on an existing with the epsilon-greedy-method
	 *
	 * WARNING! Only one action will be the best.
	 * @param policyForTheGivenState the actual policy for the given state
	 * @param aStar the best action for the given state
	 * @param epsilon
	 * @return
	 */
	public static Double[] getEpsilonGreedyPolicy(Double[] policyForTheGivenState, int aStar, double epsilon) {
		double bad = epsilon / policyForTheGivenState.length;
		double good = 1 - epsilon + bad;

		return IntStream.range(0, policyForTheGivenState.length).
				mapToDouble( i -> (i == aStar) ? good : bad).boxed().toArray(Double[]::new);
	}

	public static Double getMax(Double[] arr) {
		return Arrays.stream(arr).max(Double::compare).orElse(0D);
	}


	/**
	 * Gets average from a list of doubles
	 * @param returns
	 * @return
	 */
	public static Double getAverageReturnForAStateAction(List<Double> returns) {
		double ret = returns.stream().collect(Collectors.averagingDouble(Double::new));

		return (returns.size() == 0) ? 0 : ret;
	}

	public static String getAverageReturnsForActions(List<Double>[] returns) {
		return
			Arrays.stream(returns).
				map(list -> getAverageReturnForAStateAction(list)).
				map(String::valueOf).
				collect(Collectors.joining(","));
	}


	public static String getAverageReturns(List<Double>[][] returns) {
		return
			Arrays.stream(returns).map(arr -> getAverageReturnsForActions(arr)).
				collect(Collectors.joining("\n"));
	}

	/**
	 * Creates a matrix of empty ArrayLists
	 *
	 * @param rows
	 * @param cols
	 * @param <T>
	 * @return
	 */
	public static<T> List<T>[][] getMatrixOfEmptyArrayLists(int rows, int cols) {
		List<T> returns[][] = new List[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				returns[i][j] = new ArrayList<T>();
			}
		}

		return returns;
	}

	public static boolean doubleEquals(double a, double b, double ...threshold) {
		DoubleComparator dc = new DoubleComparator() {};

		return dc.equals(a, b, threshold);
	}


	public static <T extends Number> String arrToString(T[] arr) {
		return "[" + Arrays.stream(arr).map(String::valueOf).collect(Collectors.joining(",")) + "]";
	}

	public static <T extends Number> String matrixToString(T[][] matrix) {
		return "[" + Arrays.stream(matrix).map(
					l -> Arrays.stream(l).map(String::valueOf).collect(Collectors.joining(","))
				).
				collect(Collectors.joining("\n")) + "]";
	}

	public static <T extends Number> Integer[] rowArgMax(T[][] matrix) {
		return IntStream.range(0, matrix.length).
					map(
						row -> argMax(matrix[row])
					).
					boxed().
					toArray(Integer[]::new);
	}

	public static<T extends Number> String listToString(List<T> list) {
		return "{" + list.stream().map(String::valueOf).collect(Collectors.joining(",")) + "}";
	}

	public static <T extends Number> String arrListToString(List<T> arr[]) {
		return Arrays.stream(arr).
						map(
							l -> listToString(l)
						).
						collect(Collectors.joining("\n"));
	}

	public static <T extends Number> String matrixListToString(List<T> matrix[][]) {
		return Arrays.stream(matrix).
				map(
					row -> arrListToString(row)
				).
				collect(Collectors.joining("\n"));
	}
}
