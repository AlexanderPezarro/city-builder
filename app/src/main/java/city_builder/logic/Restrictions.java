package city_builder.logic;

import java.util.ArrayList;
import java.util.HashSet;

public class Restrictions {
    
    private final ArrayList<HashSet<Integer>> restrictions;
    
    public Restrictions(HashSet<Integer> north, HashSet<Integer> east, HashSet<Integer> south, HashSet<Integer> west) {
        restrictions = new ArrayList<>(4);
        restrictions.add(north);
        restrictions.add(east);
        restrictions.add(south);
        restrictions.add(west);
    }

    public HashSet<Integer> getNorthRestrictions() {
        return restrictions.get(0);
    }

    public HashSet<Integer> getEastRestrictions() {
        return restrictions.get(1);
    }

    public HashSet<Integer> getSouthRestrictions() {
        return restrictions.get(2);
    }

    public HashSet<Integer> getWestRestrictions() {
        return restrictions.get(3);
    }

}
