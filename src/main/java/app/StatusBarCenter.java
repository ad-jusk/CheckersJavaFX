package app;

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

    private final Label greenPlayerLabel;
    private final Label whitePlayerLabel;

    public int greenSeconds;
    public int whiteSeconds;
    private int greenKills;
    private int whiteKills;

    public StatusBarCenter(){
        setPrefSize(CheckersApp.TILE_SIZE * 2, CheckersApp.TILE_SIZE * 4);
        setMargin(this, new Insets(5 ,5, 5 ,5));
        setStyle("-fx-background-color: #333333");
        greenSeconds = 30;
        whiteSeconds = 30;

        //ADD START BUTTON
        addStartButton();

        //ADD GREEN PLAYER LABEL
        greenPlayerLabel = addPlayerLabel("green");
        greenPlayerLabel.setAlignment(Pos.CENTER);
        refreshPlayerLabel("green");
        setMargin(greenPlayerLabel, new Insets(10, 0, 0, 14));
        setTop(greenPlayerLabel);

        //ADD WHITE PLAYER LABEL
        whitePlayerLabel = addPlayerLabel("white");
        whitePlayerLabel.setAlignment(Pos.CENTER);
        refreshPlayerLabel("white");
        setMargin(whitePlayerLabel, new Insets(0,0,10,14));
        setBottom(whitePlayerLabel);
    }

    public void addStartButton(){
        startButton = new Button();
        startButton.setPrefSize(170, 40);
        startButton.setText("Start game");
        startButton.setTextFill(Color.WHITE);
        startButton.setFont(new Font("Arial", 26));
        startButton.setStyle("-fx-background-color: black; -fx-background-color: black; -fx-background-radius: 15px; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");
        setMargin(startButton, new Insets(0,0,12,0));
        startButton.setAlignment(Pos.CENTER);
        setCenter(startButton);
    }

    public Label addPlayerLabel(String color) {
        Label label = new Label();
        label.setTextFill(color.equals("green") ? Color.web("#00cc00") : Color.WHITE);
        label.setPrefSize(165, CheckersApp.TILE_SIZE * 2);
        label.setFont(new Font("Arial", 28));
        label.setStyle("-fx-background-color: black; -fx-background-radius: 15px; -fx-border-color: " + color + "; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");
        return label;
    }

    public void resetTimelinesAndKills(){
        greenSeconds = 30;
        whiteSeconds = 30;
        greenKills = 0;
        whiteKills = 0;
        whitePlayerLabel.setText(String.format("Points:  %2d\n\n   00:%02d", whiteKills,whiteSeconds));
        greenPlayerLabel.setText(String.format("Points:  %2d\n\n   00:%02d", greenKills,greenSeconds));
    }

    public void setKills(int kills, String which) {
        if(which.equals("green")){
            this.greenKills = kills;
        }
        else{
            this.whiteKills = kills;
        }
    }

    public void refreshPlayerLabel(String which){
        if(which.equals("green")){
            greenPlayerLabel.setText(String.format("Points:  %2d\n\n   00:%02d", greenKills, greenSeconds));
        }
        else{
            whitePlayerLabel.setText(String.format("Points:  %2d\n\n   00:%02d", whiteKills, whiteSeconds));
        }
    }

    public Button getStartButton() {
        return startButton;
    }

}
