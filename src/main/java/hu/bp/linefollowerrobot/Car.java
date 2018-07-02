package hu.bp.linefollowerrobot;

import hu.bp.ai.util.MLUtil;

/**
 * Simulates the movement of a two-wheeled car.
 *
 * The car has two wheels with given wheel diameter and the with of its track
 * (the distance between the two wheels)
 *
 * Movements are given by the RPM (rotate-per-minute) of the left and right wheel
 * and the time to move.
 */
public class Car {
	public final double trackWidth;
	public final double wheelDiameter;

	public Car() {
		this(1, 1);
	}

	public Car(double trackWidth, double wheelDiameter) {
		this.trackWidth = trackWidth;
		this.wheelDiameter = wheelDiameter;
	}

	public CarStateChange move(double leftMotorRPM, double rightMotorRPM) {
		return move(leftMotorRPM, rightMotorRPM, 1);
	}

	public CarStateChange move(double leftMotorRPM, double rightMotorRPM, double deltaTimeInMinutes) {
		if (MLUtil.doubleEquals(leftMotorRPM, rightMotorRPM, 0.0001)) {
			return moveStraight(leftMotorRPM, deltaTimeInMinutes);
		}

		if (MLUtil.doubleEquals(leftMotorRPM, -rightMotorRPM)) {
			return rollInPlace(leftMotorRPM, rightMotorRPM, deltaTimeInMinutes);
		}

		return moveOnCircle(leftMotorRPM, rightMotorRPM, deltaTimeInMinutes);
	}

	public CarStateChange moveOnCircle(double leftMotorRPM, double rightMotorRPM, double deltaTimeInMinutes) {
		if ((leftMotorRPM == 0 && rightMotorRPM == 0) || deltaTimeInMinutes == 0) {
			return new CarStateChange();
		}

		double vr = rightMotorRPM * Math.PI * wheelDiameter;
		double vl = leftMotorRPM * Math.PI * wheelDiameter;

		double omega = (vr - vl) / trackWidth;
		double R = (trackWidth / 2.0) * (vl + vr) / (vr - vl);

		double dalfa = omega * deltaTimeInMinutes;
		double dx = R * Math.sin(dalfa);
		double dy = R * (1 - Math.cos(dalfa));

		return new CarStateChange(dx, dy, dalfa, leftMotorRPM, rightMotorRPM);
	}

	public static double getArc(double motorRPM, double deltaTimeInMinutes) {
		return motorRPM * deltaTimeInMinutes * 2.0 * Math.PI;
	}

	public double getArcLength(double motorRPM, double deltaTimeInMinutes, double wheelDiameter) {
		return getArc(Math.abs(motorRPM), deltaTimeInMinutes) * wheelDiameter / 2.0;
	}

	public double getArcLength(double motorRPM, double deltaTimeInMinutes) {
		return getArcLength(motorRPM, deltaTimeInMinutes, wheelDiameter);
	}

	public CarStateChange moveStraight(double motorRPM, double deltaTimeInMinutes) {
		return new CarStateChange(0,getArcLength(motorRPM, deltaTimeInMinutes), 0, motorRPM, motorRPM);
	}

	public CarStateChange rollInPlace(double leftMotorRPM, double rightMotorRPM, double deltaTimeInMinutes) {
		return new CarStateChange(0, 0, getArc(leftMotorRPM, deltaTimeInMinutes), leftMotorRPM, rightMotorRPM);
	}
}
