package hu.bp.linefollowerrobot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

	@Test
	void getArcLength() {
		Car car = new Car();
		assertEquals(0, car.getArcLength(0, 1));
		assertEquals(Math.PI, car.getArcLength(1, 1));

		double wheelDiameter = 5;
		double circumference = wheelDiameter * Math.PI;
		car = new Car(0, wheelDiameter);
		assertEquals(circumference, car.getArcLength(1, 1));
		assertEquals(3 * circumference, car.getArcLength(3, 1));
		assertEquals(4 * circumference, car.getArcLength(1, 4));
		assertEquals(12 * circumference, car.getArcLength(4, 3));
	}

	@Test
	void moveStraight() {
		double wheelDiameter = 5;
		double circumference = wheelDiameter * Math.PI;
		Car car = new Car(0, wheelDiameter);

		CarStateChange noMove = new CarStateChange(0, 0, 0);
		CarStateChange move12 = new CarStateChange(0, 12 * circumference, 0);
		CarStateChange moveMinus12 = new CarStateChange(0, -12 * circumference, 0);

		assertEquals(noMove, car.moveStraight(0, 0));
		assertEquals(noMove, car.moveStraight(0, 4));
		assertEquals(noMove, car.moveStraight(4, 0));

		assertEquals(move12, car.moveStraight(1, 12));
		assertEquals(move12, car.moveStraight(12, 1));
		assertEquals(move12, car.moveStraight(3, 4));

		assertEquals(moveMinus12, car.moveStraight(-1, 12));
		assertEquals(moveMinus12, car.moveStraight(-12, 1));
		assertEquals(moveMinus12, car.moveStraight(-3, 4));
	}

	@Test
	void rollInPlace() {
/*
		double wheelDiameter = 5;
		double circumference = wheelDiameter * Math.PI;
		Car car = new Car(0, wheelDiameter);
*/
		Car car = new Car();

		CarStateChange noMove = new CarStateChange(0, 0, 0);
		CarStateChange move90degree = new CarStateChange(0, 0, Math.PI / 2);
		CarStateChange moveMinus90Degree = new CarStateChange(0, 0, - Math.PI / 2);

		assertEquals(noMove, car.rollInPlace(0, 0, 0));
		assertEquals(noMove, car.rollInPlace(0, 0, 1));
		assertEquals(noMove, car.rollInPlace(4, 4, 0));

		assertEquals(moveMinus90Degree, car.rollInPlace(-1.0 / 4.0, 1.0 / 4.0, 1));
		assertEquals(move90degree, car.rollInPlace(1.0 / 4.0, -1.0 / 4.0, 1));
	}

	@Test
	void moveOnCircle() {

		double wheelDiameter = 1;
		double circumference = wheelDiameter * Math.PI;
		Car car = new Car(1, wheelDiameter);

		CarStateChange noMove = new CarStateChange(0, 0, 0);
		CarStateChange move90degree = new CarStateChange(-1.0, 1.0, Math.PI / 2, 1, 2);
		//CarStateChange move90degree = new CarStateChange(-1, 1, Math.PI / 2);

		CarStateChange moveMinus90Degree = new CarStateChange(0, 0, - Math.PI / 2);

		assertEquals(noMove, car.moveOnCircle(0, 0, 0));
		assertEquals(noMove, car.moveOnCircle(0, 4, 0));
		assertEquals(noMove, car.moveOnCircle(0, 0, 1));

		assertEquals(move90degree, car.moveOnCircle(1.0, 3.0, 1.0 / 4.0));
		//assertEquals(moveMinus90Degree, car.rollInPlace(-1.0 / 4.0, 1));
	}
}