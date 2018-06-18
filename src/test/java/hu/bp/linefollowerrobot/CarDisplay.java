package hu.bp.linefollowerrobot;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

class CarGraphics extends Frame {
	private final Car car;
	private int x, y, halfTrackWidth, wheelRadius;
	private double angle;

	public CarGraphics (Car car) {
		this.car = car;
		halfTrackWidth = (int)car.trackWidth;
		wheelRadius = (int)(car.wheelDiameter / 2.0);

		setSize(300, 300);
		x = 0;
		y = 0;
		angle = 0;
		setVisible(true);
	}

	public void drawCar(CarStateChange carStateChange) {
		x += carStateChange.x;
		y += carStateChange.y;
		angle += carStateChange.angle;

		repaint();
	}


	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		AffineTransform saveAT = g2d.getTransform();

		AffineTransform at = new AffineTransform(saveAT);

		at.rotate(angle);
		at.translate(100 + x, 100 + y);

		g2d.setTransform(at);

		drawCar(g2d);

		g2d.setTransform(saveAT);
	}

	private void drawCar(Graphics2D g2d) {
		g2d.setColor(Color.blue);

		g2d.drawLine(x - halfTrackWidth, y, x + halfTrackWidth, y);
		g2d.drawLine(x - halfTrackWidth, y - wheelRadius, x - halfTrackWidth, y + wheelRadius);
		g2d.drawLine(x + halfTrackWidth, y - wheelRadius, x + halfTrackWidth, y + wheelRadius);

		g2d.setColor(Color.red);

		g2d.drawLine(x, y, x, y + 4 * wheelRadius);
	}
}

class CarMover extends TimerTask {
	private CarGraphics carGraphics;
	private final Car car;
	public CarMover(Car car, CarGraphics carGraphics) {
		this.car = car;
		this.carGraphics = carGraphics;
	}


	/**
	 * The action to be performed by this timer task.
	 */
	@Override
	public void run() {
		CarStateChange carStateChange = car.move(2, 0, 0.1);
		carGraphics.drawCar(carStateChange);
	}
}

public class CarDisplay {
	public static void main(String[] args) throws InterruptedException {
		Car car = new Car(30, 5);
		CarGraphics carGraphics = new CarGraphics(car);
		CarMover carMover = new CarMover(car, carGraphics);

		Timer timer = new Timer();
		timer.schedule(carMover, 1000, 1000);

		Thread.sleep(10000);

		timer.cancel();
		timer.purge();
	}
}
