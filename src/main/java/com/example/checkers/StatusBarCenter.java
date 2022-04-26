package com.example.checkers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class StatusBarCenter extends BorderPane {

    private Button startButton;

    private Label greenPlayerLabel;
    private Label whitePlayerLabel;

    private int greenSeconds;
    private int greenMinutes;
    private int whiteSeconds;
    private int whiteMinutes;

    private Timeline greenTimeline;
    private Timeline whiteTimeline;

    public StatusBarCenter(){
        setPrefSize(CheckersApp.TILE_SIZE * 2, CheckersApp.TILE_SIZE * 4);
        setMargin(this, new Insets(5 ,5, 5 ,5));
        setStyle("-fx-background-color: #333333");
        greenSeconds = 0;
        greenMinutes = 0;
        whiteSeconds = 0;
        whiteMinutes = 0;

        //ADD START BUTTON
        addStartButton();

        //ADD GREEN PLAYER LABEL
        greenPlayerLabel = addPlayerLabel("green");
        greenPlayerLabel.setAlignment(Pos.CENTER);
        setMargin(greenPlayerLabel, new Insets(10, 0, 0, 14));
        setTop(greenPlayerLabel);

        //ADD GREEN TIMELINE
        greenTimeline = new Timeline();
        greenTimeline.setCycleCount(Timeline.INDEFINITE);
        greenTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), e-> {
                    greenPlayerLabel.setText(String.format("%02d:%02d",greenMinutes, greenSeconds));
                    greenSeconds++;
                    if(greenSeconds == 60){
                        greenSeconds = 0;
                        greenMinutes++;
                    }
                }));

        //ADD WHITE TIMELINE
        whiteTimeline = new Timeline();
        whiteTimeline.setCycleCount(Timeline.INDEFINITE);
        whiteTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), e-> {
                    whitePlayerLabel.setText(String.format("%02d:%02d",whiteMinutes, whiteSeconds));
                    whiteSeconds++;
                    if(whiteSeconds == 60){
                        whiteSeconds = 0;
                        whiteMinutes++;
                    }
                }));

        //ADD WHITE PLAYER LABEL
        whitePlayerLabel = addPlayerLabel("white");
        whitePlayerLabel.setAlignment(Pos.CENTER);
        setMargin(whitePlayerLabel, new Insets(0,0,10,14));
        setBottom(whitePlayerLabel);


    }

    public void addStartButton(){
        startButton = new Button();
        startButton.setPrefSize(170, 40);
        startButton.setText("Start game");
        startButton.setTextFill(Color.WHITE);
        startButton.setFont(new Font("Arial", 26));
        startButton.setStyle("-fx-background-color: black; -fx-background-radius: 15px; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");
        startButton.setAlignment(Pos.CENTER);
        setCenter(startButton);
    }

    public Label addPlayerLabel(String color) {
        Label label = new Label();
        label.setTextFill(color.equals("green") ? Color.GREEN : Color.WHITE);
        label.setText("00:00");
        label.setPrefSize(160, CheckersApp.TILE_SIZE * 2);
        label.setFont(new Font("Arial", 28));
        label.setStyle("-fx-background-color: black; -fx-background-radius: 15px; -fx-border-color: " + color + "; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");
        return label;
    }

    public void resetTimelines(){
        greenSeconds = 0;
        greenMinutes = 0;
        whiteSeconds = 0;
        whiteMinutes = 0;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Label getGreenPlayerLabel() {
        return greenPlayerLabel;
    }

    public Label getWhitePlayerLabel() {
        return whitePlayerLabel;
    }

    public Timeline getGreenTimeline() {
        return greenTimeline;
    }

    public Timeline getWhiteTimeline() {
        return whiteTimeline;
    }
}
