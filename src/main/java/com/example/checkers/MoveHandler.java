package com.example.checkers;

public class MoveHandler {

    private TypeOfMove type;
    private Piece piece;

    public MoveHandler(TypeOfMove type, Piece piece){
        this.piece = piece;
        this.type = type;
    }
    public MoveHandler(TypeOfMove type){
        this(type, null);
    }
    public TypeOfMove getType() { return type; }
    public Piece getPiece() { return piece; }

}
