package hu.bp.linefollowerrobot;

import hu.bp.ai.interfaces.DoubleComparator;
import hu.bp.ai.util.MLUtil;

import java.util.Objects;

public class CarStateChange implements DoubleComparator {
	public final double x;
	public final double y;
	public final double angle;

	public CarStateChange() {
		this(0, 0, 0);
	}

	public CarStateChange(double x, double y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = angle % (Math.PI * 2);
	}

	public CarStateChange(double x, double y, double angle, double left, double right) {
		if (equals(x, 0)) x = 0.0;
		if (equals(y, 0)) y = 0.0;
		if (equals(angle, 0)) angle = 0.0;

		this.x = getXSign(left, right) * Math.abs(x);
		this.y = getYSign(left, right)* Math.abs(y);
		this.angle = getAngleSign(left, right) * Math.abs(angle) % (Math.PI * 2);
	}

	public static int getAngleSign(double left, double right) {
		return (left > 0 && right > 0 && left > right) || (left < 0 && right < 0 && right < left ) || (left > 0 && right < 0 && MLUtil.doubleEquals(left, Math.abs(right))) ? 1 : -1;
	}

	public static int getXSign(double left, double right) {
		return (left > 0 && right > 0 && left > right) || (left < 0 && right < 0 && left < right)? 1 : -1;
	}

	public static int getYSign(double left, double right) {
		return (left > 0 && right > 0) ? 1 : -1;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof CarStateChange)) return false;

		CarStateChange that = (CarStateChange) o;

		return equals(that.x, x) &&
				equals(that.y, y) &&
				equals(that.angle, angle);
	}

	@Override
	public int hashCode() {

		return Objects.hash(x, y, angle);
	}

	@Override
	public String toString() {
		return "CarStateChange{" +
				"x=" + x +
				", y=" + y +
				", angle=" + angle +
				'}';
	}
}
