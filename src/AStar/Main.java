package AStar;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Main extends Application {
    Scene mainScene;
    Group mainGroup;
    Pane mainPane;
    double side = 25;
    cell[][] grid;
    boolean clear;

    public static void main(String[] args) {
        launch(args);
    }

    public class cell extends Rectangle{
        public cell(double x, double y, double width, double height) {
            super(x,y,width,height);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("A* PathFinding");
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(1000);
        //mainPane = new Pane();
        mainGroup = new Group();
        grid = new cell[40][26];
        startGrid();
        clear = false;
        mainScene = new Scene(mainGroup);
        mainGroup.requestFocus();
        mainGroup.setOnMousePressed(e -> {
            switch (e.getButton()) {
                case PRIMARY:
                    System.out.println("pressed x:" + e.getX() + " y:" + e.getY());
                    updateGrid(e.getX(), e.getY());
                    break;
                case SECONDARY:
                    break;
            }
        });
        mainGroup.setOnMouseDragged(e -> {
            System.out.println("dragged x:" + e.getX() + " y:" + e.getY());
            updateGrid(e.getX(), e.getY());
        });
        mainGroup.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case C:
                    if(!clear)
                        clear = true;
                    else
                        clear = false;
                    break;
                case X:
                    startGrid();
                    break;
            }
        });
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    void startGrid() {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new cell(i * side, j * side, side, side);
                grid[i][j].setFill(Color.WHITE);
                grid[i][j].setStroke(Color.BLACK);
                mainGroup.getChildren().add(grid[i][j]);
            }
        }
    }

    void updateGrid(double x, double y) {
        int i = (int)(x / side);
        int j = (int)(y / side);
        if(i < 0 || i >= grid.length || j < 0 || j >= grid[i].length )
            return;
        if(!clear)
            grid[i][j].setFill(Color.BLACK);
        else
            grid[i][j].setFill(Color.WHITE);
    }
}
