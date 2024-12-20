package Moves;

import robocode.Robot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import java.awt.*;

public class Precoce15s extends Robot {
    boolean peek;
    double moveAmount;
    long lastTime = 0;

    public void run() {
        moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
        peek = false;

        // Configuração inicial de movimentação
        turnLeft(getHeading() % 90);
        ahead(moveAmount);
        peek = true;
        turnGunRight(90);
        turnRight(90);

        while (true) {
            // Pisca as cores do robô
            setBodyColor(new Color((int) (Math.random() * 0x1000000)));
            setGunColor(new Color((int) (Math.random() * 0x1000000)));
            setRadarColor(new Color((int) (Math.random() * 0x1000000)));
            setBulletColor(new Color((int) (Math.random() * 0x1000000)));
            setScanColor(new Color((int) (Math.random() * 0x1000000)));

            if (getTime() - lastTime >= 30) {
                lastTime = getTime();
                ahead(150); // Movimento agressivo
                fire(3);    // Disparo agressivo enquanto se movimenta
                turnGunRight(360); // Procura alvos
            }

            peek = true;
            ahead(moveAmount);
            peek = false;
            turnRight(90);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double distance = e.getDistance();
        double enemyBearing = getHeading() + e.getBearing();
        double enemyHeading = e.getHeading();
        double enemyVelocity = e.getVelocity();

        // Calcula a posição futura do inimigo
        double bulletPower = calculateBulletPower(distance);
        double bulletSpeed = 20 - 3 * bulletPower; // Fórmula para velocidade da bala
        double timeToImpact = distance / bulletSpeed;

        double futureX = getX() + e.getDistance() * Math.sin(Math.toRadians(enemyBearing)) 
                         + enemyVelocity * timeToImpact * Math.sin(Math.toRadians(enemyHeading));
        double futureY = getY() + e.getDistance() * Math.cos(Math.toRadians(enemyBearing)) 
                         + enemyVelocity * timeToImpact * Math.cos(Math.toRadians(enemyHeading));

        double angleToFuture = Math.toDegrees(Math.atan2(futureX - getX(), futureY - getY()));

        // Ajusta o canhão para mirar na posição futura
        double gunTurn = normalizeAngle(angleToFuture - getGunHeading());
        turnGunRight(gunTurn);

        // Dispara baseado na distância
        fire(bulletPower);

        if (peek) {
            scan();
        }
    }

    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            fire(3); // Disparo com potência máxima ao colidir frontalmente
            back(100);
        } else {
            ahead(100);
        }
    }

    private double calculateBulletPower(double distance) {
        if (distance < 100) {
            return 3; // Potência máxima se estiver perto
        } else if (distance < 300) {
            return 2; // Potência intermediária se estiver a uma distância média
        } else {
            return 1; // Potência baixa se estiver longe
        }
    }

    private double normalizeAngle(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }
}
