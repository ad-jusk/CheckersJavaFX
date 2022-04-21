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
        return new Piece(type, x, y);
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