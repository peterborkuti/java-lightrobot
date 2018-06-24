package hu.bp.ai.interfaces;

public interface DoubleComparator {
	default boolean equals(double a, double b, double ...threshold) {
		double _threshold = 0.0000001;
		if (threshold.length > 0) _threshold = threshold[0];
		return (Math.abs(a - b) < _threshold);
	}
}
