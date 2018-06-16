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
}