package Moves;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;

public class Precoce30s extends AdvancedRobot {

    private boolean firstLap = true; // Controle da primeira volta
    private double margin = 40; // Margem para evitar colisões
    private boolean collisionRecovery = false; // Evitar repetição de ajuste ao colidir

    @Override
    public void run() {
        // Garante que o robô comece alinhado com a borda
        alignToWall();

        while (true) {
            // Movimenta-se ao longo das bordas
            if (firstLap) {
                ahead(getMoveAmount() - margin); // Primeira volta com margem
                firstLap = false; // Marca o fim da primeira volta
            } else {
                // Movimenta suavemente ajustando pela posição e bordas
                moveAlongWall();
            }

            // Ajusta para a próxima borda
            turnRight(90);
        }
    }

    /**
     * Alinha o robô à borda inicial.
     */
    private void alignToWall() {
        turnLeft(getHeading() % 90); // Alinha ao ângulo mais próximo da borda
        ahead(getMoveAmount() - margin); // Move até a borda com margem
        turnRight(90); // Prepara para mover na direção das bordas
    }

    /**
     * Move ao longo das bordas sem colidir.
     */
    private void moveAlongWall() {
        double heading = getHeading();
        double x = getX();
        double y = getY();
        double moveDistance;

        if (heading == 0) { // Indo para o topo
            moveDistance = getBattleFieldHeight() - y - margin;
        } else if (heading == 90) { // Indo para a direita
            moveDistance = getBattleFieldWidth() - x - margin;
        } else if (heading == 180) { // Indo para baixo
            moveDistance = y - margin;
        } else if (heading == 270) { // Indo para a esquerda
            moveDistance = x - margin;
        } else {
            moveDistance = getMoveAmount() - margin; // Movimento padrão
        }

        // Executa o movimento com a distância calculada
        ahead(moveDistance);
    }

    /**
     * Retorna a distância máxima para movimentação.
     */
    private double getMoveAmount() {
        return Math.max(getBattleFieldWidth(), getBattleFieldHeight());
    }

    /**
     * Lida com colisões contra paredes.
     */
    @Override
    public void onHitWall(HitWallEvent event) {
        back(20); // Recuar levemente
        turnRight(45); // Ajustar o ângulo
    }

    /**
     * Lida com colisões contra outros robôs.
     */
    @Override
    public void onHitRobot(HitRobotEvent event) {
        if (!collisionRecovery) { // Evitar repetição do ajuste
            collisionRecovery = true; // Marca que estamos em recuperação

            if (firstLap) {
                // Ajusta a posição para retornar à borda
                back(50); // Recuar levemente para evitar o robô
                alignToWall(); // Reposicionar para a borda
            } else {
                // Reagir normalmente durante voltas subsequentes
                back(20);
                turnRight(45);
            }

            collisionRecovery = false; // Recuperação concluída
        } else {
            // Caso já esteja em recuperação, faça algo diferente
            back(10); // Pequeno ajuste para sair do loop
            turnLeft(30); // Altere o ângulo para variar o ajuste
        }
    }
}
