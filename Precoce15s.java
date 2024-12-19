package Moves;

import robocode.*;
import java.awt.*;

public class Precoce30sV2 extends AdvancedRobot {
    boolean peek;
    long lastTime = 0;
    boolean isBlinking = false;

    public void run() {
        setAdjustRadarForRobotTurn(true);
        peek = false;
//
  
		while (true) {
            // Efeito de piscar
            if (isBlinking) {
                setBodyColor(Color.red);
                setGunColor(Color.black);
                setRadarColor(Color.yellow);
            } else {
                setBodyColor(Color.blue);
                setGunColor(Color.white);
                setRadarColor(Color.red);
            }
            isBlinking = !isBlinking;
           
            execute();
        }
    }
		}
 
