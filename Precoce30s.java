package Moves;

import robocode.Robot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import java.awt.*;

public class Precoce30s extends Robot {
    boolean peek; 
    double moveAmount;
    long lastTime = 0;

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
            if (getTime() - lastTime >= 30) {
                lastTime = getTime(); // Atualiza o tempo da última mudança
                ahead(150);  // Agressivo
                fire(3);     // Agressivo
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
        } else {
            ahead(100);
        }
    }
}

