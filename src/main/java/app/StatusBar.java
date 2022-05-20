package app;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class StatusBar extends BorderPane {

    private StatusBarCenter center;
    private Label statusLabel;

    public StatusBar(int posX){
        //STYLING STATUS BAR
        setPrefSize(CheckersApp.TILE_SIZE * CheckersApp.STATUS_BAR_WIDTH, CheckersApp.TILE_SIZE * CheckersApp.STATUS_BAR_HEIGHT);
        relocate(posX * CheckersApp.TILE_SIZE, 0);
        setStyle("-fx-background-color: #333333");

        //ADDING STATUS LABEL HERE
        addTitleLabel();

        //ADDING EXIT BUTTON
        Button exitButton = addExitButton();
        setMargin(exitButton, new Insets(0,0,20,0));
        setAlignment(exitButton, Pos.CENTER);
        setBottom(exitButton);

        //ADDING CENTER PANE
        addCenterPane();
    }

    public void addTitleLabel(){
        statusLabel= new Label("  Welcome\nto checkers!");
        statusLabel.setPrefSize(160, 70);
        statusLabel.setTextFill(Color.WHITE);
        statusLabel.setFont(new Font("Arial", 26));
        statusLabel.setAlignment(Pos.CENTER);
        statusLabel.setStyle("-fx-background-color: black; -fx-background-radius: 15px; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");
        setMargin(statusLabel, new Insets(20,0,0,0));
        setAlignment(statusLabel, Pos.CENTER);
        setTop(statusLabel);
    }

    public Button addExitButton(){
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

    public void addCenterPane(){
        center = new StatusBarCenter();
        setAlignment(center,Pos.CENTER);
        setCenter(center);
    }


    public Label getStatusLabel() {
        return statusLabel;
    }

    public StatusBarCenter getStatusCenter() {
        return center;
    }
}
