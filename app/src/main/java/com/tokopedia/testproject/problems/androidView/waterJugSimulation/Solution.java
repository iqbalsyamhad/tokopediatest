package com.tokopedia.testproject.problems.androidView.waterJugSimulation;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static List<WaterJugAction> simulateWaterJug(int jug1, int jug2, int target) {
        // TODO, simulate the smallest number of action to do the water jug problem
        // below is stub, replace with your implementation!
        List<WaterJugAction> listforward = new ArrayList<>();
        List<WaterJugAction> listback = new ArrayList<>();

        //TO JUG 2
        int value1 = 0, value2 = 0;
        while (value1 != target && value2 != target) {
            if (value1 == 0) {
                value1 = jug1;
                listforward.add(new WaterJugAction(WaterJugActionEnum.FILL, 1));
            }
            if (value2 == jug2) {
                value2 = 0;
                listforward.add(new WaterJugAction(WaterJugActionEnum.EMPTY, 2));
            }

            if (value1 != target && value2 != target) {
                int tempvalue1 = value1;
                for (int i = 0; i < tempvalue1; i++) {
                    value2++;
                    value1--;
                    if (value2 == jug2) break;
                }
                listforward.add(new WaterJugAction(WaterJugActionEnum.POUR, 2));
            }
        }

        //TO JUG 1
        int vback1 = 0, vback2 = 0;
        while (vback1 != target && vback2 != target) {
            if (vback2 == 0) {
                vback2 = jug2;
                listback.add(new WaterJugAction(WaterJugActionEnum.FILL, 2));
            }
            if (vback1 == jug1) {
                vback1 = 0;
                listback.add(new WaterJugAction(WaterJugActionEnum.EMPTY, 1));
            }

            if (vback1 != target && vback2 != target) {
                int tempvalue2 = vback2;
                for (int j = 0; j < tempvalue2; j++) {
                    vback1++;
                    vback2--;
                    if (vback1 == jug1) break;
                }
                listback.add(new WaterJugAction(WaterJugActionEnum.POUR, 1));
            }
        }

        return (listforward.size() < listback.size()) ? listforward : listback;
    }
}
