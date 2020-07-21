package AStar;

import java.util.Comparator;

public class Comp implements Comparator<Cell> {
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
