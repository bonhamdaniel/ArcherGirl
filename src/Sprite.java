/*
 * Sprite class - represents a character in the Game world.
 */
import java.awt.Image;// imports library needed to create Sprite image
import java.awt.Rectangle;// imports library needed to use Rectangles
import java.util.ArrayList;// imports library needed to use ArrayLists
import javax.swing.ImageIcon;// imports library needed to create Sprite image

public class Sprite extends Kinematic {
	protected Image image;// represents the image of the Sprite
	protected int width;// represents the width of the Sprite
	protected int height;// represents the height of the Sprite
	protected boolean visible;// represents whether the Sprite is visible
	protected double maxAcceleration = 1.001;// represents the maximum acceleration of the Sprite
	protected int maxSpeed = 1;// represents the maximum speed  of the Sprite
	protected double maxRotation = .05;// represents the maximum rotation of the Sprite
	protected double maxAngularAcceleration = 0.01;// represents the maximum angular acceleration of the Sprite
	
	// Sprite constructor - requires position, orientation, velocity, rotation, and imageIcon data
	public Sprite(float[] position, double orientation, float[] velocity, double rotation, ImageIcon imageIcon) {
		super(position, orientation, velocity, rotation);// calls Kinematic constructor
		this.image = imageIcon.getImage();// sets image data for Sprite
		this.visible = true;// sets the Sprite to visible (active)
		this.width = image.getWidth(null);// sets the width of the Sprite as that of the image
		this.height = image.getHeight(null);// sets the height of the Sprite as that of the image
	}

	// getImage() - allows the image of the Sprite to be accessed
	public Image getImage() {
		return image;// returns the image of the Sprite
	}// getImage() method
	
	// getWidth method - allows the width of the Sprite to be accessed
	public int getWidth() {
		return image.getWidth(null);// returns the width of the Sprite
	}// getWidth() method
	
	// getHeight method - allows the height of the Sprite to be accessed
	public int getHeight() {
		return image.getHeight(null);// returns the height of the Sprite
	}// getHeight() method
	
	// isVisible method - allows the visibility of the Sprite to be accessed
	public boolean isVisible() {
		return visible;// returns the visibility of the Sprite
	}// isVisible() method
	
	// setVisible method - allows the visibility of the Sprite to be modified
	public void setVisible(boolean visible) {
		this.visible = visible;// sets the visibility of the Sprite to the parameter provided
	}// setVisible(boolean) method
	
	// noCollisions method - checks obstacles for collisions and returns the result
	public boolean noCollisions(ArrayList<Target> targets, ArrayList<Spider> spiders, ArrayList<Wall> walls) {
		boolean notTargets = noTargets(targets, velocity[0], velocity[1]);// call noTargets to check for Target collisions
		boolean notSpiders = noSpiders(spiders, velocity[0], velocity[1]);// call notSpiders to check for Spider collisions
		boolean notWalls = noWalls(walls, velocity[0], velocity[1]);// call notWalls to check for Wall collisions
		return notTargets && notSpiders && notWalls;// return result of collision check
	}// noCollisions(ArrayList<Target>, ArrayList<Spider>, ArrayList<Wall>) method
	
	// noCollisions method - checks obstacles for collisions and returns the result
	public boolean noCollisions(ArrayList<Target> targets, ArrayList<Spider> spiders, ArrayList<Wall> walls, float[] velocity) {
		boolean notTargets = noTargets(targets, velocity[0], velocity[1]);// call notTargets to check for Target collisions
		boolean notSpiders = noSpiders(spiders, velocity[0], velocity[1]);// call notSpiders to check for Spider collisions
		boolean notWalls = noWalls(walls, velocity[0], velocity[1]);// call notWalls to check for Wall collisions
		return notTargets && notSpiders && notWalls;// return result of collision check
	}// noCollisions(ArrayList<Target>, ArrayList<Spider>, ArrayList<Wall>, float[]) method
	
	// noTargets method - checks for potential collisions between Targets and a given Sprite
	public boolean noTargets(ArrayList<Target> targets, float dx, float dy) {
		boolean safe = true;// represents whether it is safe to proceed (no collision)
		Rectangle sprite = new Rectangle((int)(position[0] + dx), (int)(position[1] + dy), getWidth(), getHeight());// gets rectangle enclosing Sprite
		for (int i = 0; i < targets.size(); i++) {// loops through all the Targets in the Game world
			Rectangle target = new Rectangle((int)(targets.get(i).position[0]), (int)(targets.get(i).position[1]), targets.get(i).getWidth(), targets.get(i).getHeight());// gets rectangle enclosing Target
			if (target.intersects(sprite) && !targets.get(i).equals(this))// checks for collision
				return false;// returns false as a collision has been detected
		}// for (targets)
		return safe;// returns result
	}// noTargets(ArrayList<Target>, float, float) method
	
	// noSpiders method - checks for potential collisions between Spiders and a given Sprite
	public boolean noSpiders(ArrayList<Spider> spiders, float dx, float dy) {
		boolean safe = true;// represents whether it is safe to proceed (no collision)
		Rectangle sprite = new Rectangle((int)(position[0] + dx), (int)(position[1] + dy), getWidth(), getHeight());// gets rectangle enclosing Sprite
		for (int i = 0; i < spiders.size(); i++) {// loops through all the Spiders in the Game world
			Rectangle spider = new Rectangle((int)(spiders.get(i).position[0]), (int)(spiders.get(i).position[1]), spiders.get(i).getWidth(), spiders.get(i).getHeight());// gets rectangle enclosing Spider
			if (spider.intersects(sprite) && !spiders.get(i).equals(this))// checks for collision
				return false;// returns false as a collision has been detected
		}// for (spiders)
		return safe;// returns result
	}// noSpiders(ArrayList<Spider>, float, float) method
	
	// noWalls method - checks for potential collisions between Walls and a given Sprite
	public boolean noWalls(ArrayList<Wall> walls, float dx, float dy) {
		boolean safe = true;// represents whether it is safe to proceed (no collision)
		Rectangle sprite = new Rectangle((int)(position[0] + dx), (int)(position[1] + dy), getWidth(), getHeight());// gets rectangle enclosing Sprite
		for (int i = 0; i < walls.size(); i++) {// loops through all the Walls in the Game world
			Rectangle wall = new Rectangle((int)(walls.get(i).position[0]), (int)(walls.get(i).position[1]), walls.get(i).getWidth(), walls.get(i).getHeight());// gets rectangle enclosing Wall
			if (wall.intersects(sprite))// checks for collision
				return false;// returns false as a collision has been detected
		}// for (walls)
		return safe;// returns result
	}// noWalls(ArrayList<Wall>, float, float) method
	
	// noWalls(ArrayList<Wall>, float[]) method - checks for potential collisions with Walls and a given Sprite
	public float[] noWalls(ArrayList<Wall> walls, float[] velocity) {
		Rectangle sprite = new Rectangle((int)(position[0] + velocity[0]), (int)(position[1] + velocity[1]), getWidth(), getHeight());// gets rectangle enclosing Sprite
		for (int i = 0; i < walls.size(); i++) {// loops through all the Walls in the Game world
			Rectangle wall = new Rectangle((int)(walls.get(i).position[0]), (int)(walls.get(i).position[1]), walls.get(i).getWidth(), walls.get(i).getHeight());// gets rectangle enclosing Wall
			if (wall.intersects(sprite)) {// if potential collision detected
				Rectangle temp = wall.intersection(sprite);// gets intersection rectangle of potential collision
				
				float[] center = new float[2];// represents the center point of the collision rectangle
				center[0] = (float)temp.getCenterX();// sets the x-coord of the center point of the collision rectangle
				center[1] = (float)temp.getCenterY();// sets the y-coord of the center point of the collision rectangle
				
				float[] upperLeft = new float[2];// represents the upper left point of the collision rectangle
				upperLeft[0] = (float)temp.x;// sets the x-coord of the upper left point of the collision rectangle
				upperLeft[1] = (float)temp.y;// sets the y-coord of the upper left point of the collision rectangle
				
				float[] bottomRight = new float[2];// represents the bottom right point of the collision rectangle
				bottomRight[0] = (float)center[0] + center[0] - upperLeft[0];// sets the x-coord of the bottom right point of the collision rectangle
				bottomRight[1] = (float)center[1] + center[1] - upperLeft[1];// sets the y-coord of the bottom right point of the collision rectangle
				
				float[] bottomLeft = new float[2];// represents the bottom left point of the collision rectangle
				bottomLeft[0] = (float)center[0] - (center[0] - upperLeft[0]);// sets the x-coord of the upper left point of the collision rectangle
				bottomLeft[1] = (float)center[1] + center[1] - upperLeft[1];// sets the y-coord of the upper left point of the collision rectangle
				
				float[] upperRight = new float[2];// represents the upper right point of the collision rectangle
				upperRight[0] = (float)center[0] + (center[0] - upperLeft[0]);// sets the x-coord of the upper right point of the collision rectangle
				upperRight[1] = (float)center[1] - (center[1] - upperLeft[1]);// sets the y-coord of the upper right point of the collision rectangle
				
				float[] verticalNormal = new float[2];// represents  the normal of the potential vertical collision plane
				float[] horizontalNormal = new float[2];// represents  the normal of the potential horizontal collision plane
				float[] verticalIntersection = new float[2];// represents potential vertical collision point
				float[] horizontalIntersection = new float[2];// represents potential horizontal collision point
				long verticalLength = 0;// represents the length from the Sprite to the potential vertical collision point
				long horizontalLength = 0;// represents the length from the Sprite to the potential horizontal collision point
				
				// Determines whether the character is on the left or right of the object they collided with
				if (MovementMath.length(position, upperLeft) < MovementMath.length(position, upperRight)) {// on left of collision
					verticalNormal = MovementMath.normalize(new float[] {bottomLeft[0] - upperLeft[0], bottomLeft[1] - upperLeft[1]});// normalizes left potential collision plane
					verticalIntersection = new float[] {bottomLeft[0], center[1]};// potential collision spot on left of object
					verticalLength = MovementMath.length(position, verticalIntersection);// gets length from potential collision point on left plane
				} else {// on right of collision
					verticalNormal = MovementMath.normalize(new float[] {bottomRight[0] - upperRight[0], upperRight[1] - upperRight[1]});// normalizes right potential collision plane
					verticalIntersection = new float[] {bottomRight[0], center[1]};// potential collision spot on right of object
					verticalLength = MovementMath.length(position, verticalIntersection);// gets length from potential collision point on right plane
				}// if collision(with left)
					
				// Determines whether the character is on the top or bottom of the object they collided with
				if (MovementMath.length(position, upperRight) < MovementMath.length(position, bottomRight)) {// on top of collision
					horizontalIntersection = new float[] {center[0], upperRight[1]};// potential collision spot on top of object
					horizontalNormal = MovementMath.normalize(new float[] {upperRight[0] - upperLeft[0], upperRight[1] - upperLeft[1]});// normalizes top potential collision plane
					horizontalLength = MovementMath.length(position, horizontalIntersection);// gets length from potential collision point on top plane
				} else {// on bottom of plane
					horizontalNormal = MovementMath.normalize(new float[] {bottomRight[0] - bottomLeft[0], bottomRight[1] - bottomLeft[1]});// normalizes bottom potential collision plane
					horizontalIntersection = new float[] {center[0], bottomRight[1]};// potential collision spot on bottom of object
					horizontalLength = MovementMath.length(position, horizontalIntersection);// gets length from potential collision point on bottom plane
				}// if (collision with top)
				
				// Determines closest point of collision and sets data accordingly
				float[] result = new float[4];// holds collision results
				if (verticalLength < horizontalLength) {// if vertical collision
					result[0] = horizontalIntersection[0];// x-coord of collision point as horizontal value (wall)
					result[1] = verticalIntersection[1];// y-coord of collision point as horizontal value (corner)
					result[2] = verticalNormal[0];// x-coord of wall normal 
					result[3] = verticalNormal[1];// y-coord of wall normal 
				} else {// if horizontal collision
					result[0] = horizontalIntersection[0];// x-coord of collision point as horizontal value (corner)
					result[1] = verticalIntersection[1];// y-coord of collision point as horizontal value (wall)
					result[2] = horizontalNormal[0];// x-coord of wall normal 
					result[3] = horizontalNormal[1];// y-coord of wall normal 
				}// if(vertical collision)
				return result;// returns collision data
			}// if (collision)
		}// for(walls)
		return new float[1];// return if no collision occurs
	}// noWalls(ArrayList<Wall>, float) method
}// Sprite class
