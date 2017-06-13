/*
 * MovementMath class - contains functions to calculate equations necessary for the
 * 						Archer Girl game.
 */
public class MovementMath {
	// Determines and returns the length of a velocity given a point.
	public static long length(float[] point) {
		return Math.round(Math.sqrt(point[0]*point[0] + point[1]*point[1]));
	}// length(float[] point) method
	
	// Determines and returns the length of a line consisting of two given points.
		public static long length(float[] point1, float[] point2) {
			return Math.round(Math.sqrt((point1[0]-point2[0])*(point1[0]-point2[0]) + (point1[1]-point2[1])*(point1[1]-point2[1])));
		}// length(float[] point) method
		
	// Determines and returns the normalized version of a velocity
	public static float[] normalize(float[] point) {
		float divisor = Math.abs(point[0]) + Math.abs(point[1]);// gets magnitude
		float dx = (point[0] / (divisor));// determines normalized x
		float dy = (point[1] / (divisor));// determines normalized y
		return new float[] {dx, dy};// returns normalized velocity
	}// normalize(float[] point) method
	
	// Determines and returns a random double between -1.0 and 1.0
	public static double randomBinomial() {
		return Math.random() - Math.random();
	}// randomBinomial() method
}// MovementMath class
