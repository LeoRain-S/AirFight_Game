package AirFight_LeoShi;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import java.io.*;
/*
 * Name: Leo Shi    
 * Data: 2022/6/22
 * Description : This program will start a game called air fighter, this game is simple user control a air jet to fight the enemy
 * 				 The game have 3 different difficulty mode and the difficulty will increase as the game processing, in order to challenge
 * 				 the player. The game will save the highest record. 
 */
public class Main extends JPanel implements KeyListener, Runnable, ActionListener{
	// set up all stages
	int stage = 0;
	static int start = 0;
	static int processing = 1;
	static int stop = 2;
	static int end = 3;
	int shootSpeed = 25;

	static int highestScore = 0; // the highest record
	static String gameMode = "Normal"; // game mode

	// set up all variables
	Player p = new Player();
	Bullet[] bullets = {};
	movingThing[] flys = {};
	boolean up, down, left, right;
	int speed = 4;
	int screenWidth = 400;
	int screenHeight = 600;
	Thread thread;
	int FPS = 60;
	int Score = 0;
	int life = 3;

	public static void main(String[] args) {
		// set up the main frame
		Main a = new Main();
		JFrame frame = new JFrame("Air Fighter!");
		Main myPanel = new Main();
		// the mene bar
		JMenuBar menu = new JMenuBar();
		JMenu m = new JMenu("Menu");
		JMenuItem x1, x2, x3, s1, s2, s3;
		x1 = new JMenuItem("How To Play");
		x2 = new JMenu("Game Mode");
		x3 = new JMenuItem("ABOUT");
		s1 = new JMenuItem("Free");
		s2 = new JMenuItem("Normal");
		s3 = new JMenuItem("Hell");
		// add actionlistener to menu bar
		x1.addActionListener(a);
		x3.addActionListener(a);
		s1.addActionListener(a);
		s2.addActionListener(a);
		s3.addActionListener(a);

		x2.add(s1);
		x2.add(s2);
		x2.add(s3);

		m.add(x1);
		m.add(x2);
		m.add(x3);

		menu.add(m);

		frame.setJMenuBar(menu);
		frame.add(myPanel);
		frame.addKeyListener(myPanel);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

	public Main() {
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setVisible(true);

		thread = new Thread(this);
		thread.start();
	}

	/**
	 * paint different component for different stage of the game
	 */
	public void paint(Graphics g) {
		super.paintComponent(g);
		if (stage == end) { // the game over stage
			paintBackground(g);
			paintEnding(g);
		} else if (stage == start) { // the starting stage
			paintBackground(g);
			paintStart(g);
			paintRecordAndGameMode(g);
		} else if (stage == processing) { // the game in processing
			paintBackground(g);
			paintPlayer(g);
			paintBullet(g);
			paintEnemy(g);
			paintScore(g);
		}

	}

	/**
	 * paint the components for the START stage
	 * 
	 * @param g
	 */
	public void paintStart(Graphics g) {
		ImageIcon icon = new ImageIcon("AirFight_LeoShi/image/text3.png");
		Image Img = icon.getImage();
		g.drawImage(Img, 0, 200, null);

		ImageIcon icon2 = new ImageIcon("AirFight_LeoShi/image/text2.png");
		Image Img2 = icon2.getImage();
		g.drawImage(Img2, 0, 400, null);

	}

	/**
	 * display the player record and selected game mode
	 * 
	 * @param g
	 */
	public void paintRecordAndGameMode(Graphics g) {
		int x = 120;
		int y = 30;
		Font f = new Font(Font.SERIF, Font.BOLD, 18); // set font
		g.setFont(f);
		g.drawString("Highest Record: " + highestScore, x, y); // display record

		int x2 = 120;
		int y2 = 50;
		Font f2 = new Font(Font.SERIF, Font.BOLD, 22); // set font
		g.setFont(f2);
		g.drawString("Game Mode: " + gameMode, x2, y2); // display game mode

	}

	/**
	 * paint the game over stage
	 * 
	 * @param g
	 */
	public void paintEnding(Graphics g) {
		ImageIcon icon = new ImageIcon("AirFight_LeoShi/image/end.png");
		Image Img = icon.getImage();
		g.drawImage(Img, 20, 200, null);

		ImageIcon icon2 = new ImageIcon("AirFight_LeoShi/image/text2.png");
		Image Img2 = icon2.getImage();
		g.drawImage(Img2, 0, 400, null);
	}

	/**
	 * paint the game background
	 */
	public void paintBackground(Graphics g) {
		ImageIcon icon = new ImageIcon("AirFight_LeoShi/image/start.png");
		Image backgroundImg = icon.getImage();
		g.drawImage(backgroundImg, 0, 0, null);
	}

	/**
	 * paint the player's jet
	 * 
	 * @param g
	 */
	public void paintPlayer(Graphics g) {
		g.drawImage(p.getImg(), p.getX(), p.getY(), null);
	}

	/**
	 * paint the enemy jet
	 * 
	 * @param g
	 */
	public void paintEnemy(Graphics g) {
		for (int i = 0; i < flys.length; i++) {
			movingThing f = flys[i];
			g.drawImage(f.getImg(), f.getX(), f.getY(), null);
		}
	}

	/**
	 * paint the bullet
	 * 
	 * @param g
	 */
	public void paintBullet(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.getImg(), b.getX(), b.getY(), null);
		}
	}

	/**
	 * paint the score
	 */
	public void paintScore(Graphics g) {
		int x = 15;
		int y = 30;
		Font f = new Font(Font.MONOSPACED, Font.ITALIC, 18);
		g.setFont(f);
		g.drawString("Score: " + Score, x, y);
		g.drawString("Life: " + life, x, y + 20);
	}

	/**
	 * create new enemy randomly
	 * 
	 * @return the object type that will be created
	 */
	public static movingThing newEnemy() {
		Random random = new Random();
		int type = random.nextInt(100); // base on this random number, different enemy kind will be created

		if (gameMode.equals("Normal")) { // for game mode --- normal
			if (60 < type && type <= 95) {
				return new Enemy();
			} else if (20 < type && type <= 60) {
				return new Obstacle();
			} else if (0 <= type && type <= 20) {
				return new Obstacle2();
			} else {
				return new heart();
			}
		} else if (gameMode.equals("Hell")) { // for game mode --- hell
			if (60 < type && type <= 97) {
				return new Enemy();
			} else if (40 < type && type <= 60) {

				return new Obstacle();

			} else if (0 <= type && type <= 40) {
				return new Obstacle2();
			} else {
				return new heart();
			}
		} else { // for game mode --- free
			if (60 < type && type <= 100) {
				return new Enemy();
			} else if (10 < type && type <= 60) {
				return new Obstacle();
			} else if (0 <= type && type <= 10) {
				return new heart();
			} else {
				return new Obstacle();
			}
		}
	}

	int flyIndex = 0;
	int difficulty = 0;
	int enemySpawnTime = 50;
	/**
	 * this will created a new flying object
	 */
	public void newFlysAction() {
		flyIndex++;

		if (flyIndex % (enemySpawnTime - difficulty) == 0) { // increase the difficulty as the score increase
			if (Score % 100 == 0) {
				difficulty = Score / 20;
			}
			movingThing o = newEnemy();
			flys = Arrays.copyOf(flys, flys.length + 1);
			flys[flys.length - 1] = o;
		}
	}
	// update the game scene
	public void update() {
		if (stage == processing) {
			move();
			keepInBound();
			moveAction();
			shootAction();
			checkOutAction();
			newFlysAction();
			flysMove();
			deleteAction();
			cheakGameAction();
		}
		repaint();

	}
	/*
	 * move the bullets
	 */
	public void moveAction() {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			b.move();
		}
	}
	/**
	 * move the flying objects
	 */
	public void flysMove() {
		for (int i = 0; i < flys.length; i++) {
			movingThing f = flys[i];
			f.move();
		}
	}
	
	int shootTime = 0;
	/**
	 * the action of the player's jet shoot
	 */
	public void shootAction() {
		shootTime++;
		if (shootTime % shootSpeed == 0) {
			Bullet[] shootBullets = p.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length + shootBullets.length);
			System.arraycopy(shootBullets, 0, bullets, bullets.length - shootBullets.length, shootBullets.length);
			// System.out.println("fire");
		}
	}
	/**
	 * check if any flying objects are out of the scene
	 */
	public void checkOutAction() {
		int index = 0;
		Bullet[] bulletInside = new Bullet[bullets.length]; // save all the bullet inside the scene
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds()) { // check if the bullet is inside
				bulletInside[index++] = b;
			} else { // delete the bullet outside
			}
		}
		bullets = Arrays.copyOf(bulletInside, index);

		index = 0;
		movingThing[] f = new movingThing[flys.length];
		for (int i = 0; i < flys.length; i++) {
			movingThing fCheck = flys[i];
			if (!fCheck.outOfBounds()) { // check if the object is inside
				f[index++] = fCheck;
			} else { // delete the object if its outside
				if (fCheck instanceof Enemy) { // if it is the enemy jet, the player will lose one life
					life -= 1;
					Score -=100; 
				}
			}
		}
		flys = Arrays.copyOf(f, index);
	}
	/**
	 * check if the game is over
	 */
	public void cheakGameAction() {
		if (gotHit() == true) { // game over
			stage = end; // change the game stage to end
			if (Score > highestScore) { // save the highest record
				highestScore = Score;
			}
			// reset life and score
			life = 3;
			Score = 0;

		}
	}
	/**
	 * check if the player's jet got hit by a flying object
	 * @return a boolean 
	 */
	public boolean gotHit() {
		int index = -1;
		for (int i = 0; i < flys.length; i++) {
			movingThing f = flys[i];
			if (p.crush(f)) {
				if (f instanceof heart) { // hit by a heart, life plus 1
					life++;
					index = i;
				} else {
					life--;
					index = i;
					p.reset(); // reset the position of player's jet
				}
			}
		}

		if (index != -1) {
			movingThing f2 = flys[index];
			flys[index] = flys[flys.length - 1];
			flys[flys.length - 1] = f2;
			flys = Arrays.copyOf(flys, flys.length - 1);
		}

		return life <= 0; // when player lost all the life, game over, return true
	}
	/**
	 * delete the objects and bullets when needed
	 */
	public void deleteAction() {
		for (int i = 0; i < bullets.length; i++) {
			delete(bullets, i);
		}
	}

	public void delete(Bullet[] bullet, int a) {
		int index = -1;
		for (int i = 0; i < flys.length; i++) {
			if (flys[i].gotKilled(bullet[a])) { // got hit by the bullet, delete the object
				if (flys[i] instanceof heart) { // except the heart
					break;
				} else {
					index = i;
					break;
				}
			}
		}

		if (index != -1) {
			movingThing deadOne = flys[index]; // save the object that got killed
			movingThing copyOfDeadOne = flys[index];
			flys[index] = flys[flys.length - 1];
			flys[flys.length - 1] = copyOfDeadOne;
			int index2 = 0;

			Bullet[] bulletInside = new Bullet[bullets.length];
			for (int i = 0; i < bullets.length; i++) {
				Bullet b = bullets[i];
				if (i != a) {
					bulletInside[index2++] = b; // save bullet
				} else { // delete bullet
				}
			}
			bullets = Arrays.copyOf(bulletInside, index2);
			flys = Arrays.copyOf(flys, flys.length - 1);

			if (deadOne instanceof Enemy) { // check the instance of it
				Enemy e = (Enemy) deadOne;
				Score += e.destroyed();
			} else { // rock will not add score
			}
		}
	}
	/**
	 * control the player's jet by keyboard
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			left = true;
			right = false;
		} else if (key == KeyEvent.VK_D) {
			right = true;
			left = false;
		} else if (key == KeyEvent.VK_W) {
			up = true;
			down = false;
		} else if (key == KeyEvent.VK_S) {
			down = true;
			up = false;
		} else if (key == KeyEvent.VK_SPACE) { // start the game
			if (stage == end) {
				flys = new movingThing[0];
				bullets = new Bullet[0];
				p = new Player();
				Score = 0;
				stage = start;
			} else if (stage == start) {
				stage = processing;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			left = false;
		} else if (key == KeyEvent.VK_D) {
			right = false;
		} else if (key == KeyEvent.VK_W) {
			up = false;
		} else if (key == KeyEvent.VK_S) {
			down = false;
		}
	}

	@Override
	/**
	 * control the menu bar
	 */
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.equals("How To Play")) { // user select how to play
			howToPlayWindow();
		} else if (s.equals("ABOUT")) { // user select about
			aboutWindow();
		} else if (s.equals("Free")) { // user select free
			gameMode = s; // change the difficulty
			life = 3;
			shootSpeed = 50;
			enemySpawnTime = 30;
		} else if (s.equals("Normal")) { // user select normal
			gameMode = s; // change the difficulty
			life = 2;
			shootSpeed = 25;
			enemySpawnTime = 50;
		} else if (s.equals("Hell")) { // user select hell
			gameMode = s; // change the difficulty
			life = 1;
			shootSpeed = 20;
			enemySpawnTime = 70;
		}
	}
	/**
	 * display a new window when user select How To Play in the menu bar
	 */
	public static void howToPlayWindow() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				JLabel l1, l2, l3, l4, l5; // JLabels
				// set up JFrame
				JFrame frame = new JFrame("How To Play");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(0, 1)); // set layout
				panel.setPreferredSize(new Dimension(800, 300)); // set size
				// set the image
				ImageIcon icon = new ImageIcon("AirFight_LeoShi/image/hardRock.png");
				Image i = icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
				icon = new ImageIcon(i);
				// add image to the Label
				l1 = new JLabel("    ---    This is just a normal rock:)", icon, SwingConstants.HORIZONTAL);
				// set the image
				ImageIcon icon2 = new ImageIcon("AirFight_LeoShi/image/rock.png");
				i = icon2.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
				icon2 = new ImageIcon(i);
				l2 = new JLabel("    ---    This is a special rock, it fly really fast :)", icon2,
						SwingConstants.HORIZONTAL);
				// set the image
				ImageIcon icon3 = new ImageIcon("AirFight_LeoShi/image/Enemy_Image.png");
				i = icon3.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
				icon3 = new ImageIcon(i);
				// add image to the Label
				l3 = new JLabel(
						"    ---    They are the bad guys, you can't let them fly cross the board, destroy them! ",
						icon3, SwingConstants.HORIZONTAL);
				// set the image
				ImageIcon icon4 = new ImageIcon("AirFight_LeoShi/image/player.png");
				i = icon4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
				icon4 = new ImageIcon(i);
				// add image to the Label
				l4 = new JLabel(
						"    ---    This is your jet, control your jet and destroy the enemy, but don't forget the dodge the rock!",
						icon4, SwingConstants.HORIZONTAL);

				l5 = new JLabel(
						"                      You will use 'w' 'a' 's' 'd' to control your jet, every enemy you destroy will add up to your score. Now, be ready to fight!");
				// add Labels to the panel
				panel.add(l1);
				panel.add(l2);
				panel.add(l3);
				panel.add(l4);
				panel.add(l5);

				frame.getContentPane().add(BorderLayout.CENTER, panel);
				frame.pack();
				frame.setVisible(true);
				frame.setResizable(false);
			}
		});
	}
	/**
	 * display a new window when user select About in the menu bar
	 */
	public static void aboutWindow() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// set JLabels
				JLabel l1, l2, l3, l4, l5, l6, l7, l8;
				JFrame frame = new JFrame("About");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(0, 1)); // set layout
				panel.setPreferredSize(new Dimension(800, 300)); // set size
				// set up the texts in JLabel
				l1 = new JLabel("What is this game?", SwingConstants.CENTER);
				Font f = new Font(Font.SERIF, Font.BOLD, 22); // font
				l1.setFont(f);
				l2 = new JLabel("This is a air fighter game, in this game, you will be fighting with enemy.",
						SwingConstants.CENTER);
				l3 = new JLabel(
						"Background: There are enemy trying to get pass the border of your country and you are the last one standing, what is your choice?",
						SwingConstants.CENTER);
				l4 = new JLabel(
						"(Destroy the enemy will give you score, try your best to get the highest score you can get!)",
						SwingConstants.CENTER);
				l5 = new JLabel("You can choose the difficulty by select game mode in the menu bar.",
						SwingConstants.CENTER);
				l6 = new JLabel("The difficulty of the game will increase as you reach a higher score.",
						SwingConstants.CENTER);
				l7 = new JLabel("--- Made by Leo Shi", SwingConstants.CENTER);
				l8 = new JLabel(
						"You have 3 lives, If you got hit, you lost 1 life, if one enemy get pass the border, you lost 1 life.",
						SwingConstants.CENTER);
				// add JLable to panel
				panel.add(l1);
				panel.add(l2);
				panel.add(l3);
				panel.add(l8);
				panel.add(l5);
				panel.add(l6);
				panel.add(l4);
				panel.add(l7);
				frame.getContentPane().add(BorderLayout.CENTER, panel);
				frame.pack();
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
				frame.setResizable(false);

			}
		});
	}
	/**
	 * move player's jet base on the keyboard input
	 */
	void move() {
		if (left)
			p.x -= speed;
		else if (right)
			p.x += speed;
		if (up)
			p.y += -speed;
		else if (down)
			p.y += speed;
	}
	/*
	 * keep the player's jet in the scene
	 */
	void keepInBound() {
		if (p.x < 0)
			p.x = 0;
		else if (p.x > screenWidth - p.width)
			p.x = screenWidth - p.width;

		if (p.y < 0)
			p.y = 0;
		else if (p.y > screenHeight - p.height)
			p.y = screenHeight - p.height;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	@Override
	public void run() {
		while (true) {
			// main game loop
			update();
			this.repaint();
			try {
				Thread.sleep(1000 / FPS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
