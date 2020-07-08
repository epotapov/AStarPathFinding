package AStar;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    Scene mainScene;
    Group mainGroup;
    Pane mainPane;
    double side = 50;
    Rectangle[][] grid;

    public static void main(String[] args) {
        launch(args);
    }

    public class cell {
        boolean red;
        public cell() {

        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("A* PathFinding");
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(1000);
        Label hello = new Label("what up");
        //mainPane = new Pane();
        mainGroup = new Group();
        Canvas canvas = new Canvas(1000,650);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        grid = new Rectangle[20][13];
        startGrid();
        //mainGroup.getChildren().add(canvas);
        mainScene = new Scene(mainGroup);
        mainGroup.setOnMousePressed(e -> {
            System.out.println("pressed x:" + e.getX() + " y:" + e.getY());
            updateGrid(e.getX(), e.getY());
        });
        mainGroup.setOnMouseDragged(e -> {
            System.out.println("dragged x:" + e.getX() + " y:" + e.getY());
            updateGrid(e.getX(), e.getY());
        });
        //primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    void startGrid() {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Rectangle(i * side, j * side, side, side);
                grid[i][j].setFill(Color.WHITE);
                grid[i][j].setStroke(Color.BLACK);
                mainGroup.getChildren().add(grid[i][j]);
            }
        }
    }

    void updateGrid(double x, double y) {
        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                double x1 = grid[i][j].getX();
                double y1 = grid[i][j].getY();
                if(x >= grid[i][j].getX() && y >= grid[i][j].getY() && x <= (grid[i][j].getX() + side) && y <= (grid[i][j].getY() + side)) {
                    grid[i][j].setFill(Color.BLACK);
                }
            }
        }
    }
}
