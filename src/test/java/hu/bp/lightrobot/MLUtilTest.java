package hu.bp.lightrobot;

import hu.bp.ai.util.MLUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

	@Test
	public void testAverageFromAList() {
		assertEquals(0, (double)MLUtil.getAverageReturnForAStateAction(Collections.EMPTY_LIST));
		Double[] test1 = {1D, 1D, 1D, 1D};
		assertEquals(1, (double)MLUtil.getAverageReturnForAStateAction(Arrays.asList(test1)));
		Double[] test2 = {1D, 2D, 3D, 4D, 5D};
		assertEquals(3, (double)MLUtil.getAverageReturnForAStateAction(Arrays.asList(test2)));
	}

}