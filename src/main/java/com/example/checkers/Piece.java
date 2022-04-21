package com.example.checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {

    private  TypeOfPiece typeOfPiece;

    public TypeOfPiece getTypeOfPiece() { return typeOfPiece; }

    public Piece(TypeOfPiece type, int positionX, int positionY){

        final double pieceBackgroundWidth = 0.3125;
        final double pieceBackgroundHeight = 0.26;

        this.typeOfPiece = type;

        relocate(positionX * CheckersApp.TILE_SIZE, positionY * CheckersApp.TILE_SIZE);

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

    }
}
