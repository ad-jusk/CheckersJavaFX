package com.example.checkers;

public enum TypeOfPiece {
    GREEN(1), WHITE(-1), KING_WHITE(0), KING_GREEN(0);

    final int moveDirection;

    TypeOfPiece(int moveDirection){
        this.moveDirection = moveDirection;
    }
}
