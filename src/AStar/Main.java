package AStar;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Main extends Application {
    Scene mainScene;
    Group mainGroup;
    Label clearIndicator;
    double side = 25;
    int sideInt = 25;
    cell[][] grid;
    int gridWidth;
    int gridHeight;
    boolean clear;
    boolean node1Exists;
    boolean node2Exists;

    public static void main(String[] args) {
        launch(args);
    }

    public class cell extends Rectangle{
        boolean startnode = false;
        boolean endnode = false;

        void start() {
            startnode = true;
            endnode = false;
        }

        void end() {
            startnode = false;
            endnode = true;
        }

        void restartNode() {
            startnode = false;
            endnode = false;
        }

        public cell(double x, double y, double side) {
            super(x,y,side,side);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("A* PathFinding");
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(1000);
        mainGroup = new Group();
        gridWidth = 40;
        gridHeight = 26;
        grid = new cell[gridWidth][gridHeight];
        clear = false;
        mainScene = new Scene(mainGroup);
        clearIndicator = new Label("Clear on");
        clearIndicator.setTextFill(Color.PURPLE);
        clearIndicator.setVisible(false);
        startGrid();
        mainGroup.requestFocus();
        mainGroup.setOnMousePressed(e -> {
            switch (e.getButton()) {
                case PRIMARY:
                    drawWall(e.getX(), e.getY());
                    break;
                case SECONDARY:
                    drawNodes(e.getX(), e.getY());
                    break;
            }
        });
        mainGroup.setOnMouseDragged(e -> {
            switch (e.getButton()) {
                case PRIMARY:
                    drawWall(e.getX(), e.getY());
                    break;
                case SECONDARY:
                    break;
            }
        });
        mainGroup.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case C:
                    if(!clear) {
                        clearIndicator.setVisible(true);
                        clear = true;
                    } else {
                        clearIndicator.setVisible(false);
                        clear = false;
                    }
                    break;
                case X:
                    startGrid();
                    break;
            }
        });
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            gridWidth = (int)(primaryStage.getWidth() / side);
            grid = new cell[gridWidth][gridHeight];
            startGrid();
        });
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            gridHeight = (int)(primaryStage.getHeight() / side);
            grid = new cell[gridWidth][gridHeight];
            startGrid();
        });
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    void startGrid() {
        mainGroup.getChildren().clear();
        node1Exists = false;
        node2Exists = false;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new cell(i * side, j * side, side);
                grid[i][j].setFill(Color.WHITE);
                grid[i][j].setStroke(Color.BLACK);
                grid[i][j].restartNode();
                mainGroup.getChildren().add(grid[i][j]);
            }
        }
        mainGroup.getChildren().add(clearIndicator);
    }

    void drawWall(double x, double y) {
        int i = (int)(x / side);
        int j = (int)(y / side);
        if((i < 0 || i >= grid.length || j < 0 || j >= grid[i].length) || (grid[i][j].startnode || grid[i][j].endnode))
            return;
        if(!clear)
            grid[i][j].setFill(Color.BLACK);
        else
            grid[i][j].setFill(Color.WHITE);
    }

    void drawNodes(double x, double y) {
        int i = (int)(x / side);
        int j = (int)(y / side);
        if(i < 0 || i >= grid.length || j < 0 || j >= grid[i].length )
            return;
        if(!node1Exists && !node2Exists) {
            grid[i][j].setFill(Color.BLUE);
            node1Exists = true;
            grid[i][j].start();
        } else if(node1Exists && !node2Exists) {
            grid[i][j].setFill(Color.PURPLE);
            node2Exists = true;
            grid[i][j].end();
        }
    }
}
