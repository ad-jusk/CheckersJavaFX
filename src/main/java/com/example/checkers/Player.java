package com.example.checkers;

public class Player {

    private TypeOfPiece type;
    private int killedEnemyPieces;

    public Player(TypeOfPiece type){
        this.type = type;
        this.killedEnemyPieces = 0;
    }

    public TypeOfPiece getType() { return type; }

    public void addKill() { killedEnemyPieces++; }
}
