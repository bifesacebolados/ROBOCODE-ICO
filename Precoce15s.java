package Moves;

import robocode.Robot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import java.awt.*;

public class Precoce15s extends Robot {
    boolean peek; 
    double moveAmount;
    long lastAggressiveTime = 0; // Para armazenar o tempo da última ação agressiva

    public void run() {
        // Definir cores do robô
        setBodyColor(Color.black);
        setGunColor(Color.black);
        setRadarColor(Color.orange);
        setBulletColor(Color.cyan);
        setScanColor(Color.cyan);

        // Calcular a quantidade de movimento máxima possível
        moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
        peek = false;

        // Alinhar à borda inicial
        turnLeft(getHeading() % 90);
        ahead(moveAmount);
        peek = true;
        turnGunRight(90);
        turnRight(90);

        // Loop principal de execução
        while (true) {
            if (getTime() - lastAggressiveTime >= 30) {
                lastAggressiveTime = getTime();
                executeAggressiveMove();
            }
            peek = true;
            ahead(moveAmount);
            peek = false;
            turnRight(90);
        }
    }

    private void executeAggressiveMove() {
        ahead(150);  // Move-se para frente agressivamente
        fire(3);     // Dispara com potência máxima permitida
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        fire(2); // Disparo padrão ao avistar um robô
        if (peek) {
            scan(); // Continua escaneando se o robô estiver em "peek"
        }
    }

    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            back(100); // Recuar se colidir de frente
        } else {
            ahead(100); // Avance se colidir de costas
        }
    }
}
