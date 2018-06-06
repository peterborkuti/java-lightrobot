package hu.bp.lightrobot;

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
		Random rnd = new Random();
		Double[][] q = new Double[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				q[i][j] = rnd.nextDouble();
			}
		}

		return q;
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
		double _threshold = 0.01;
		if (threshold.length > 0) _threshold = threshold[0];
		return (Math.abs(a - b) < _threshold);
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

	public static <T extends Number> Integer[] rowArgMax(T[][] matrix) {
		return IntStream.range(0, matrix.length).
					map(
						row -> argMax(matrix[row])
					).
					boxed().
					toArray(Integer[]::new);
	}

	public static <T extends Number> String arrListToString(List<T> arr[]) {
		return Arrays.stream(arr).
						map(
							l -> l.stream().map(String::valueOf).collect(Collectors.joining(","))
						).
						collect(Collectors.joining("\n"));
	}
}
