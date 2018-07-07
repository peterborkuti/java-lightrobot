package hu.bp.lightrobot;

import hu.bp.ai.interfaces.Environment;
import hu.bp.ai.util.MLUtil;

public class RobotRunner {
	public static void main(String[] args) {
		Environment synchronWorld = new BulbWorld(true);
		LightRobotTD lightRobot1 = new LightRobotTD(synchronWorld);

		lightRobot1.controlQLearning(1, 1000, 0.5, 0.5);

		for (;lightRobot1.oneStep(););

//		System.out.println("\nQ:" + MLUtil.matrixToString(
//				lightRobot1.s);
/*
		Agent lightRobot2 = new LightRobotMC(asynchronWorld);

		lightRobot2.act(10, 10);
*/
		System.out.println(MLUtil.arrToString(lightRobot1.getGreedyPolicy()));
/*
		System.out.println(MLUtil.arrToString(lightRobot2.getGreedyPolicy()));
		*/
	}
}
