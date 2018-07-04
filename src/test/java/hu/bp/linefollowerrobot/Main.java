package hu.bp.linefollowerrobot;

public class Main {
	public static void main(String[] args) {
		Car car = new Car(1, 1);
		double x = 0;
		double y = 0;
		double a = 0;
		for (int i = 0; i < 100; i++) {
			CarStateChange csc = car.moveOnCircle(1.1, 1, 1.0 / 40.0);
			x += csc.x;
			y += csc.y;
			a += (180.0 / Math.PI) * csc.angle;

			System.out.format("%2.1f, %2.1f, %3.1f\n", x,y,a);

			//System.out.println(car.moveOnCircle(3, 1, 1.0 / 40.0));
		}
	}
}
