package Moves;

import robocode.Robot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import java.awt.*;

public class Precoce30s extends Robot {
    boolean peek; 
    double moveAmount;
    boolean surrounded = false; // Verifica se está encurralado

    public void run() {
        setBodyColor(Color.black);
        setGunColor(Color.black);
        setRadarColor(Color.orange);
        setBulletColor(Color.cyan);
        setScanColor(Color.cyan);
        
        moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
        peek = false;

        turnLeft(getHeading() % 90);
        ahead(moveAmount);
        peek = true;
        turnGunRight(90);
        turnRight(90);

        while (true) {
            if (surrounded) {
                fire(3); // Disparo com potência 3 quando encurralado
            }
            peek = true;
            ahead(moveAmount);
            peek = false;
            turnRight(90);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        fire(2); // Disparo com potência 2
        if (peek) {
            scan();
        }
    }

    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            back(100);
            surrounded = true; // Marcar como encurralado
        } else {
            ahead(100);
            surrounded = true; // Marcar como encurralado
        }
    }
}

