package city_builder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class WaveCollapseFuntionTest {
    
    private static int[][] initialGrid;
    private static HashSet<Integer> possibleValues;
    private static HashMap<Integer,Restrictions> restrictions;

    @BeforeEach void setInitialParameters() {
        initialGrid = new int[4][4];
        for (int y = 0; y < initialGrid.length; y++) {
            for (int x = 0; x < initialGrid[y].length; x++) {
                initialGrid[y][x] = -1;
            }
        }
        possibleValues = new HashSet<Integer>(Arrays.asList(1, 2, 3));
        HashSet<Integer> res2 = new HashSet<Integer>(Arrays.asList(2));
        HashSet<Integer> res13 = new HashSet<Integer>(Arrays.asList(1,3));
        
        Restrictions restriction13 = new Restrictions(res13, res13, res13, res13);
        Restrictions restriction2 = new Restrictions(res2, res2, res2, res2);
        restrictions = new HashMap<>();
        restrictions.put(1, restriction13);
        restrictions.put(2, restriction2);
        restrictions.put(3, restriction13);
    }

    @Test void wcfConstructerReturnsObject() {
        WaveCollapseFunction wcf = new WaveCollapseFunction(initialGrid, possibleValues, restrictions);
        assertNotNull(wcf);
    }

    @Test void wcfInitialGrid() {
        String expectedOutput = "-1 -1 -1 -1\n-1 -1 -1 -1\n-1 -1 -1 -1\n-1 -1 -1 -1";
        WaveCollapseFunction wcf = new WaveCollapseFunction(initialGrid, possibleValues, restrictions);
        assertEquals(expectedOutput, wcf.printGrid());
    }

    @Test void wcfGridWithOneSetValue() {
        String expectedOutput = "1 2 -1 -1\n2 -1 -1 -1\n-1 -1 -1 -1\n-1 -1 -1 -1";
        initialGrid[0][0] = 1;
        WaveCollapseFunction wcf = new WaveCollapseFunction(initialGrid, possibleValues, restrictions);
        assertEquals(expectedOutput, wcf.printGrid());
    }

    @Test void wcfNullInitialGrid() {
        String expectedOutput = "Invalid grid";
        initialGrid = null;
        WaveCollapseFunction wcf = new WaveCollapseFunction(initialGrid, possibleValues, restrictions);
        assertEquals(expectedOutput, wcf.printGrid());
    }

    @Test void wcfInvalidInitialGrid() {
        String expectedOutput = "Invalid grid";
        initialGrid[0][0] = 1;
        initialGrid[0][1] = 1;
        WaveCollapseFunction wcf = new WaveCollapseFunction(initialGrid, possibleValues, restrictions);
        assertEquals(expectedOutput, wcf.printGrid());
    }

    @Test void wcfStep() {
        initialGrid[0][0] = 1;
        WaveCollapseFunction wcf = new WaveCollapseFunction(initialGrid, possibleValues, restrictions);
        assertTrue(wcf.step());
    }
}
