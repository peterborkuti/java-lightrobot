package hu.bp.lightrobot;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MLUtilTest {

	@Test
	public void testArgMax() {
		assertEquals(-1, MLUtil.argMax(new ArrayList<Integer>()));
		assertEquals(2, MLUtil.argMax(Arrays.asList(new Integer[]{0,0,1,0})));
		assertEquals(4, MLUtil.argMax(Arrays.asList(new Double[]{1D,-3D,7D,-10D, 20D})));
		assertEquals(0, MLUtil.argMax(Arrays.asList(new Integer[]{1, 1, 1 ,1 ,1})));
		assertEquals(0, MLUtil.argMax(Arrays.asList(new Double[]{0.8, 0.2})));
		assertEquals(1, MLUtil.argMax(Arrays.asList(new Double[]{0.2, 0.8})));
	}

	@Test
	public void testrowArgMax() {
		assertArrayEquals(new Integer[]{0,0,1,1}, MLUtil.rowArgMax(new Double[][]{{0.8,0.2},{0.8,0.2},{0.2,0.8},{0.2,0.8}}));
		assertArrayEquals(new Integer[]{1,0,0,1}, MLUtil.rowArgMax(new Double[][]{{0.2,0.8},{0.8,0.1},{0.6,0.5},{0.2,0.8}}));
	}

}