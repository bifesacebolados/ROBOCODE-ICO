package Moves;

import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.HitRobotEvent;
import java.awt.Color;

public class Precoce30s extends Robot {
    public void run() {
        // Definir as cores do robô
        setBodyColor(Color.black);
        setGunColor(Color.black);
        setRadarColor(Color.orange);
        setBulletColor(Color.cyan);
        setScanColor(Color.cyan);

        // Movimento básico
        ahead(100);  // Move o robô para frente 100 unidades
    }

    // Método para lidar com robôs escaneados
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(2);  // Dispara com potência 2 ao detectar um robô
    }

    // Método para lidar com a colisão com outro robô
    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            back(100);  // Se o robô colidir pela frente, recua 100 unidades
        } else {
            ahead(100);  // Caso contrário, avança 100 unidades
        }
    }
}
