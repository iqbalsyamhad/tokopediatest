package com.tokopedia.testproject.problems.algorithm.waterJug;

public class Solution {

    public static int minimalPourWaterJug(int jug1, int jug2, int target) {
        // TODO, return the smallest number of POUR action to do the water jug problem
        int pourforward = 0, pourback = 0;

        //TO JUG 2
        int value1 = 0, value2 = 0;
        while (value1 != target && value2 != target) {
            if (value1 == 0) value1 = jug1;
            if (value2 == jug2) value2 = 0;

            if (value1 != target && value2 != target) {
                int tempvalue1 = value1;
                for (int i = 0; i < tempvalue1; i++) {
                    value2++;
                    value1--;
                    if (value2 == jug2) break;
                }
                pourforward++;
            }
        }

        //TO JUG 1
        int vback1 = 0, vback2 = 0;
        while (vback1 != target && vback2 != target) {
            if (vback2 == 0) vback2 = jug2;
            if (vback1 == jug1) vback1 = 0;

            if (vback1 != target && vback2 != target) {
                int tempvalue2 = vback2;
                for (int j = 0; j < tempvalue2; j++) {
                    vback1++;
                    vback2--;
                    if (vback1 == jug1) break;
                }
                pourback++;
            }
        }

        return (pourforward < pourback) ? pourforward : pourback;
    }
}
