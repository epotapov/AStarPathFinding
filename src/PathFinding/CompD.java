package PathFinding;

import java.util.Comparator;

public class CompD implements Comparator<Cell> {
    public int compare(Cell c1, Cell c2) {
        if(c1.gCost == c2.gCost)
            if(c1.count == c2.count)
                return 0;
            else if(c1.count > c2.count)
                return 1;
            else
                return -1;
        else if(c1.gCost > c2.gCost)
            return 1;
        else
            return -1;
    }
}
