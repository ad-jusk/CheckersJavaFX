package com.example.checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {

    private TypeOfPiece typeOfPiece;
    private double mousePosX, mousePoxY;
    private double oldX, oldY;

    private final double pieceBackgroundWidth = 0.3125;
    private final double pieceBackgroundHeight = 0.26;

    public Piece(TypeOfPiece type, int positionX, int positionY){

        this.typeOfPiece = type;
        movePiece(positionX, positionY);

        //Piece background
        Ellipse pieceBg = new Ellipse(CheckersApp.TILE_SIZE * pieceBackgroundWidth, CheckersApp.TILE_SIZE * pieceBackgroundHeight);
        pieceBg.setFill(Color.BLACK);
        pieceBg.setStroke(type == TypeOfPiece.GREEN ? Color.DARKGREEN : Color.DARKGRAY);
        pieceBg.setStrokeWidth(CheckersApp.TILE_SIZE * 0.05);
        pieceBg.setTranslateX((CheckersApp.TILE_SIZE - CheckersApp.TILE_SIZE * pieceBackgroundWidth * 2) / 2 - 3);
        pieceBg.setTranslateY((CheckersApp.TILE_SIZE - CheckersApp.TILE_SIZE * pieceBackgroundHeight * 2) / 2 + CheckersApp.TILE_SIZE * 0.07 - 7);

        //Piece foreground
        Ellipse pieceFg = new Ellipse(CheckersApp.TILE_SIZE * pieceBackgroundWidth, CheckersApp.TILE_SIZE * pieceBackgroundHeight);
        pieceFg.setFill(type == TypeOfPiece.GREEN ? Color.valueOf("#00cc00") : Color.valueOf("#fff9f4"));
        pieceFg.setStroke(Color.BLACK);
        pieceFg.setStrokeWidth(CheckersApp.TILE_SIZE * 0.05);
        pieceFg.setTranslateX((CheckersApp.TILE_SIZE - CheckersApp.TILE_SIZE * pieceBackgroundWidth * 2) / 2 - 3);
        pieceFg.setTranslateY((CheckersApp.TILE_SIZE - CheckersApp.TILE_SIZE * pieceBackgroundHeight * 2) / 2 - 7);

        getChildren().addAll(pieceBg, pieceFg);
        setOnMousePressed(e -> {
            mousePosX = e.getSceneX();
            mousePoxY = e.getSceneY();
        });
        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mousePosX + oldX, e.getSceneY() - mousePoxY + oldY);
        });

    }

    public TypeOfPiece getTypeOfPiece() { return typeOfPiece; }

    public void setTypeOfPiece(TypeOfPiece typeOfPiece) { this.typeOfPiece = typeOfPiece; }

    public double getOldX() { return oldX; }

    public double getOldY() { return oldY; }

    public void movePiece(int positionX, int positionY){
        oldX = positionX * CheckersApp.TILE_SIZE;
        oldY = positionY * CheckersApp.TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void transformToKing(TypeOfPiece type){

        //SET PIECE TO KING
        setTypeOfPiece(type == TypeOfPiece.GREEN ? TypeOfPiece.KING_GREEN : TypeOfPiece.KING_WHITE);

        Ellipse ellipse = new Ellipse(12,12);
        ellipse.setFill(Color.YELLOW);
        ellipse.setTranslateX((CheckersApp.TILE_SIZE - CheckersApp.TILE_SIZE * pieceBackgroundWidth * 2) / 2 - 3);
        ellipse.setTranslateY((CheckersApp.TILE_SIZE - CheckersApp.TILE_SIZE * pieceBackgroundHeight * 2) / 2 - 7);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(CheckersApp.TILE_SIZE * 0.03);
        getChildren().add(ellipse);
    }

    public void abortMove(){ relocate(oldX, oldY); }
}
