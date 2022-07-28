/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package city_builder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class App {

    public static void main(String[] args) {
        int[][] initialGrid;
        HashSet<Integer> possibleValues;
        HashMap<Integer,Restrictions> restrictions;

        initialGrid = new int[4][4];
        for (int y = 0; y < initialGrid.length; y++) {
            for (int x = 0; x < initialGrid[y].length; x++) {
                initialGrid[y][x] = -1;
            }
        }
        possibleValues = new HashSet<Integer>(Arrays.asList(1, 2, 3));
        HashSet<Integer> res13 = new HashSet<Integer>(Arrays.asList(2));
        HashSet<Integer> res2 = new HashSet<Integer>(Arrays.asList(1,3));
        
        Restrictions restriction13 = new Restrictions(res13, res13, res13, res13);
        Restrictions restriction2 = new Restrictions(res2, res2, res2, res2);
        restrictions = new HashMap<>();
        restrictions.put(1, restriction13);
        restrictions.put(2, restriction2);
        restrictions.put(3, restriction13);

        WaveCollapseFunction wcf = new WaveCollapseFunction(initialGrid, possibleValues, restrictions);
        System.out.println(wcf.printGrid());
    }
}
