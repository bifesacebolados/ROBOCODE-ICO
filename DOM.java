package CC;

import robocode.*;
import robocode.util.Utils;
import java.awt.Color;
import robocode.HitWallEvent;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

public class DOM extends AdvancedRobot {
    // Variáveis para controle do movimento seguro
    private static final double WALL_MARGIN = 50; // Distância mínima das paredes
    private static final double ROBOT_MARGIN = 30; // Margem para evitar colisões com outros robôs
    
    // Variáveis para controle do alvo
    private double targetX = -1;
    private double targetY = -1;

    @Override
    public void run() {
        // Configuração das cores
        setBodyColor(Color.blue);
        setGunColor(Color.red);
        setRadarColor(Color.green);
        setBulletColor(Color.yellow);
        setScanColor(Color.orange);

        // Fazer o scan contínuo e independente
        setTurnRadarRight(Double.MAX_VALUE); // Gira o radar indefinidamente para buscar inimigos

        // Loop principal
        while (true) {
            if (targetX == -1 && targetY == -1) {
                // Se não tem alvo fixado, faz um scan inicial para detectar um inimigo
                // O radar já está girando, logo a detecção ocorrerá automaticamente
            } else {
                // Se já fixou um alvo, ajusta a movimentação e continua o scan
                followTarget();
            }
            moveSafely(); // Movimenta-se de forma segura
        }
    }

    /**
     * Movimenta o robô de forma segura, evitando colisões com as paredes e outros robôs.
     */
    private void moveSafely() {
        double x = getX();
        double y = getY();
        double battlefieldWidth = getBattleFieldWidth();
        double battlefieldHeight = getBattleFieldHeight();

        // Evitar colisão com as paredes
        if (x <= WALL_MARGIN) {
            turnRight(90);
            ahead(50); // Recuar um pouco da parede
        } else if (x >= battlefieldWidth - WALL_MARGIN) {
            turnLeft(90);
            ahead(50); // Recuar um pouco da parede
        } else if (y <= WALL_MARGIN) {
            turnRight(90);
            ahead(50); // Recuar um pouco da parede
        } else if (y >= battlefieldHeight - WALL_MARGIN) {
            turnLeft(90);
            ahead(50); // Recuar um pouco da parede
        } else {
            // Movimento normal
            ahead(100);
            turnRight(90); // Gira para mudar direção
        }
    }

    /**
     * Segue o alvo fixado e realiza a movimentação de forma segura.
     */
    private void followTarget() {
        if (targetX != -1 && targetY != -1) {
            // Calcula a distância até o alvo fixado
            double angleToTarget = Math.atan2(targetX - getX(), targetY - getY());
            double angleToTurn = Utils.normalRelativeAngle(angleToTarget - getHeadingRadians());

            // Ajusta o movimento para seguir o alvo
            setTurnRightRadians(angleToTurn);
            ahead(100); // Move para frente

            // Realiza a movimentação segura após seguir o alvo
            moveSafely();
        }
    }

    /**
     * Scan inteligente para detectar o inimigo, fixar um alvo e prever seu movimento.
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        double enemyDistance = e.getDistance();
        double enemyHeading = e.getHeading();
        double enemyVelocity = e.getVelocity();

        // Calculando a antecipação (lead) do movimento do inimigo
        double timeToImpact = enemyDistance / 8; // Velocidade média do projétil (ajustável)
        double predictedX = getX() + Math.sin(Math.toRadians(enemyHeading)) * enemyVelocity * timeToImpact;
        double predictedY = getY() + Math.cos(Math.toRadians(enemyHeading)) * enemyVelocity * timeToImpact;

        // Atualizando a posição do alvo fixado (previsão)
        targetX = predictedX;
        targetY = predictedY;

        // Atualizando a direção do radar para seguir o inimigo com antecipação
        double angleToTurn = Utils.normalRelativeAngle(Math.atan2(predictedX - getX(), predictedY - getY()) - getRadarHeadingRadians());
        setTurnRadarRightRadians(angleToTurn);

        // Dispara com base na distância do inimigo
        if (enemyDistance < 100) {
            fire(3); // Potência alta para inimigos muito próximos
        } else if (enemyDistance < 400) {
            fire(2); // Potência média para inimigos a distância intermediária
        } else {
            fire(1); // Potência baixa para inimigos distantes
        }
    }

    /**
     * Lida com colisões contra robôs e evita um novo impacto.
     */
    @Override
    public void onHitRobot(HitRobotEvent event) {
        // Se o robô colidir com outro robô, recua e faz um ajuste de direção
        back(50); // Recuar
        turnRight(90); // Ajuste de direção após colisão
    }

    /**
     * Lida com colisões contra as paredes.
     */
    @Override
    public void onHitWall(HitWallEvent event) {
        // Se o robô colidir com a parede, recua e ajusta a direção
        back(50); // Recuar
        turnRight(45); // Ajuste de direção para contornar a parede
    }
}
