/*
 * Board class - handles the bulk of the game duties.
 */

import java.awt.BorderLayout;// imports library necessary to use BorderLayout in JPanel
import java.awt.Color;// imports library used to implement Game background color
import java.awt.Dimension;// imports library necessary to use Dimension objects
import java.awt.Graphics;// imports library necessary for the Graphics used to implement Game board
import java.awt.Graphics2D;// imports library necessary for the Graphics2D used to implement Game board
import java.awt.GridLayout;// imports library necessary for the GridLayout
import java.awt.Rectangle;// imports library necessary for Rectangles used for collisions
import java.awt.Toolkit;// imports library necessary for the Toolkit
import java.awt.event.ActionEvent;// imports library necessary for handling ActionEvents
import java.awt.event.ActionListener;// imports library necessary for using an ActionListener
import java.awt.event.KeyAdapter;// imports library necessary for using the KeyAdapter
import java.awt.event.KeyEvent;// imports library necessary for handling KeyEvents
import java.util.ArrayList;// imports library necessary for ArrayList usage
import java.util.Random;// imports library necessary for random number generation
import javax.swing.JLabel;// imports library necessary for using JLabel
import javax.swing.JPanel;// imports library necessary for using JPanel
import javax.swing.JTextField;// imports library necessary for the text field that holds the score
import javax.swing.Timer;// imports library necessary for the timer used

public class Board extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Timer timer;// represents the game timer
	protected static ArcherGirl archerGirl;// represents the user-controlled, main character in the game
	private final int DELAY = 10;// used to set the between-event delay
	private ArrayList<Target> targets = new ArrayList<Target>();// represents a collection of Targets in the Game
	private ArrayList<Spider> spiders = new ArrayList<Spider>();// represents a collection of Spiders in the Game
	private ArrayList<Wall> walls = new ArrayList<Wall>();// represents a collection of Walls in the Game
	private int count = 0;// represents a count of the game loops, used for adjusting game difficulty
	private int score = 0;// represents a player's score
	private JPanel statusPane = new JPanel();// adds a JPanel to hold the score
	private JLabel scoreLabel = new JLabel("Score:");// holds label for player's score
	private JTextField playerScore = new JTextField(score);// holds player's score
	
	// Board constructor - no parameters necessary
	public Board() {
		addKeyListener(new TAdapter());// adds a Key Listener TAdapter
		setFocusable(true);// allows focusable
		setPreferredSize(new Dimension(600, 600));// sets the preferred size
		setBackground(Color.WHITE);// sets the background color of the display to white
		setDoubleBuffered(true);// sets double vuffered to true
		setLayout(new BorderLayout());// sets the layout of the Board JPanel
		
		statusPane.setLayout(new GridLayout(1, 2));;// sets the layout for the status JPanel pane
		statusPane.add(scoreLabel, BorderLayout.EAST);// adds the score JLabel to the status pane
		statusPane.add(playerScore);// adds the playerScore JTextField to the status pane
		add(statusPane, BorderLayout.SOUTH);// adds the statusPane holding the acore to the display
		
		archerGirl = new ArcherGirl();// creates Game instance of ArcherGirl
		walls.add(new Wall(("wall.png"), new float[] {150, 150}));// creates stone pillar in Game space
		walls.add(new Wall(("wall.png"), new float[] {350, 350}));// creates stone pillar in Game space
		walls.add(new Wall(("edge.png"), new float[] {0, 0}));// creates left-side wall in Game space
		walls.add(new Wall(("edge.png"), new float[] {585, 0}));// creates right-side wall in Game space
		
		timer = new Timer(DELAY, this);// creates timer with between-event DELAY 
		timer.start();// starts 
	}// Board() constructor
	
	@Override // paintComponent method - performs duties to paint Board
	public void paintComponent(Graphics g) {
		super.paintComponent(g);// calls JPanel paintComponent
		doDrawing(g);// calls the doDrawing method to get graphics information about objects in the Game
		Toolkit.getDefaultToolkit().sync();// synchronizes the toolkit state
	}// paintComponent(Graphics) method
	
	// doDrawing method - gets graphics information for each object in the Game
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;// converts Graphics g to Graphics2D
		g2d.rotate(archerGirl.orientation, (int)archerGirl.position[0] + archerGirl.width / 2, (int)archerGirl.position[1] + archerGirl.height  / 2);// performs rotation based on Spider's orientation
		g2d.drawImage(archerGirl.getImage(), (int)archerGirl.position[0], (int)archerGirl.position[1], this);// draws ArcherGirl with its current information
		g2d.rotate(-archerGirl.orientation, (int)archerGirl.position[0] + archerGirl.width  / 2, (int)archerGirl.position[1] + archerGirl.height  / 2);// performs inverse rotation
		ArrayList<Arrow> arrows = archerGirl.getArrows();// gets Arrows from ArcherGirl collection
		for (Object object : arrows) {// loops through all Arrow objects
			Arrow arrow = (Arrow) object;// gets each individual Arrow object
			g2d.rotate(arrow.orientation, (int)arrow.position[0] + arrow.width / 2, (int)arrow.position[1] + arrow.height  / 2);// performs rotation based on Spider's orientation
			g2d.drawImage(arrow.getImage(), (int)arrow.position[0], (int)arrow.position[1], this);// draws each Arrow with its current information
			g2d.rotate(-arrow.orientation, (int)arrow.position[0] + arrow.width / 2, (int)arrow.position[1] + arrow.height  / 2);// performs inverse rotation
		}// for (arrows)
		for (Object object : targets) {// loops through all Target objects
			Target target = (Target) object;// gets each individual Target object
			g2d.drawImage(target.getImage(), (int)target.position[0], (int)target.position[1], this);// draws each Target with its current information
		}// for (targets)
		for (Object object : spiders) {// loops through all Spider objects
			Spider spider = (Spider) object;// gets each individual Spider object
			g2d.rotate(spider.orientation, (int)spider.position[0] + spider.width / 2, (int)spider.position[1] + spider.height / 2);// performs rotation based on Spider's orientation
			g2d.drawImage(spider.getImage(), (int)spider.position[0], (int)spider.position[1], this);// draws each Spider with its current information
			g2d.rotate(-spider.orientation, (int)spider.position[0] + spider.width / 2, (int)spider.position[1] + spider.height / 2);// performs inverse rotation
		}// for (spiders)
		for (Object object : walls) {// loops through all Wall objects
			Wall wall = (Wall) object;// gets each individual Wall object
			g2d.drawImage(wall.getImage(), (int)wall.position[0], (int)wall.position[1], this);// draws each Wall with its current information
		}// for (walls)
	}// doDrawing(Graphics) method
	
	@Override // actionPerformed method - main game loop, updates Game world
	public void actionPerformed(ActionEvent e) {
		archerGirl.move(targets, spiders, walls);// calls method to move archerGirl every time through
		
		updateArrows();// calls method to update Arrows
		if (count % 3 == 0)// updates Targets at every such interval
			updateTargets();// calls method to update Targets
		if (count % 3 == 0)// updates Spiders at every such interval
			updateSpiders();// calls method to update Spiders
		
		Random random = new Random();// gets random instance for use generating Targets
		if (count % 1000 == 0)// adds a Target at random point of Game space every such interval
			targets.add(new Target(new float[] {(float)random.nextInt(550) + 25, (float)random.nextInt(550) + 25}));// add a Target at random position
		if (count % 1000 == 0)// adds a Spider at top of Game space every such interval
			spiders.add(new Spider(random.nextInt(550) + 25, 0, 1, archerGirl));// adds a Spider at top of Game space
		if (count++ % 2000 == 0)// adds a Spider at bottom of Game space every such interval
			spiders.add(new Spider(random.nextInt(550) + 25, 600, 1, archerGirl));// adds a Spider at bottom of Game space
		
		checkArrows();// calls method to see if any arrows have collided with any objects
		checkKilled();// calls method to check if a Spider has killed ArcherGirl
		
		repaint();// repaints with current infor
	}// actionPerformed(ActionEvent) method
	
	// updateArrows method - removes spiders or sends them for movement
	private void updateArrows() {
		for (int i = 0; i < archerGirl.getArrows().size(); i++) {// loops through all Arrows
			Arrow arrow = (Arrow)archerGirl.getArrow(i);// creates duplicate of each Arrow, necessary for deletion purposes
			if (arrow.isVisible()) {// is visible
				arrow.move();// calls move to determine appropriate movement
			} else {// not visible
				archerGirl.removeArrow(arrow);// removes Arrows that are no longer visible (active)
			}// if (isVisible)
		}// for (arrows)
	}// updateArrows() method
	
	// updateTargets method - removes spiders or sends them for movement
	private void updateTargets() {
		for (int i = 0; i < targets.size(); i++) {// loops through all Targets
			Target target = (Target)targets.get(i);// creates duplicate of each Target, necessary for deletion purposes
			if (target.isVisible()) {// is visible
				target.move(archerGirl, walls, spiders, targets);// calls move to determine appropriate movement
			} else {// not visible
				targets.remove(i);// removes Targets that are no longer visible (active)
			}// if (isVisible)
		}// for (targets)
	}// updateTargets() method
	
	// updateSpiders method - removes spiders or sends them for movement
	private void updateSpiders() {
		for (int i = 0; i < spiders.size(); i++) {// loops through all Spiders
			Spider spider = (Spider)spiders.get(i);// creates duplicate of each Spider, necessary for deletion purposes
			if (spider.isVisible()) {// is visible
				spider.move(archerGirl, walls, spiders, targets);// calls move to determine appropriate movement
			} else {// not visible
				spiders.remove(i);// removes Spiders that are no longer visible (active)
			}// if (isVisible)
		}// for (spiders)
	}// updateSpiders() method
	
	// checkArrow method - checks Arrows for collisions with Targets, Walls, and Spiders, and acts accordingly
	private void checkArrows() {
		for (int i = 0; i < archerGirl.getArrows().size(); i++) {// loops through all Arrows
			Arrow arrow = (Arrow)archerGirl.getArrow(i);// creates duplicate of each Arrow, necessary for deletion purposes
			Rectangle sprite = new Rectangle((int)(arrow.position[0]), (int)(arrow.position[1]), arrow.getWidth(), arrow.getHeight());// gets rectangle enclosing ArcherGirl
			for (int j = 0; j < targets.size(); j++) {// loops through all Targets
				Target target = (Target) targets.get(j);// creates duplicate of each Target, necessary for deletion purposes
				Rectangle targetRec = new Rectangle((int)(target.position[0]), (int)(target.position[1]), target.getWidth(), target.getHeight());// gets rectangle enclosing Spider
				if (targetRec.intersects(sprite)) {// checks for target/arrow collision)
					targets.remove(j);// remove the target from an active state
					archerGirl.removeArrow(arrow);// remove the arrow from active state
					score += 250;// add 250 to player score
					playerScore.setText(" " + score);// display updated score
				}// if (collision)
			}// for (targets)
			for (int j = 0; j < walls.size(); j++) {// loops through all Walls
				Wall wall = (Wall) walls.get(j);// creates duplicate of each Wall, necessary for deletion purposes
				Rectangle wallRec = new Rectangle((int)(wall.position[0]), (int)(wall.position[1]), wall.getWidth(), wall.getHeight());// gets rectangle enclosing Wall
				if (wallRec.intersects(sprite)) {// checks for wall/arrow collision
					archerGirl.removeArrow(arrow);// removes Arrow that collided with wall
				}// if (collision)
			}// for (walls)
			for (int j = 0; j < spiders.size(); j++) {// loops through all Spiders
				Spider spider = (Spider) spiders.get(j);// creates duplicate of each Spider, necessary for deletion purposes
				Rectangle spiderRec = new Rectangle((int)(spider.position[0]), (int)(spider.position[1]), spider.getWidth(), spider.getHeight());// gets rectangle enclosing Spider
				if (spiderRec.intersects(sprite)) {// checks for collisions
					spiders.remove(j);// remove the spider from an active state
					archerGirl.removeArrow(arrow);// remove the arrow from active state
					score += 50;// add 50 to player score
					playerScore.setText(" " + score);// display updated score
				}// if (success)	
			}// for (spiders)
		}// for (Arrows)
	}// checkArrows() method
	
	// Determines if ArcherGirl has been killed, if so game is over
	private void checkKilled() {
		Rectangle sprite = new Rectangle((int)(archerGirl.position[0]), (int)(archerGirl.position[1]), archerGirl.getWidth(), archerGirl.getHeight());// gets rectangle enclosing ArcherGirl
		// Loops through all active spiders to determine if any has reached ArcherGirl
		for (int i = 0; i < spiders.size(); i++) {// loops through all Spiders in Game world
			Rectangle spider = new Rectangle((int)(spiders.get(i).position[0]), (int)(spiders.get(i).position[1]), spiders.get(i).getWidth(), spiders.get(i).getHeight());// gets rectangle enclosing Spider
			// Checks to see if current instance of Spider intersects with ArcherGirl
			if (spider.intersects(sprite)) {// checks for intersection
				timer.stop();// stops timer and ends game
			}// if (killed)
		}// for(spiders)
	}// checkKilled() method
	
	// TAdapter nested class - handles key events, calls ArcherGirl methods
	private class TAdapter extends KeyAdapter {
		@Override // keyPressed method
		public void keyPressed(KeyEvent e) {
			archerGirl.keyPressed(e);// calls method in ArcherGirl to handle key presses
		}// keyPressed(KeyEvent) method
		@Override // keyReleased method
		public void keyReleased(KeyEvent e) {
			archerGirl.keyReleased(e);// calls method in ArcherGirl to handle key releases
		}// keyReleased(KeyEvent) method
	}// TAdapter class
}// Board class
