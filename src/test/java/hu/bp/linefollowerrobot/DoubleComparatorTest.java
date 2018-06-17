package hu.bp.linefollowerrobot;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class DoubleComparatorTest {
	private class A implements DoubleComparator {
		public final double x;

		public A(double x) {
			this.x = x;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;

			if (!(o instanceof A)) return false;

			A a = (A) o;

			return equals(a.x, x);
		}

		@Override
		public int hashCode() {

			return Objects.hash(x);
		}
	}

	@Test
	void equals() {
		A a1 = new A(-1.0);
		A a2 = new A(-0.999999999999999999);

		assertEquals(a1, a2);

		A a3 = new A(2);
		assertNotEquals(a1, a3);
	}
}