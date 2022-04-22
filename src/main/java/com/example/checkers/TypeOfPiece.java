package com.example.checkers;

public enum TypeOfPiece {
    GREEN(1), WHITE(-1);

    final int moveDirection;

    TypeOfPiece(int moveDirection){
        this.moveDirection = moveDirection;
    }
}
