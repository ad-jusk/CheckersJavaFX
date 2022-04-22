package com.example.checkers;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class StatusBar extends BorderPane {

    private Button startButton;

    public StatusBar(int posX){
        //STYLING STATUS BAR
        setPrefSize(CheckersApp.TILE_SIZE * CheckersApp.STATUS_BAR_WIDTH, CheckersApp.TILE_SIZE * CheckersApp.STATUS_BAR_HEIGHT);
        relocate(posX * CheckersApp.TILE_SIZE, 0);
        setStyle("-fx-background-color: #333333");

        //ADDING TITLE LABEL HERE
        Label titleLabel = setTitleLabel();
        setMargin(titleLabel, new Insets(20,0,0,0));
        setAlignment(titleLabel, Pos.CENTER);
        setTop(titleLabel);

        //ADDING EXIT BUTTON
        Button exitButton = setExitButton();
        setMargin(exitButton, new Insets(0,0,20,0));
        setAlignment(exitButton, Pos.CENTER);
        setBottom(exitButton);

        //ADDING START BUTTON
        setStartButton();
        setAlignment(startButton,Pos.CENTER);
        setCenter(startButton);
    }

    public Label setTitleLabel(){
        Label label = new Label("  Welcome\nto checkers!");
        label.setPrefSize(160, 70);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font("Arial", 26));
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-background-color: black; -fx-background-radius: 15px; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");
        return label;
    }

    public Button setExitButton(){
        Button button = new Button();
        button.setText("Quit");
        button.setPrefSize(100, 40);
        button.setTextFill(Color.WHITE);
        button.setFont(new Font("Arial", 26));
        button.setStyle("-fx-background-color: #ff3333; -fx-background-radius: 15px; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");
        button.setAlignment(Pos.CENTER);
        button.setOnAction(e -> {
            Platform.exit();
        });
        button.setOnMouseEntered(e -> {
            button.setTextFill(Color.web("#ff3333"));
            button.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-color: #ff3333; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");

        });
        button.setOnMouseExited(e -> {
            button.setTextFill(Color.WHITE);
            button.setStyle("-fx-background-color: #ff3333; -fx-background-radius: 15px; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");

        });
        return button;
    }

    public void setStartButton(){
        startButton = new Button();
        startButton.setPrefSize(170, 40);
        startButton.setAlignment(Pos.CENTER);
        startButton.setText("Start game");
        startButton.setTextFill(Color.WHITE);
        startButton.setFont(new Font("Arial", 26));
        startButton.setStyle("-fx-background-color: black; -fx-background-radius: 15px; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");

    }

    public Button getStartButton() { return startButton; }
}
