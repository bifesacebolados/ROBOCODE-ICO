package Moves;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import java.awt.*;

public class Precoce30s extends AdvancedRobot {
    double wallMargin = 50; // Distância mínima das paredes

    public void run() {
        // Configurações visuais
        setBodyColor(Color.black);
        setGunColor(Color.black);
        setRadarColor(Color.orange);
        setBulletColor(Color.cyan);
        setScanColor(Color.cyan);

        // Ajuste inicial para alinhar com o contorno
        goToWall();
        turnRight(90); // Prepara para contornar as bordas

        // Loop principal
        while (true) {
            followWall();
        }
    }

    private void goToWall() {
        // Move-se para a borda mais próxima
        double heading = getHeading();
        if (heading != 0) {
            turnLeft(heading % 90); // Ajusta para alinhar com os eixos
        }
        ahead(Math.max(getBattleFieldWidth(), getBattleFieldHeight())); // Move-se até uma parede
    }

    private void followWall() {
        // Movimento para contornar as bordas do mapa
        double x = getX();
        double y = getY();
        double battlefieldWidth = getBattleFieldWidth();
        double battlefieldHeight = getBattleFieldHeight();

        if (x <= wallMargin && getHeading() != 90) {
            // Perto da parede esquerda, vira para baixo
            turnTo(90);
        } else if (x >= battlefieldWidth - wallMargin && getHea

