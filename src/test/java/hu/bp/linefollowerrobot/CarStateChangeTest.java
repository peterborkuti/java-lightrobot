package hu.bp.linefollowerrobot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarStateChangeTest {

	@Test
	void equals() {
		assertEquals(new CarStateChange(0, 0, 0), new CarStateChange(0, 0, 2 * Math.PI));
		assertEquals(new CarStateChange(0, 0, 0), new CarStateChange(0, 0, 4 * Math.PI));
		assertEquals(new CarStateChange(0, 0, Math.PI), new CarStateChange(0, 0, 3 * Math.PI));
	}

	@Test
	void getAngleSign() {
		assertEquals(-1, CarStateChange.getAngleSign(1,2));
		assertEquals(1, CarStateChange.getAngleSign(2,1));
		assertEquals(1, CarStateChange.getAngleSign(-1,-2));
		assertEquals(-1, CarStateChange.getAngleSign(-2,-1));
	}

	@Test
	void getXSign() {
		assertEquals(-1, CarStateChange.getXSign(1,2));
		assertEquals(1, CarStateChange.getXSign(2,1));
		assertEquals(-1, CarStateChange.getXSign(-1,-2));
		assertEquals(1, CarStateChange.getXSign(-2,-1));
	}

	@Test
	void getYSign() {
		assertEquals(1, CarStateChange.getYSign(1,2));
		assertEquals(1, CarStateChange.getYSign(2,1));
		assertEquals(-1, CarStateChange.getYSign(-1,-2));
		assertEquals(-1, CarStateChange.getYSign(-2,-1));
	}
}