import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class DodgeBall extends GraphicsProgram implements ActionListener {
	
	private ArrayList<GOval> balls;
	private ArrayList<GRect> enemies;
	private GLabel text;
	private Timer movement;
	private RandomGenerator rgen;
	private int numTimes = -1;
	private int enemiesRemoved = 0;
	
	private static final int ENEMY_SPAWN_INTERVAL = 40;
	public static final int SIZE = 25;
	public static final int SPEED = 2;
	public static final int MS = 50;
	public static final int MAX_ENEMIES = 10;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 300;
	
	public void run() {
		rgen = RandomGenerator.getInstance();
		balls = new ArrayList<GOval>();
		enemies = new ArrayList<GRect>();
		
		text = new GLabel(""+enemies.size(), 0, WINDOW_HEIGHT - 10);
		add(text);
		
		movement = new Timer(MS, this);
		movement.start();
		addMouseListeners();
	}
	
	public void actionPerformed(ActionEvent e) {
		numTimes++;
		if(numTimes % ENEMY_SPAWN_INTERVAL == 0) {
		    addAnEnemy();
		}
		
		if (enemies.size() > MAX_ENEMIES) {
			endGame();
			return;
		}
		moveAllBallsOnce();
		moveAllEnemiesOnce();
		
		
	}
	
	public void mousePressed(MouseEvent e) {
		for(GOval b:balls) {
			if(b.getX() < SIZE * 2.5) {
				return;
			}
		}
		addABall(e.getY());     
	}
	
	private void addABall(double y) {
		GOval ball = makeBall(SIZE/2, y);
		add(ball);
		balls.add(ball);
	}
	
	public GOval makeBall(double x, double y) {
		GOval temp = new GOval(x-SIZE/2, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.RED);
		temp.setFilled(true);
		return temp;
	}
	
	private void addAnEnemy() {
		GRect e = makeEnemy(rgen.nextInt(0, WINDOW_HEIGHT-SIZE/2));
		enemies.add(e);
		text.setLabel("" + enemies.size());
		add(e);
	}
	
	public GRect makeEnemy(double y) {
		GRect temp = new GRect(WINDOW_WIDTH-SIZE, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.GREEN);
		temp.setFilled(true);
		return temp;
	}

	private void moveAllBallsOnce() {
		for(GOval ball:balls) {
			ball.move(SPEED, 0);
			
			GObject obj = getElementAt(ball.getX() + ball.getWidth() + 1, ball.getY() + ball.getHeight() / 2);
			if (obj instanceof GRect) {
				remove(obj);
				this.enemies.remove(obj);
				this.enemiesRemoved++; // Increment enemiesRemoved counter
			}
		}
	}
	
	private void moveAllEnemiesOnce() {
		for (GRect enemy : enemies) {
			enemy.move(0, rgen.nextInt(-SPEED, SPEED));
		}
	}
	
	private void endGame() {
		movement.stop();
		removeAll();
		
		GLabel lostMessage1 = new GLabel ("You lost - Score: " + this.numTimes, WINDOW_WIDTH / 2 - 50, WINDOW_HEIGHT / 2);
		add(lostMessage1);
		
		GLabel lostMessage2 = new GLabel ("Enemies removed: " + this.enemiesRemoved, WINDOW_WIDTH / 2 - 50, WINDOW_HEIGHT / 2 + 20);
		add(lostMessage2);
	}
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public static void main(String args[]) {
		new DodgeBall().start();
	}
}
