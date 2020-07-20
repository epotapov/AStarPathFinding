package AStar;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main extends Application {
    Scene mainScene;
    Group mainGroup;
    Label clearIndicator;
    double side = 25;
    int sideInt = 25;
    int delay = 10;
    public Cell[][] grid;
    int gridWidth;
    int gridHeight;
    boolean clear;
    boolean node1Exists;
    boolean node2Exists;
    Point startNode;
    Point endNode;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("A* PathFinding");
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(1000);
        mainGroup = new Group();
        gridWidth = 40;
        gridHeight = 26;
        grid = new Cell[gridWidth][gridHeight];
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
                case SPACE:
                    if(node1Exists && node2Exists) {
                        Runnable r = new Runnable() {
                            public void run() {
                                Algo();
                            }
                        };
                        new Thread(r).start();
                    }
                    System.out.println("Insert Algorithm");
                    break;
            }
        });
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            gridWidth = (int)(primaryStage.getWidth() / side);
            grid = new Cell[gridWidth][gridHeight];
            startGrid();
        });
        mainScene.heightProperty().addListener((obs, oldVal, newVal) -> {
            gridHeight = (int)(primaryStage.getHeight() / side);
            grid = new Cell[gridWidth][gridHeight];
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
                grid[i][j] = new Cell(i * side, j * side, side);
                grid[i][j].setFill(Color.WHITE);
                grid[i][j].setStroke(Color.BLACK);
                grid[i][j].restartNode(i, j);
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
            startNode = new Point(i, j);
            grid[i][j].start();
        } else if(node1Exists && !node2Exists && !grid[i][j].startnode) {
            grid[i][j].setFill(Color.PURPLE);
            node2Exists = true;
            endNode = new Point(i, j);
            grid[i][j].end();
        }
    }

    void pathCreation(Cell Current) {
        while(!Current.equals(grid[startNode.x][startNode.y])) {
            try{
                Thread.sleep(delay);
            } catch (Exception e) {}
            Current.setFill(Color.PURPLE);
            Current = Current.parent;
        }
        grid[startNode.x][startNode.y].setFill(Color.PURPLE);
    }

    boolean Algo() {
        borderNodes();
        int count = 0;
        grid[startNode.x][startNode.y].gCost = 0;
        grid[startNode.x][startNode.y].fCost = H(startNode, endNode);
        grid[startNode.x][startNode.y].setCount(count);
        PriorityQueue<Cell> openSet = new PriorityQueue<Cell>(new Comp());
        openSet.add(grid[startNode.x][startNode.y]);
        while (!openSet.isEmpty()) {
            Cell current = openSet.peek();
            openSet.remove(current);
            if(current.equals(grid[endNode.x][endNode.y])) {
                pathCreation(current);
                return true;
            }
            try{
                Thread.sleep(delay / 2);
            } catch (Exception e) {}
            if(!current.equals(grid[startNode.x][startNode.y]))
                current.setFill(Color.RED);
            for (int i = 0; i < current.Surrounding.size(); i++) {
                int tempG = current.gCost + 1;
                if(tempG < current.Surrounding.get(i).gCost) {
                    current.Surrounding.get(i).setParent(current);
                    current.Surrounding.get(i).gCost = tempG;
                    current.Surrounding.get(i).fCost = tempG + H(current.Surrounding.get(i).location, endNode);
                    if (!openSet.contains(current.Surrounding.get(i))) {
                        count++;
                        current.Surrounding.get(i).setCount(count);
                        openSet.add(current.Surrounding.get(i));
                        try{
                            Thread.sleep(delay / 2);
                        } catch (Exception e) {}
                        current.Surrounding.get(i).setFill(Color.GREEN);
                    }
                }
            }
        }
        return false;
    }

    class Comp implements Comparator<Cell> {
        public int compare(Cell c1, Cell c2) {
            if(c1.fCost == c2.fCost)
                if(c1.count == c2.count)
                    return 0;
                else if(c1.count > c2.count)
                    return 1;
                else
                    return -1;
            else if(c1.fCost > c2.fCost)
                return 1;
            else
                return -1;
        }
    }

    void borderNodes() {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                ArrayList<Cell> border = new ArrayList<Cell>();
                if(i != grid.length - 1 && !grid[i + 1][j].getFill().equals(Color.BLACK))
                    border.add(grid[i + 1][j]);
                if(i != 0 && !grid[i - 1][j].getFill().equals(Color.BLACK))
                    border.add(grid[i - 1][j]);
                if(j != grid[i].length - 1 && !grid[i][j + 1].getFill().equals(Color.BLACK))
                    border.add(grid[i][j+ 1]);
                if(j != 0 && !grid[i][j - 1].getFill().equals(Color.BLACK))
                    border.add(grid[i][j - 1]);
                grid[i][j].setNeighbors(border);
            }
        }

    }

    int H(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
}
