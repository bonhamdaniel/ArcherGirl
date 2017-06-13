# ArcherGirl
Simple Java game demonstrating steering behaviours.

- Description: 		Action Game
   - A simple 2D action game where the user controls the main character, Archer Girl, whose goal is to shoot as many targets as possible, while avoiding the enemy, spiders.  Both the targets and the spiders appear at random positions in the game space, at set intervals of time.  Archer Girl has her trusty bow with her, and an unlimited set of arrows, with which she can shoot both the targets and the spiders.  Points are accrued for each entity shot, with targets being worth more points than spiders.
- Usage Instructions:	You are the main character, Archer Girl
  - The goal of the game is to accrue the maximum amount of points that you can, achieved by shooting as many Targets (250 points) and Spiders (50 points) as you can.  This must be achieved will staying away from the chasing Spiders, who kill you by touch.
  - Archer Girl is controlled by key presses:
    - ←		Move left
    - →		Move right
    - ↑		Move up
    - ↓		Move down
    - <space bar>	Shoot arrow
- Compilation:	javac ArcherGirl.java Arrow.java Board.java Game.java Kinematic.java MovementMath.java Spider.java Sprite.java Steering.java Target.java Wall.java
- Execution:		java Game
- Steering Behaviours:	Wander, Seek, Flee, Obstacle Avoidance, and Look Where You’re Going
  - Each of these behaviours is hardcoded into the Archer Girl Game.  At execution, the behaviours will be active at appropriate times, depending on the current circumstances.  The user does not have to do anything to cause them to happen.  The Spider and Target characters use a form of priority steering, which governs the implementation of the steering behaviours as follows:
    - The Spiders are initially set to Wander when created.  This steering behaviour continues until it is interrupted by the Spider becoming too close to either the Archer Girl character or a Wall.  If the Spider gets too close to the Archer Girl, a value specified in the code that governs the Spider’s movement, its steering behaviour is set to Seek; while if it gets too close to a Wall, a value specified in the code for the Obstacle Avoidance implementation, its steering behaviour is set to Obstacle Avoidance.  At all times, the Spider is implementing the Look Where You’re Going behaviour.
    - The Targets are initially set to Wander when created.  This steering behaviour continues until it is interrupted by the Target becoming too close to either the Archer Girl character or a Wall.  If the Target gets too close to the Archer Girl, a value specified in the code that governs the Target’s movement, its steering behaviour is set to Flee; while if it gets too close to a Wall, a value specified in the code for the Obstacle Avoidance implementation, its steering behaviour is set to Obstacle Avoidance.  The Target does not need to implement the Look Where You’re Going behaviour, as its image does not face any particular direction.
    - Archer Girl and her Arrows follow a simpler movement protocols.  Archer Girl’s position is simply altered by one in whichever direction the key press indicates, with her orientation being changed in the method that handles the key press.  She is not allowed to make a movement that will result in her colliding with any object or entity in the game world.  The Arrow positions are simply altered by one in the direction that they are shot.  If they collide with a Wall, they disappear and are no longer active; if they collide with a Target or Spider they register points, then disappear and are no longer active.
- Bugs/Problems:		Collisions, Choppy movements, and Obstacle Avoidance issues
  - Collisions – there is no steering behaviour implemented to stop Spiders and Targets from running into each other and getting stuck.  If too many of these characters are on the game board they quickly find each other and become stuck in their position.
  - Choppy movements – when a Spider or Target transitions from one steering behaviour to another, there are certain situations that cause them to get stuck going back and forth between the behaviours.  This causes them to quickly turn back and forth from one orientation to another, resulting in very unpleasant visual effects.
  - Obstacle Avoidance issues – the algorithm implemented for the obstacle avoidance does not always lead to correct results.  There are situations where the correction algorithm does not work and the character gets stuck on the wall.  It seems to be a matter of precision. 
