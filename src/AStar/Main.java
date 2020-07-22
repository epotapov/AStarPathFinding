package AStar;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main extends Application {
    Scene mainScene;
    Group mainGroup;
    HBox indicatorBar;
    Label clearIndicator;
    Rectangle clearBackground;
    StackPane clearPane;
    Label dijkstraIndicator;
    Rectangle dijkstraBackground;
    StackPane dijkstraPane;
    double side = 25;
    int delay = 10;
    double clearWidth = 49;
    double clearHeight = 25;
    public Cell[][] grid;
    int gridWidth;
    int gridHeight;
    boolean clear;
    boolean node1Exists;
    boolean node2Exists;
    boolean algoRun;
    boolean dijkstra;
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
        dijkstra = false;
        mainScene = new Scene(mainGroup);
        indicatorSetup();
        startGrid();
        mainGroup.requestFocus();
        addControl(primaryStage);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    void indicatorSetup() {
        indicatorBar = new HBox();
        clearPane = new StackPane();
        clearPane.setPrefSize(clearWidth, clearHeight);
        clearIndicator = new Label("Clear on");
        clearIndicator.setPrefSize(clearWidth, clearHeight);
        clearIndicator.setStyle("-fx-font-weight: bold");
        clearBackground = new Rectangle(0,0,clearWidth, clearHeight);
        clearBackground.setFill(Color.YELLOW);
        clearBackground.setStroke(Color.BLACK);
        clearPane.getChildren().addAll(clearBackground, clearIndicator);
        clearPane.setVisible(false);
        dijkstraPane = new StackPane();
        dijkstraPane.setPrefSize(clearWidth, clearHeight);
        dijkstraIndicator = new Label("Dijkstra");
        dijkstraIndicator.setPrefSize(clearWidth, clearHeight);
        dijkstraIndicator.setStyle("-fx-font-weight: bold");
        dijkstraBackground = new Rectangle(clearWidth, 0, clearWidth,clearHeight);
        dijkstraBackground.setFill(Color.web("#ff6ef5"));
        dijkstraBackground.setStroke(Color.BLACK);
        dijkstraPane.setVisible(false);
        dijkstraPane.getChildren().addAll(dijkstraBackground,dijkstraIndicator);
        indicatorBar.getChildren().addAll(clearPane, dijkstraPane);
    }

    void addControl(Stage primaryStage) {
        mainGroup.setOnMousePressed(e -> {
            switch (e.getButton()) {
                case PRIMARY:
                    if(!algoRun) {
                        drawWall(e.getX(), e.getY());
                    }
                    break;
                case SECONDARY:
                    drawNodes(e.getX(), e.getY());
                    break;
            }
        });
        mainGroup.setOnMouseDragged(e -> {
            switch (e.getButton()) {
                case PRIMARY:
                    if(!algoRun) {
                        drawWall(e.getX(), e.getY());
                    }
                    break;
                case SECONDARY:
                    break;
            }
        });
        mainGroup.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case C:
                    if(!algoRun) {
                        if (!clear) {
                            clearPane.setVisible(true);
                            clear = true;
                        } else {
                            clearPane.setVisible(false);
                            clear = false;
                        }
                    }
                    break;
                case X:
                    startGrid();
                    break;
                case D:
                    if(!algoRun) {
                        if (!dijkstra) {
                            dijkstraPane.setVisible(true);
                            dijkstra = true;
                        } else {
                            dijkstraPane.setVisible(false);
                            dijkstra = false;
                        }
                    }
                    break;
                case SPACE:
                    if(!algoRun) {
                        if (node1Exists && node2Exists) {
                            Runnable r = () -> {
                                if(!dijkstra)
                                    Algo();
                                else
                                    DijkstraAlgo();
                            };
                            new Thread(r).start();
                        }
                    }
                    break;
            }
        });
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            gridWidth = (int)(primaryStage.getWidth() / side);
            grid = new Cell[gridWidth][gridHeight];
            startGrid();
        });
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            gridHeight = (int)(primaryStage.getHeight() / side);
            grid = new Cell[gridWidth][gridHeight];
            startGrid();
        });
    }

    void startGrid() {
        mainGroup.getChildren().clear();
        node1Exists = false;
        node2Exists = false;
        algoRun = false;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i * side, j * side, side);
                grid[i][j].setFill(Color.WHITE);
                grid[i][j].setStroke(Color.BLACK);
                grid[i][j].restartNode(i, j);
                mainGroup.getChildren().add(grid[i][j]);
            }
        }
        mainGroup.getChildren().add(indicatorBar);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            Current.setFill(Color.PURPLE);
            Current = Current.parent;
        }
        grid[startNode.x][startNode.y].setFill(Color.PURPLE);
    }

    boolean Algo() {
        algoRun = true;
        int count = 0;
        grid[startNode.x][startNode.y].gCost = 0;
        grid[startNode.x][startNode.y].fCost = H(startNode, endNode);
        grid[startNode.x][startNode.y].setCount(count);
        PriorityQueue<Cell> openSet = new PriorityQueue<Cell>(new Comp());
        openSet.add(grid[startNode.x][startNode.y]);
        while (!openSet.isEmpty()) {
            Cell current = openSet.peek();
            openSet.remove(current);
            borderNodes(current.location.x, current.location.y);
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

    boolean DijkstraAlgo() {
        algoRun = true;
        int count = 0;
        grid[startNode.x][startNode.y].gCost = 0;
        grid[startNode.x][startNode.y].setCount(count);
        PriorityQueue<Cell> openSet = new PriorityQueue<Cell>(new CompD());
        openSet.add(grid[startNode.x][startNode.y]);
        while (!openSet.isEmpty()) {
            Cell current = openSet.peek();
            openSet.remove(current);
            borderNodes(current.location.x, current.location.y);
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
                    if (!openSet.contains(current.Surrounding.get(i))) {
                        count++;
                        current.Surrounding.get(i).setCount(count);
                        openSet.add(current.Surrounding.get(i));
                        try{
                            Thread.sleep(delay / 2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        current.Surrounding.get(i).setFill(Color.GREEN);
                    }
                }
            }
        }
        return false;
    }

    void borderNodes(int i, int j) {
        ArrayList<Cell> border = new ArrayList<Cell>();
        boolean up = true;
        boolean down = true;
        boolean right = true;
        boolean left = true;
        if(i != 0 && !grid[i - 1][j].getFill().equals(Color.BLACK)) {
            border.add(grid[i - 1][j]);
            up = false;
        }
        if(i != grid.length - 1 && !grid[i + 1][j].getFill().equals(Color.BLACK)) {
            border.add(grid[i + 1][j]);
            down = false;
        }
        if(j != grid[i].length - 1 && !grid[i][j + 1].getFill().equals(Color.BLACK)) {
            border.add(grid[i][j + 1]);
            right = false;
        }
        if(j != 0 && !grid[i][j - 1].getFill().equals(Color.BLACK)) {
            border.add(grid[i][j - 1]);
            left = false;
        }
        if(i != 0 && j != 0 && !grid[i - 1][j - 1].getFill().equals(Color.BLACK) && !(up && left)) {
            border.add(grid[i - 1][j - 1]);
        }
        if(i != 0 && j != grid[i].length - 1 && !grid[i - 1][j + 1].getFill().equals(Color.BLACK) && !(up && right)) {
            border.add(grid[i - 1][j + 1]);
        }
        if(i != grid.length - 1 && j != 0 && !grid[i + 1][j - 1].getFill().equals(Color.BLACK) && !(down && left)) {
            border.add(grid[i + 1][j - 1]);
        }
        if(i != grid.length - 1 && j != grid[i].length - 1 && !grid[i + 1][j + 1].getFill().equals(Color.BLACK) && !(down && right)) {
            border.add(grid[i + 1][j + 1]);
        }
        grid[i][j].setNeighbors(border);
    }

    int H(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
}
