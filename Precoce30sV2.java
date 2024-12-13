package Moves;

import robocode.*;
import java.awt.*;

public class Precoce30sV2 extends AdvancedRobot {
    boolean peek;
    double moveAmount;
    long lastTime = 0;
    boolean isBlinking = false;
    long lastColorChange = 0;
    int direction = 1;
    long lastWallMove = 0;

    public void run() {
        setAdjustRadarForRobotTurn(true);
        setAdjustGunForRobotTurn(true);
        moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
        peek = false;

  
		while (true) {
            // Efeito de piscar
            if (isBlinking) {
                setBodyColor(Color.red);
                setGunColor(Color.red);
                setRadarColor(Color.red);
            } else {
                setBodyColor(Color.blue);
                setGunColor(Color.blue);
                setRadarColor(Color.blue);
            }
            isBlinking = !isBlinking;
            moveToOppositeWall();
            execute();
        }
    }

    public void onScanner() {
        setTurnRadarRight(360);
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
       

        if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10) {
            setFire(Math.min(400 / e.getDistance(), 3));
        }

        if (e.getEnergy() < getEnergy()) {
            setAhead((e.getDistance() - 140) * direction);
        } else {
            setBack((e.getDistance() - 130) * direction);
        }

        avoidWalls();
    }

    public void avoidWalls() {
        double wallMargin = 50;

        if (getX() <= wallMargin || getX() >= getBattleFieldWidth() - wallMargin ||
            getY() <= wallMargin || getY() >= getBattleFieldHeight() - wallMargin) {

            direction = -direction;

         
    }



   
    }

    public void setAllColors(Color c) {
        setBodyColor(c);
        setGunColor(c);
        setRadarColor(c);
        setBulletColor(c);
        setScanColor(c);
    }

    public void moveToOppositeWall() {
        long currentTime = getTime();
        if (currentTime - lastWallMove >= 15) {
            double targetX, targetY;
            if (getX() < getBattleFieldWidth() / 2) {
                targetX = getBattleFieldWidth() - 50;
            } else {
                targetX = 50;
            }
            if (getY() < getBattleFieldHeight() / 2) {
                targetY = getBattleFieldHeight() - 50;
            } else {
                targetY = 50;
        }
		}
		}
		}
 
