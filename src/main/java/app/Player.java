package app;

public class Player {

    private int killedEnemyPieces;

    public Player(){
        this.killedEnemyPieces = 0;
    }
    public void addPoint(int point) { killedEnemyPieces += point; }

    public int getKilledEnemyPieces() {
        return killedEnemyPieces;
    }
}
