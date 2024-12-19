package Moves;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import java.awt.*;

public class Precoce30s extends AdvancedRobot {
    boolean peek; 
    double moveAmount;

    public void run() {
        // Configurações visuais do robô
        setBodyColor(Color.black);
        setGunColor(Color.black);
        setRadarColor(Color.orange);
        setBulletColor(Color.cyan);
        setScanColor(Color.cyan);

        // Calcula o movimento máximo para atravessar o mapa
        moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
        peek = false;

        // Alinha-se para andar pela borda
        turnLeft(getHeading() % 90); // Alinha com o eixo
        ahead(moveAmount);           // Move até a borda
        turnRight(90);               // Prepara para contornar a borda

        while (true) {
            moveAlongWall();
        }
    }

    public void moveAlongWall() {
        // Move-se pela borda do mapa
        ahead(moveAmount);           // Move uma grande distância
        turnRight(90);               // Faz curva de 90 graus para contornar a borda
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // Atira nos robôs detectados
        fire(2); // Disparo com potência 2
        scan();  // Mantém o radar ativo
    }

    public void onHitRobot(HitRobotEvent e) {
        // Reage ao colidir com outro robô
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            back(100);  // Recuar se estiver à frente
        } else {
            ahead(100); // Avançar se estiver atrás
        }
    }
}
