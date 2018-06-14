package hu.bp.lightrobot;

import java.util.stream.IntStream;

public class RobotRunner {
	public static void main(String[] args) {
		Environment synchronWorld = new BulbWorld(true);
		LightRobotTD lightRobot1 = new LightRobotTD(synchronWorld);

		System.out.println("\nQ:" + MLUtil.matrixToString(
				lightRobot1.controlQLearning(1, 1000, 0.5, 0.5)));
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
