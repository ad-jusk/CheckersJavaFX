package com.example.checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckersApp extends Application {

    public static final int TILE_SIZE = 100;
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;
    public static final int STATUS_BAR_WIDTH = 2;
    public static final int STATUS_BAR_HEIGHT = 8;

    private final Group tileGroup = new Group();
    private final Group pieceGroup = new Group();
    private final StatusBar statusBar = new StatusBar(BOARD_WIDTH);

    private final Tile[][] board = new Tile[BOARD_HEIGHT][BOARD_WIDTH];

    private String whoseTurn;
    private Player playerGreen;
    private Player playerWhite;

    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(TILE_SIZE * (BOARD_WIDTH + STATUS_BAR_WIDTH), TILE_SIZE * BOARD_HEIGHT);

        root.getChildren().addAll(tileGroup, pieceGroup, statusBar);

        //CREATE TILES AND PIECES
        Tile tile;
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

        //INITIALLY DISABLE ALL PIECES
        disableOrEnablePieces(TypeOfPiece.GREEN, TypeOfPiece.KING_GREEN, true);
        disableOrEnablePieces(TypeOfPiece.WHITE, TypeOfPiece.KING_WHITE, true);

        //ADD ACTION TO START BUTTON
        statusBar.getStartButton().setOnAction(e -> {
            resetPiecesPosition();
            statusBar.getStartButton().setText("Reset");
            statusBar.getStatusLabel().setText("Green turn");
            whoseTurn = "green";
            playerGreen = new Player();
            playerWhite = new Player();
        });

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
                    handleRound(0);
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
                    handleRound(1);

                    //CHECK WIN
                    String winner = checkIfSomeoneWon();
                    if(winner.equals("white")){
                        statusBar.getStatusLabel().setText("White wins!");
                    }
                    else if(winner.equals("green")){
                        statusBar.getStatusLabel().setText("Green wins!");
                    }
                    break;
            }
        });
        return piece;
    }

    public void resetPiecesPosition(){
        pieceGroup.getChildren().clear();
        for(int row = 0;row<BOARD_HEIGHT;row++){
            for(int column = 0;column<BOARD_WIDTH;column++){
                //ADDING PIECES HERE
                Piece piece = null;
                if(row < 3 && (row + column) % 2 == 1){
                    piece = makePiece(TypeOfPiece.GREEN, column, row);
                }
                if(row > 4 && (row + column) % 2 != 0){
                    piece = makePiece(TypeOfPiece.WHITE, column, row);
                }
                if(piece != null) {
                    board[column][row].setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
                else{
                    board[column][row].setPiece(null);
                }
            }
        }
    }

    public void disableOrEnablePieces(TypeOfPiece type, TypeOfPiece kingType, boolean disabled){
        for(int i = 0;i<pieceGroup.getChildren().size();i++){
            Node node = pieceGroup.getChildren().get(i);
            Piece piece = (Piece)node;
            if(piece.getTypeOfPiece() == type || piece.getTypeOfPiece() == kingType){
                piece.setDisable(disabled);
            }
        }
    }

    private MoveHandler tryMove(Piece piece, int newPosX, int newPosY){
        if(board[newPosX][newPosY].containsPiece() || (newPosX + newPosY) % 2 == 0){
            return new MoveHandler(TypeOfMove.NO_MOVE);
        }
        int x0 = pixelsToBoard(piece.getOldX());
        int y0 = pixelsToBoard(piece.getOldY());

        //GREEN KING PIECE
        if(piece.getTypeOfPiece() == TypeOfPiece.KING_GREEN || piece.getTypeOfPiece() == TypeOfPiece.KING_WHITE){
            if(Math.abs(newPosX - x0) == 1 && Math.abs(newPosY - y0) == 1){
                return new MoveHandler(TypeOfMove.NORMAL_MOVE);
            }
            else if(Math.abs(newPosX - x0) == 2 && Math.abs(newPosY - y0) == 2){
                int enemyX = x0 + (newPosX - x0) / 2;
                int enemyY = y0 + (newPosY - y0) / 2;

                if(piece.getTypeOfPiece() == TypeOfPiece.KING_GREEN){
                    if(board[enemyX][enemyY].containsPiece() && board[enemyX][enemyY].getPiece().getTypeOfPiece() != piece.getTypeOfPiece() && board[enemyX][enemyY].getPiece().getTypeOfPiece() != TypeOfPiece.GREEN){
                        return new MoveHandler(TypeOfMove.KILL, board[enemyX][enemyY].getPiece());
                    }
                }
                else{
                    TypeOfPiece enemyType = board[enemyX][enemyY].getPiece().getTypeOfPiece();
                    if(board[enemyX][enemyY].containsPiece() && enemyType != piece.getTypeOfPiece() && enemyType != TypeOfPiece.WHITE){
                        return new MoveHandler(TypeOfMove.KILL, board[enemyX][enemyY].getPiece());
                    }
                }
                return new MoveHandler(TypeOfMove.NO_MOVE);
            }
        }

        //NORMAL PIECE
        if(Math.abs(newPosX - x0) == 1 && newPosY - y0 == piece.getTypeOfPiece().moveDirection){
            if((newPosY == 0 || newPosY == 7) && piece.getTypeOfPiece() != TypeOfPiece.KING_GREEN && piece.getTypeOfPiece() != TypeOfPiece.KING_WHITE){
                piece.transformToKing(piece.getTypeOfPiece());
            }
            return new MoveHandler(TypeOfMove.NORMAL_MOVE);
        }
        else if(Math.abs(newPosX - x0) == 2 && newPosY - y0 == piece.getTypeOfPiece().moveDirection * 2){
            int enemyX = x0 + (newPosX - x0) / 2;
            int enemyY = y0 + (newPosY - y0) / 2;
            TypeOfPiece enemyType = board[enemyX][enemyY].getPiece().getTypeOfPiece();
            TypeOfPiece pieceType = piece.getTypeOfPiece();
            boolean isFromTeam = false;
            if(pieceType == TypeOfPiece.GREEN && enemyType == TypeOfPiece.KING_GREEN){
                isFromTeam = true;
            }
            else if(pieceType == TypeOfPiece.WHITE && enemyType == TypeOfPiece.KING_WHITE){
                isFromTeam = true;
            }
            if(board[enemyX][enemyY].containsPiece() && enemyType != pieceType && !isFromTeam){
                if((newPosY == 0 || newPosY == 7) && piece.getTypeOfPiece() != TypeOfPiece.KING_GREEN && piece.getTypeOfPiece() != TypeOfPiece.KING_WHITE){
                    piece.transformToKing(piece.getTypeOfPiece());
                }
                return new MoveHandler(TypeOfMove.KILL, board[enemyX][enemyY].getPiece());
            }
        }
        return new MoveHandler(TypeOfMove.NO_MOVE);
    }

    public void handleRound(int point){
        switch (whoseTurn){
            case "green":
                disableOrEnablePieces(TypeOfPiece.GREEN, TypeOfPiece.KING_GREEN, true);
                disableOrEnablePieces(TypeOfPiece.WHITE, TypeOfPiece.KING_WHITE, false);
                playerGreen.addPoint(point);
                whoseTurn = "white";
                statusBar.getStatusLabel().setText("White turn");
                break;
            case "white":
                disableOrEnablePieces(TypeOfPiece.WHITE, TypeOfPiece.KING_WHITE, true);
                disableOrEnablePieces(TypeOfPiece.GREEN, TypeOfPiece.KING_GREEN, false);
                playerWhite.addPoint(point);
                whoseTurn = "green";
                statusBar.getStatusLabel().setText("Green turn");
                break;
            default:
                break;
        }
    }

    public String checkIfSomeoneWon(){
        if(playerWhite.getKilledEnemyPieces() == 12){
            return "white";
        }
        else if(playerGreen.getKilledEnemyPieces() == 12){
            return "green";
        }
        return "";
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
        launch();
    }
}