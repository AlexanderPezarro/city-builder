package city_builder;

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

    public WaveCollapseFunction(int[][] initialGrid, HashSet<Integer> possibleValues, HashMap<Integer, Restrictions> restrictions) {
        if (!(invalidGrid = initialGrid == null)) {
            ArrayList<int[]> setTiles = new ArrayList<>();
            grid = new ArrayList<>(initialGrid.length);
            this.restrictions = restrictions;
            rand = new Random();

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
                    Restrictions restriction = restrictions.get(initialGrid[y][x]);
                    propagate(grid, x, y, restriction);
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

                        Restrictions restriction = restrictions.get(randomValue);
                        propagate(grid, x, y, restriction);
                    }
                }
            }
        }
        return false;
    }

    public boolean solve() {
        if (!invalidGrid) {
            
        }
        return false;
    }

    // TODO: Have propagate call recurcivly if a cell has been collapsed to one value after propagation
    private void propagate(ArrayList<ArrayList<HashSet<Integer>>> grid, int x, int y, Restrictions restriction) {
        if (y - 1 >= 0) {
            grid.get(y - 1).get(x).removeAll(restriction.getNorthRestrictions());
            if (grid.get(y - 1).get(x).isEmpty()) {
                invalidGrid = true;
                return;
            }
        }
        if (y + 1 < grid.size()) {
            grid.get(y + 1).get(x).removeAll(restriction.getSouthRestrictions());
            if (grid.get(y + 1).get(x).isEmpty()) {
                invalidGrid = true;
                return;
            }
        }
        if (x - 1 >= 0) {
            grid.get(y).get(x - 1).removeAll(restriction.getWestRestrictions());
            if (grid.get(y).get(x - 1).isEmpty()) {
                invalidGrid = true;
                return;
            }
        }
        if (x + 1 < grid.get(y).size()) {
            grid.get(y).get(x + 1).removeAll(restriction.getEastRestrictions());
            if (grid.get(y).get(x + 1).isEmpty()) {
                invalidGrid = true;
                return;
            }
        }
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
