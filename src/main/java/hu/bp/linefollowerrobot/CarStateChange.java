package hu.bp.linefollowerrobot;

import java.util.HashMap;
import java.util.Objects;

public class CarStateChange {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof CarStateChange)) return false;

		CarStateChange that = (CarStateChange) o;

		return Double.compare(that.x, x) == 0 &&
				Double.compare(that.y, y) == 0 &&
				Double.compare(that.angle, angle) == 0;
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
