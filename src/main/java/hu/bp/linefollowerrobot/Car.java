package hu.bp.linefollowerrobot;

import hu.bp.lightrobot.MLUtil;

public class Car {
	private double trackWidth;
	private double wheelDiameter;

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
			return rollInPlace(leftMotorRPM, deltaTimeInMinutes);
		}

		return moveOnCircle(leftMotorRPM, rightMotorRPM, deltaTimeInMinutes);
	}

	public CarStateChange moveOnCircle(double leftMotorRPM, double rightMotorRPM, double deltaTimeInMinutes) {
		if ((leftMotorRPM == 0 && rightMotorRPM == 0) || deltaTimeInMinutes == 0) {
			return new CarStateChange(0, 0, 0);
		}

		double leftWheelArcLength = getArcLength(leftMotorRPM, deltaTimeInMinutes);
		double rightWheelArcLength = getArcLength(rightMotorRPM, deltaTimeInMinutes);

		double alfa = leftMotorRPM == 0 ? 0: rightMotorRPM / leftMotorRPM;
		double r = trackWidth * rightWheelArcLength / (leftWheelArcLength - rightWheelArcLength);
		double R = r + trackWidth / 2.0;

		double dx = R - R * Math.cos(alfa);
		double dy = R * Math.sin(alfa);

		return new CarStateChange(dx, dy, alfa);
	}

	public static double getArc(double motorRPM, double deltaTimeInMinutes) {
		return motorRPM * deltaTimeInMinutes * 2.0 * Math.PI;
	}

	public double getArcLength(double motorRPM, double deltaTimeInMinutes, double wheelDiameter) {
		return getArc(motorRPM, deltaTimeInMinutes) * wheelDiameter / 2.0;
	}

	public double getArcLength(double motorRPM, double deltaTimeInMinutes) {
		return getArcLength(motorRPM, deltaTimeInMinutes, wheelDiameter);
	}

	public CarStateChange moveStraight(double motorRPM, double deltaTimeInMinutes) {
		return new CarStateChange(0,getArcLength(motorRPM, deltaTimeInMinutes), 0);
	}

	public CarStateChange rollInPlace(double motorRPM, double deltaTimeInMinutes) {
		return new CarStateChange(0, 0, getArc(motorRPM, deltaTimeInMinutes));
	}
}
