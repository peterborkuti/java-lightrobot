package hu.bp.linefollowerrobot;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;
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

		setSize(600, 600);
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

		int centerX = getWidth() / 2 + x;
		int centerY = getHeight() / 2 + y;
		at.rotate(angle,  centerX, centerY);
		at.translate(centerX, centerY);

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
	private Random rnd = new Random();

	public CarMover(Car car, CarGraphics carGraphics) {
		this.car = car;
		this.carGraphics = carGraphics;
	}


	/**
	 * The action to be performed by this timer task.
	 */
	@Override
	public void run() {
		CarStateChange carStateChange = car.move(Math.abs(rnd.nextInt(10)), Math.abs(rnd.nextInt(10)), 0.1);
		carGraphics.drawCar(carStateChange);
	}
}

public class CarDisplay {
	public static void main(String[] args) throws InterruptedException {
		Car car = new Car(30, 5);
		CarGraphics carGraphics = new CarGraphics(car);
		CarMover carMover = new CarMover(car, carGraphics);

		Timer timer = new Timer();
		timer.schedule(carMover, 1000, 100);

		Thread.sleep(30000);

		timer.cancel();
		timer.purge();
	}
}
