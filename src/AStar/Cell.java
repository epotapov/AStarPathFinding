package AStar;

import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.ArrayList;

public class Cell extends Rectangle {
    boolean startnode = false;
    boolean middlenode = false;
    boolean endnode = false;
    Cell parent;
    ArrayList<Cell> Surrounding;
    Point location;
    int gCost;
    int hCost;
    int fCost;

    void start() {
        startnode = true;
        endnode = false;
    }

    void end() {
        startnode = false;
        endnode = true;
    }

    void restartNode(int i, int j) {
        startnode = false;
        endnode = false;
        gCost = Integer.MAX_VALUE;
        fCost = Integer.MAX_VALUE;
        location = new Point(i, j);
    }

    void setNeighbors(ArrayList<Cell> c) {
        Surrounding = c;
    }

    void setParent(Cell c) {
        parent = c;
    }

    public Cell(double x, double y, double side) {
        super(x,y,side,side);
    }
}
