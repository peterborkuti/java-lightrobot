package hu.bp.lightrobot;

public class RobotRunner {
	public static void main(String[] args) {
		BulbWorld world = new BulbWorld();
		Robot robot = new Robot(world);

		robot.act(10, 10);
	}
}
