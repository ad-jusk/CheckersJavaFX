package com.example.checkers;

public enum TypeOfPiece {
    //GREEN - top of board
    //WHITE - bottom of board
    GREEN(1), WHITE(-1);

    final int moveDirection;

    TypeOfPiece(int moveDirection){
        this.moveDirection = moveDirection;
    }
}
