package hu.bp.lightrobot;

import java.util.stream.IntStream;

public class RobotRunner {
	public static void main(String[] args) {
		Environment synchronWorld = new BulbWorld(true);
		Environment asynchronWorld = new BulbWorld(false);
		Agent lightRobot1 = new LightRobot(synchronWorld);

		IntStream.range(0, 10).forEach( i ->
			{
				lightRobot1.act(1, 100);
				System.out.println(lightRobot1);
				System.out.println(MLUtil.arrToString(lightRobot1.getGreedyPolicy()));
			}
		);
/*
		Agent lightRobot2 = new LightRobot(asynchronWorld);

		lightRobot2.act(10, 10);

		System.out.println(MLUtil.arrToString(lightRobot1.getGreedyPolicy()));
		System.out.println(MLUtil.arrToString(lightRobot2.getGreedyPolicy()));
		*/
	}
}
