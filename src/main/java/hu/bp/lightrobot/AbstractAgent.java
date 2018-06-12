package hu.bp.lightrobot;

import java.util.List;

public abstract class AbstractAgent implements Agent {
	protected final Environment world;

	protected Double[][] policy;

	// @Todo
	private Double[][] q;

	protected List<Double> returns[][];

	protected double stepSize = 0.1;
	protected double epsilon = 0.1;

	AbstractAgent(Environment world, double stepSize, double epsilon) {
		this.world = world;
		this.stepSize = stepSize;
		this.epsilon = epsilon;
		q =  MLUtil.getRandomMatrix(world.getNumberOfStates(), getNumberOfActions());
		returns = MLUtil.getMatrixOfEmptyArrayLists(world.getNumberOfStates(), getNumberOfActions());
		policy = MLUtil.getMatrix(world.getNumberOfStates(), getNumberOfActions(), 1 / getNumberOfActions());
	}

	public String toString() {
		return
				"Q:\n" + MLUtil.matrixToString(q) + "\n" +
						"Returns:\n" + MLUtil.matrixListToString(returns) + "\n" +
						"AVGReturns:\n" + MLUtil.getAverageReturns(returns) + "\n" +
						"Policy:\n" + MLUtil.matrixToString(policy);
	}




}
