package city_builder.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class WaveCollapseFunction {

    public static final int EMPTY = -1;
    public static final int BLANK = -2;

    private ArrayList<ArrayList<HashSet<Integer>>> grid;
    private HashMap<Integer, Restrictions> restrictions;
    private boolean invalidGrid;
    private Random rand;
    private boolean isSolved;

    public WaveCollapseFunction(int[][] initialGrid, HashSet<Integer> possibleValues, HashMap<Integer, Restrictions> restrictions) {
        if (!(invalidGrid = initialGrid == null)) {
            ArrayList<int[]> setTiles = new ArrayList<>();
            grid = new ArrayList<>(initialGrid.length);
            this.restrictions = restrictions;
            rand = new Random();
            // Assume the given grid isn't solved
            // TODO: Check that this is actually true
            isSolved = false;

            for (int i = 0; i < initialGrid.length; i++) {
                ArrayList<HashSet<Integer>> row = new ArrayList<>(initialGrid[i].length);
                for (int j = 0; j < initialGrid[i].length; j++) {
                    HashSet<Integer> tilePossibleValues = new HashSet<>();
                    // If the tile is empty (has no value set) then set all posible values
                    if (initialGrid[i][j] == -1) {
                        tilePossibleValues.addAll(possibleValues);
                    // Else add only the set value
                    } else {
                        tilePossibleValues.add(initialGrid[i][j]);
                        setTiles.add(new int[] {i, j});
                    }
                    row.add(tilePossibleValues);
                }
                grid.add(row);
            }

            // Collapses the possibilities from the set tiles (if any were set)
            if (setTiles.size() != 0) {
                for (int[] coords : setTiles) {
                    int x = coords[1];
                    int y = coords[0];
                    propagate(grid, x, y);
                }
            }
        }
    }

    public boolean step() {
        if (!invalidGrid) {
            for (int y = 0; y < grid.size(); y++) {
                ArrayList<HashSet<Integer>> row = grid.get(y);

                for (int x = 0; x < row.size(); x++) {
                    HashSet<Integer> possibleValues = row.get(x);

                    // If cell has only one value then skip
                    if (possibleValues.size() != 1) {
                        // Taken from https://stackoverflow.com/a/25410520
                        int index = rand.nextInt(possibleValues.size());
                        Iterator<Integer> iter = possibleValues.iterator();
                        for (int i = 0; i < index; i++) {
                            iter.next();
                        }
                        int randomValue = iter.next();
                        // End copied work

                        // "Collapse" the cell's possible value to the selected value
                        possibleValues.clear();
                        possibleValues.add(randomValue);

                        return propagate(grid, x, y);
                    }
                }
            }
        }
        // All cells have value set so assume board is solved
        isSolved = true;
        return true;
    }

    public boolean solve() {
        if (!invalidGrid) {
            while (!isSolved) {
                // If step() returns false return else loop
                if (!step()) return false;
            }
        }
        return true;
    }

    /**
     * @param grid The cell grid to be propagated over
     * @param x The x coord of the cell being propagated
     * @param y The y coord of the cell being propagated
     * @return False if it resulted in an invalid board and True otherwise
     * 
     * Progagates the restrictions of the value of a cell at the given coords.
     * Assumes that the cell at (x,y) has at least one value. 
     */
    private boolean propagate(ArrayList<ArrayList<HashSet<Integer>>> grid, int x, int y) {
        // Get the restrictions of the value of the cell being propagated
        Restrictions restriction = restrictions.get(grid.get(y).get(x).iterator().next());

        if (y - 1 >= 0) {
            HashSet<Integer> cell = grid.get(y - 1).get(x);
            // Check if cell already has a set value and skip if it does
            if (cell.size() > 1) {
                cell.removeAll(restriction.getNorthRestrictions());

                // Check if no possibilties are left
                if (cell.isEmpty()) {
                    invalidGrid = true;
                    return false;
                }

                // If only one possible value then recursively properage it
                if (cell.size() == 1) {
                    propagate(grid, x, y - 1);
                }
            }
        }

        if (y + 1 < grid.size()) {
            HashSet<Integer> cell = grid.get(y + 1).get(x);
            // Check if cell already has a set value and skip if it does
            if (cell.size() > 1) {
                cell.removeAll(restriction.getSouthRestrictions());

                if (cell.isEmpty()) {
                    invalidGrid = true;
                    return false;
                }

                if (cell.size() == 1) {
                    propagate(grid, x, y + 1);
                }
            }
        }

        if (x - 1 >= 0) {
            HashSet<Integer> cell = grid.get(y).get(x - 1);
            // Check if cell already has a set value and skip if it does
            if (cell.size() > 1) {
                cell.removeAll(restriction.getWestRestrictions());

                if (cell.isEmpty()) {
                    invalidGrid = true;
                    return false;
                }

                if (cell.size() == 1) {
                    propagate(grid, x - 1, y);
                }
            }
        }

        if (x + 1 < grid.get(y).size()) {
            HashSet<Integer> cell = grid.get(y).get(x + 1);
            // Check if cell already has a set value and skip if it does
            if (cell.size() > 1) {
                cell.removeAll(restriction.getEastRestrictions());

                if (cell.isEmpty()) {
                    invalidGrid = true;
                    return false;
                }

                if (cell.size() == 1) {
                    propagate(grid, x + 1, y);
                }
            }
        }
        return true;
    }

    public String printGrid() {
        if (!invalidGrid) {
            String output = "";
            for (ArrayList<HashSet<Integer>> row : grid) {
                for (int i = 0; i < row.size(); i++) {
                    HashSet<Integer> set = row.get(i);
                    // If cell has 1 possible value print it
                    if (set.size() == 1) {
                        output += set.iterator().next();
                    // Else show that the cell in empty
                    } else {
                        output += EMPTY;
                    }
                    // Formating
                    if (i == row.size() - 1) {
                        output += "\n";
                    } else {
                        output += " ";
                    }
                }
                
            }
            // Trim to remove the final space
            return output.trim();
        }
        return "Invalid grid";
    }
}
