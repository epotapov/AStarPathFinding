package PathFinding;

import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.ArrayList;

public class Cell extends Rectangle {
    boolean startnode = false;
    boolean endnode = false;
    boolean diagonal = false;
    Cell parent;
    ArrayList<Cell> Surrounding;
    Point location;
    double gCost;
    int count;
    double fCost;

    void start() {
        startnode = true;
        endnode = false;
    }

    void end() {
        startnode = false;
        endnode = true;
    }

    void setFalse() {
        startnode = false;
        endnode = false;
    }

    void setDiagonal() {
        diagonal = true;
    }

    void notDiagonal() {
        diagonal = false;
    }

    void restartNode(int i, int j) {
        startnode = false;
        endnode = false;
        diagonal = false;
        gCost = Double.MAX_VALUE;
        fCost = Double.MAX_VALUE;
        location = new Point(i, j);
    }

    void setCount(int c) {
        count = c;
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
