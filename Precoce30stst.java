package Moves;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import java.awt.*;

public class Precoce30s extends AdvancedRobot {
    double wallMargin = 50; // Margem de segurança para evitar colisões com a parede

    public void run() {
        // Configurações visuais do robô
        setBodyColor(Color.black);
        setGunColor(Color.black);
        setRadarColor(Color.orange);
        setBulletColor(Color.cyan);
        setScanColor(Color.cyan);

        // Loop principal
        while (true) {
            moveAlongBorders();
        }
    }

    public void moveAlongBorders() {
        double x = getX(); // Posição X atual
        double y = getY(); // Posição Y atual

        double battlefieldWidth = getBattleFieldWidth();
        double battlefieldHeight = getBattleFieldHeight();

        // Detecta proximidade com as bordas e ajusta o movimento
        if (x <= wallMargin) { // Próximo à borda esquerda
            turnTo(90); // Virar para baixo
        } else if (x >= battlefieldWidth - wallMargin) { // Próximo à borda direita
            turnTo(270); // Virar para cima
        } else if (y <= wallMargin) { // Próximo à borda inferior
            turnTo(0); // Virar para direita
        } else if (y >= battlefieldHeight - wallMargin) { // Próximo à borda superior
            turnTo(180); // Virar para esquerda
        }

        // Move para frente mantendo a lógica de bordas
        ahead(100);
    }

    public void turnTo(double angle) {
        // Gira suavemente para o ângulo especificado
        double angleToTurn = angle - getHeading();
        if (angleToTurn <= -180) {
            angleToTurn += 360;
        } else if (angleToTurn > 180) {
            angleToTurn -= 360;
        }
        turnRight(angleToTurn);
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // Atira nos robôs detectados
        fire(2); // Disparo com potência 2
        scan();  // Mantém o radar ativo
    }

    public void onHitRobot(HitRobotEvent e) {
        // Reage ao colidir com outro robô
        back(50); // Recuar para evitar colisões prolongadas
    }
}
