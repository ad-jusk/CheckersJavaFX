package app;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private Piece piece;

    public Tile(boolean red, int positionX, int positionY){
        setWidth(CheckersApp.TILE_SIZE);
        setHeight(CheckersApp.TILE_SIZE);
        relocate(CheckersApp.TILE_SIZE * positionX, CheckersApp.TILE_SIZE * positionY);
        setFill(red ? Color.RED : Color.BLACK);
    }
    public void setPiece(Piece piece){ this.piece = piece; }
    public boolean containsPiece(){ return piece != null; }
    public Piece getPiece() { return piece; }
}
