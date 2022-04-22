package com.example.checkers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckersApp extends Application {

    public static final int TILE_SIZE = 100;
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Tile[][] board = new Tile[BOARD_HEIGHT][BOARD_WIDTH];

    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(TILE_SIZE * BOARD_WIDTH, TILE_SIZE * BOARD_HEIGHT);

        root.getChildren().addAll(tileGroup, pieceGroup);

        //CREATE TILES AND PIECES
        Tile tile = null;
        for(int row = 0;row<BOARD_HEIGHT;row++){
            for(int column = 0;column<BOARD_WIDTH;column++){
                tile = new Tile((row + column) % 2 == 0,column,row);
                board[column][row] = tile;
                tileGroup.getChildren().add(tile);

                //ADDING PIECES HERE
                Piece piece = null;
                if(row < 3 && (row + column) % 2 == 1){
                    piece = makePiece(TypeOfPiece.GREEN, column, row);
                }
                if(row > 4 && (row + column) % 2 != 0){
                    piece = makePiece(TypeOfPiece.WHITE, column, row);
                }
                if(piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }
        return root;
    }

    private Piece makePiece(TypeOfPiece type, int x, int y){
        Piece piece = new Piece(type, x, y);
        piece.setOnMouseReleased(e -> {
            int newX = pixelsToBoard(piece.getLayoutX());
            int newY = pixelsToBoard(piece.getLayoutY());
            MoveHandler handler = tryMove(piece, newX, newY);

            int x0 = pixelsToBoard(piece.getOldX());
            int y0 = pixelsToBoard(piece.getOldY());

            switch(handler.getType()){
                case NO_MOVE:
                    piece.abortMove();
                    break;
                case NORMAL_MOVE:
                    piece.movePiece(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    break;
                case KILL:
                    piece.movePiece(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece killedPiece = handler.getPiece();
                    int killedPieceX = pixelsToBoard(killedPiece.getOldX());
                    int killedPieceY = pixelsToBoard(killedPiece.getOldY());
                    board[killedPieceX][killedPieceY].setPiece(null);
                    pieceGroup.getChildren().remove(killedPiece);
            }
        });
        return piece;
    }

    private MoveHandler tryMove(Piece piece, int newPosX, int newPosY){
        if(board[newPosX][newPosY].containsPiece() || (newPosX + newPosY) % 2 == 0){
            return new MoveHandler(TypeOfMove.NO_MOVE);
        }
        int x0 = pixelsToBoard(piece.getOldX());
        int y0 = pixelsToBoard(piece.getOldY());

        if(Math.abs(newPosX - x0) == 1 && newPosY - y0 == piece.getTypeOfPiece().moveDirection){
            return new MoveHandler(TypeOfMove.NORMAL_MOVE);
        }
        else if(Math.abs(newPosX - x0) == 2 && newPosY - y0 == piece.getTypeOfPiece().moveDirection * 2){
            int enemyX = x0 + (newPosX - x0) / 2;
            int enemyY = y0 + (newPosY - y0) / 2;
            if(board[enemyX][enemyY].containsPiece() && board[enemyX][enemyY].getPiece().getTypeOfPiece() != piece.getTypeOfPiece()){
                return new MoveHandler(TypeOfMove.KILL, board[enemyX][enemyY].getPiece());
            }
        }
        return new MoveHandler(TypeOfMove.NO_MOVE);
    }

    private int pixelsToBoard(double pixel){
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("CheckersJavaFX");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}