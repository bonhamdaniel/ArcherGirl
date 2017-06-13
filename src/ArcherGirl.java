/*
 * ArcherGirl class - represents the user-controlled, main character of the game.
 */
import java.awt.event.KeyEvent;// imports library needed to handle KeyEvents - user input
import java.util.ArrayList;// imports library needed to hold Arrow instances
import javax.swing.ImageIcon;// imports library needed to create ArcherGirl image in game

public class ArcherGirl extends Sprite {
	private ArrayList<Arrow> arrows;// represents the Arrows shot by ArcherGirl
	
	// ArcherGirl constructor - no parameters necessary
	public ArcherGirl() {
		super(new float[] {286, 286}, 0.0, new float[] {0,0}, 0.0, new ImageIcon("archer.png"));// class Sprite constructor
		arrows = new ArrayList<Arrow>();// initializes ArcherGirl's Arrow collection
	}// ArcherGirl() constructor
	
	// keyPressed method - handles KeyEvents (user input)
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();// gets KeyCode of the key that was pressed by user
		
		// Determines how to move ArcherGirl or if an Arrow needs to be shot
		if (key == KeyEvent.VK_SPACE) {
			shoot();// calls shoot() method if space bar was pressed
		} else if (key == KeyEvent.VK_LEFT) {
			velocity[0] = -1;// changes x-coord velocity to that of a left turn
			orientation = -Math.PI / 2;// changes orientation to represent a left turn
		} else if (key == KeyEvent.VK_RIGHT) {
			velocity[0] = 1;// changes x-coord velocity to that of a right turn
			orientation = Math.PI / 2;// changes orientation to represent a right turn
		} else if (key == KeyEvent.VK_UP) {
			velocity[1] = -1;// changes y-coord velocity to that of a move upwards on screen
			orientation = 0;// changes orientation to represent a move upwards
		} else if (key == KeyEvent.VK_DOWN) {
			velocity[1] = 1;// changes y-coord velocity to that of a move downwards on screen
			orientation = -Math.PI;// changes orientation to represent a move downwards
		}// if (KeyEvent)
	}// keyPressed(KeyEvent) method
	
	// keyReleased method - handles when a key is released by the user
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();// gets KeyCode of the key that was pressed by user
		
		// Determines which velocity direction to reset based on key released
		if (key == KeyEvent.VK_LEFT) {
			velocity[0] = 0;// resets x-coord velocity to that of standing still
		} else if (key == KeyEvent.VK_RIGHT) {
			velocity[0] = 0;// resets x-coord velocity to that of standing still
		} else if (key == KeyEvent.VK_UP) {
			velocity[1] = 0;// resets y-coord velocity to that of standing still
		} else if (key == KeyEvent.VK_DOWN) {
			velocity[1] = 0;// resets y-coord velocity to that of standing still
		}// if (KeyEvent)
	}// keyReleased(KeyEvent) method
	
	// Determines if the suggested movement is legal and calls update() in Kinematic if so
	public void move(ArrayList<Target> targets, ArrayList<Spider> spiders, ArrayList<Wall> walls) {
		boolean noCollisions = noCollisions(targets,spiders, walls);// Determines whether suggested movement would cause collision
		
		// Determines whether suggested movement is within game boundaries
		if ((position[0] + velocity[0] ) < 574 && (position[0] + velocity[0] ) > 0 &&
				(position[1] + velocity[1] ) < 574 && (position[1] + velocity[1] ) > 0 && noCollisions)
			update(velocity[0], velocity[1]);// calls update() in /kinematic if within game boundaries and has no collision
	}// move(ArrayList<Target>, ArrayList<Spider>, ArrayList<Wall>) method
	
	// getArrows method - returns ArcherGirl's active Arrows
	public ArrayList<Arrow> getArrows() {
		return arrows;// return active Arrows
	}// getArrows() method
	
	
	// getArrow method - returns specific Arrow from ArcherGirl's collection
	public Object getArrow(int i) {
		return arrows.get(i);// returns specific Arrow
	}// getArrow(int) method
	
	// removeArrow method - allows and Arrow to be removed from ArcherGirl's collection
	public void removeArrow(Arrow arrow) {
		arrows.remove(arrow);// removes specific Arrow
	}// removeArrow(Arrow) method
	
	// shoot method - handles when ArcherGirl shoots a new Arrow
	public void shoot() {
		// Handles creating a new Arrow with corresponding position, velocity, and orientation
		if (orientation == 0)// when ArcherGirl facing up
			arrows.add(new Arrow(new float[] {position[0] + 14, position[1] - 23}, new float[] {0, -1}, orientation));
		if (orientation > (-Math.PI - 1) && orientation < (-Math.PI + 1))// when ArcherGirl facing down
			arrows.add(new Arrow(new float[] {position[0] + 14, position[1] + 25}, new float[] {0, 1}, orientation));
		if (orientation > (-Math.PI / 2 - 1) && orientation < (-Math.PI / 2 + 1))// when ArcherGirl facing left
			arrows.add(new Arrow(new float[] {position[0] - 8, position[1] + 2}, new float[] {-1, 0}, orientation));
		if (orientation > (Math.PI / 2 - 1) && orientation < (Math.PI / 2 + 1))// when ArcherGirl facing right
			arrows.add(new Arrow(new float[] {position[0] + 36, position[1] + 2}, new float[] {1, 0}, orientation));
	}// shoot() method
}// ArcherGirl class
