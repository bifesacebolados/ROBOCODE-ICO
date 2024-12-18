package TENORIO;
import robocode.*;
// import java.awt.Color; // Uncomment if you want to set colors

public class TERRIFY_primeiro extends Robot {

    
    public void run() {
        // Main robot loop
        while (true) {
            ahead(70);            // Move ahead 70 units
            setTurnRight(45);     // Turn right 45 degrees
            setTurnLeft(180);     // Turn left 180 degrees
            setBack(100);         // Move back 100 units

            // The robot will continue repeating these actions in the loop
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
   
    public void onScannedRobot(ScannedRobotEvent e) {
        // Fire a bullet with power 1 when another robot is scanned
        fire(1);
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
   
    public void onHitByBullet(HitByBulletEvent e) {
        // Move back 10 units if hit by a bullet
        back(10);
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
   
    public void onHitWall(HitWallEvent e) {
        // Move back 20 units if you hit a wall
        back(20);
    }
}

