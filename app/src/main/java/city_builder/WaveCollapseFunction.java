package city_builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class WaveCollapseFunction {

    public static final int EMPTY = -1;
    public static final int BLANK = -2;
    // Check if using HashSet<Integer>[][] might be better
    private ArrayList<ArrayList<HashSet<Integer>>> grid;

    public WaveCollapseFunction(int[][] initialGrid, HashSet<Integer> possibleValues, HashMap<Integer,Restrictions> restrictions) {
        ArrayList<int[]> setTiles = new ArrayList<>();
        grid = new ArrayList<>(initialGrid.length);
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

    public boolean step() {

        return true;
    }

    public boolean solve() {

        return true;
    }

    private void propagate(ArrayList<ArrayList<HashSet<Integer>>> grid, int x, int y, Restrictions restriction) {
        if (y - 1 >= 0) {
            grid.get(y - 1).get(x).removeAll(restriction.getNorthRestrictions());
        }
        if (y + 1 < grid.size()) {
            grid.get(y + 1).get(x).removeAll(restriction.getSouthRestrictions());
        }
        if (x - 1 >= 0) {
            grid.get(y).get(x - 1).removeAll(restriction.getWestRestrictions());
        }
        if (x + 1 < grid.get(y).size()) {
            grid.get(y).get(x + 1).removeAll(restriction.getEastRestrictions());
        }
    }

    public String printGrid() {
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
        return output;
    }
}
