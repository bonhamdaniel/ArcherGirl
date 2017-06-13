/*
 * Steering class - represents steering attributes.
 */
import java.util.ArrayList;// imports library needed to use ArrayLists

public class Steering {
	protected float[] linear;// stores the current linear steering data
	protected double angular;// stores the current angular steering data
	float[] target;// stores the current target of steering
	protected float wanderOrientation = ((float)Math.random() * 600);// stores the wander orientation when wander steering is being used
	protected int stuck = 0;
	
	// Steering constructor - no parameters
	public Steering() {
		this.linear = new float[2];// initializes linear array
		this.angular = 0.0;// sets initial angular data
		target = new float[] {300, 300};// sets initial target to middle of game space
	}// Steering() constructor
	
	// seek method - implements seek steering based on given target
	public Steering seek(float[] target, Sprite character) {
		Steering temp = new Steering();// new Steering instance to hold results of seek
		temp.linear[0] = target[0] - character.position[0];// sets x-coord of linear data
		temp.linear[1] = target[1] - character.position[1];// sets y-coord of linear data
		
		MovementMath.normalize(temp.linear);// normalizes linear data
		
		temp.linear[0] *= character.maxAcceleration;// multiplies x-coord of linear data by maxAcceleration
		temp.linear[1] *= character.maxAcceleration;// multiplies x-coord of linear data by maxAcceleration
		
		temp.angular = 0;// sets angular data to 0
		
		return temp;// returns results of seek Steering
	}// seek(float[], Sprite) method
	
	// flee method- implements flee steering based on given target
	public Steering flee(float[] target, Sprite character) {
		Steering temp = new Steering();// new Steering instance to hold results of flee
		temp.linear[0] = character.position[0] - target[0];// sets x-coord of linear data
		temp.linear[1] = character.position[1] - target[1];// sets y-coord of linear data
		
		MovementMath.normalize(temp.linear);// normalizes linear data
		
		temp.linear[0] *= character.maxAcceleration;// multiplies x-coord of linear data by maxAcceleration
		temp.linear[1] *= character.maxAcceleration;// multiplies x-coord of linear data by maxAcceleration
		
		temp.angular = 0;// sets angular data to 0
		
		return temp;// returns results of flee Steering
	}// flee(float[], Sprite) method
	
	// wander method - implements wander steering
	public Steering wander(Sprite character) {
		Steering temp = new Steering();// new Steering instance to hold results of flee
		
		wanderOrientation += MovementMath.randomBinomial() * 8 * Math.PI;// increments wanderOrientation by random amount
		
		double targetOrientation = wanderOrientation + character.orientation;// sets the targetOrientation based on current orientation and wanderOrientation
		
		// sets the target data for wander based on current position and orientation plus arbitrary amount
		float[] target = {character.position[0] + 300 * getOrientationAsPosition(character.orientation)[0], character.position[1] + 300 * getOrientationAsPosition(character.orientation)[1]};
		
		target[0] += 50 * getOrientationAsPosition(targetOrientation)[0];// adds orientation amount to target x-coord
		target[1] += 50 * getOrientationAsPosition(targetOrientation)[1];// adds orientation amount to target y-coord
		
		temp.linear = getOrientationAsPosition(character.orientation);// sets temp linear to orientation position
		temp.linear[0] *= character.maxAcceleration;// multiplies x-coord of linear by maxAcceleration
		temp.linear[1] *= character.maxAcceleration;// multiplies y-coord of linear by maxAcceleration
		
		temp.target[0] = target[0] - character.position[0];// sets x-coord of temp target as target - position
		temp.target[1] = target[1] - character.position[1];// sets y-coord of temp target as target - position
		
		//float[] alignTarget;// used in align steering
		//double alignOrientation = Math.atan2(-temp.target[0],  temp.target[1]);// used in align steering
		
		double rotation = targetOrientation - character.orientation;// used to generate ultimate rotation result
		rotation %= Math.PI;// trims rotation from target - character
		double rotationSize = Math.abs(rotation);// gets absolute value of the rotation
		double targetRotation = character.maxRotation;// gets rotation of character as targetRotation
		targetRotation *= rotation / rotationSize;// multiplies targetRotation by rotation divided by rotationSize
		temp.angular = targetRotation - character.rotation;// sets temp angular to target minus character
		
		return temp;// returns temp data
	}// wander(Sprite) method
	
	// obstacleAvoidance - implements obstacle avoidance steering based on obstacles in the game world
	public Steering obstacleAvoidance(ArcherGirl archerGirl, Sprite character, ArrayList<Wall> walls) {
		Steering temp = new Steering();// new Steering instance to hold results of obstacleAvoidance
		int avoidDistance = 32;// determines the distance to avoid when setting target after a collision
		int lookahead = 32;// determines lookahead amount of the algorithm
		
		float[] rayVector = character.velocity;// sets rayVector as character velocity
		
		rayVector = MovementMath.normalize(rayVector);// normalize the rayVector data
		rayVector[0] *= lookahead;// multiplies x-coord of rayVector by lookahead amount
		rayVector[1] *= lookahead;// multiplies y-coord of rayVector by lookahead amount
		
		float[] collisionData = character.noWalls(walls, rayVector);// determines whether a collision has occurred and gets results
		
		// Determines if a collision has occurred and sets data accordingly
		if (collisionData.length < 2) {// no collision
			temp.target[0] = character.target[0];// sets temp target x-coord as target based point
			temp.target[1] = character.target[1];// sets temp target y-coord as target based point
			temp.linear[0] = character.target[0] - character.position[0];// sets temp linear x-coord as target based point
			temp.linear[1] = character.target[1] - character.position[1];// sets temp linear y-coord as target based point
			temp.stuck = 10;
		} else if (collisionData.length > 2) {// if collision
			temp.target[0] = collisionData[0] + collisionData[2] * avoidDistance;// sets temp target x-coord as collision based point
			temp.target[1] = collisionData[1] + collisionData[3] * avoidDistance;// sets temp target y-coord as collision based point
			temp.linear[0] = collisionData[0] + collisionData[2] * avoidDistance;// sets temp linear x-coord as collision based point
			temp.linear[1] = collisionData[1] + collisionData[3] * avoidDistance;// sets temp linear y-coord as collision based point
		}// if (collision)
		
		temp.angular = 0;// sets temp angular to zero
		
		return temp;// return results of steering
	}// obstacleAvoidance(ArcherGirl, Sprite, ArrayList)
	
	// lookWhereYoureGoing method - sets a characters orientation based on their movement direction if they are moving
	public void lookWhereYoureGoing(Sprite character) {
		// Determined if character is moving
		if (MovementMath.length(character.velocity) > 0) {
			character.orientation = Math.atan2(-character.velocity[0], character.velocity[1]);// sets orientation to the direction character is moving
		}// if (velocity > 0)
	}// lookWhereYoureGoing (Sprite) method
	
	// getOrientationAsPosition method - calculates and returns a position based on orientation
	public float[] getOrientationAsPosition(double orientation) {
		return new float[] {(float)-Math.sin(orientation), (float)Math.cos(orientation)};// return position based on orientation
	}// getOrientationAsPosition(double) method
}// Steering class
