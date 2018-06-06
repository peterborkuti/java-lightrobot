package hu.bp.lightrobot;

public class RobotRunner {
	public static void main(String[] args) {
		Environment synchronWorld = new BulbWorld(true);
		Environment asynchronWorld = new BulbWorld(false);
		Agent lightRobot1 = new LightRobot(synchronWorld);

		lightRobot1.act(1000, 1000);

		Agent lightRobot2 = new LightRobot(asynchronWorld);

		lightRobot2.act(1000, 1000);

		System.out.println(MLUtil.arrToString(lightRobot1.getGreedyPolicy()));
		System.out.println(MLUtil.arrToString(lightRobot2.getGreedyPolicy()));
	}
}
