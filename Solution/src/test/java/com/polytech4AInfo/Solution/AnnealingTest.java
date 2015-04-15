package com.polytech4AInfo.Solution;

import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by benoitvuillemin on 15/04/2015.
 */
public class AnnealingTest {
    Solution s_first;
    ShapeGroup s1;
    ShapeGroup s2;
    ShapeGroup s3;
    ArrayList<ShapeGroup> a;
    Pattern p1;
    Pattern[] p;
    int[] tab_int;

    @Before
    public void setUp() {
        s1 = new ShapeGroup(2, 3, 1000);
        s2 = new ShapeGroup(3, 8, 3000);
        s3 = new ShapeGroup(3, 9, 4000);
        a = new ArrayList<ShapeGroup>();
        a.add(s1);
        a.add(s2);
        a.add(s3);
        p1 = new Pattern(a, new Sheet(20, 20));
        p = new Pattern[1];
        p[0] = p1;
        tab_int = new int[1];
        s_first = new Solution(p, tab_int);
    }

    @Test
    public void testFindNeighbour() {
        Solution s_final = Annealing.findNeighbour(s_first);
        int changes = 0;
        assertEquals(s_final.getSolution().length, s_first.getSolution().length);
        assertEquals(s_final.getSolution()[0].length, s_first.getSolution()[0].length);
        for (int i = 0; i < s_final.getSolution().length; i++) {
            for (int j = 0; j < s_final.getSolution()[i].length; j++) {
                if (s_final.getSolution()[i][j] == s_first.getSolution()[i][j] - 1 ||
                        s_final.getSolution()[i][j] == s_first.getSolution()[i][j] + 1) {
                    changes += 1;
                }
            }
        }
        assertEquals(1, changes);
    }
}
