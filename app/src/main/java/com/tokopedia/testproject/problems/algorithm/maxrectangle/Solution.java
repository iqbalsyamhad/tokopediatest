package com.tokopedia.testproject.problems.algorithm.maxrectangle;


import android.util.Log;

public class Solution {
    public static int maxRect(int[][] matrix) {
        // TODO, return the largest area containing 1's, given the 2D array of 0s and 1s
        int loopx, loopy, resultx = 0, resulty = 0, resultxy, result = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i != 0 && j != 0 && matrix[i][j] == 1) {
                    if (matrix[i - 1][j - 1] == 1 && matrix[i][j - 1] == 1 && matrix[i - 1][j] == 1) {
                        resultxy = 4;
                        if (resultxy > result) result = resultxy;
                        int xyi = i - 2, xyj = j - 2;
                        while (xyi >= 0 && xyj >= 0) {
                            //DIAGONAL CHECK
                            if (
                                    matrix[xyi][xyj] == 1
                                            && matrix[xyi][xyj + 1] == 1
                                            && matrix[xyi + 1][xyj] == 1
                            ) {
                                int vindex = xyi + 2, hindex = xyj + 2, hvincrement = 3;
                                boolean hvcheck = true;
                                while (hindex <= j) {
                                    if (matrix[xyi][hindex] == 1) {
                                        hvincrement++;
                                    } else {
                                        hvcheck = false;
                                        break;
                                    }
                                    hindex++;
                                }
                                while (vindex <= i) {
                                    if (matrix[vindex][xyj] == 1) {
                                        hvincrement++;
                                    } else {
                                        hvcheck = false;
                                        break;
                                    }
                                    vindex++;
                                }
                                if (hvcheck) {
                                    int newresultxy = resultxy + hvincrement;
                                    if (newresultxy > result) result = newresultxy;
                                }
                            }
                            //HORIZONTAL CHECK
                            if (matrix[xyi][xyj + 1] == 1) {
                                int hincrement = 1, hindex = xyj + 2;
                                boolean hcheck = true;
                                while (hindex <= j) {
                                    if (matrix[xyi][hindex] == 1) {
                                        hincrement++;
                                    } else {
                                        hcheck = false;
                                        break;
                                    }
                                    hindex++;
                                }
                                if (hcheck) {
                                    int newresulth = resultxy + hincrement;
                                    if (newresulth > result) result = newresulth;
                                }
                            }
                            //VERTICAL CHECK
                            if (matrix[xyi + 1][xyj] == 1) {
                                int vincrement = 1, vindex = xyi + 2;
                                boolean vcheck = true;
                                while (vindex <= i) {
                                    if (matrix[vindex][xyj] == 1) {
                                        vincrement++;
                                    } else {
                                        vcheck = false;
                                        break;
                                    }
                                    vindex++;
                                }
                                if (vcheck) {
                                    int newresultv = resultxy + vincrement;
                                    if (newresultv > result) result = newresultv;
                                }
                            }
                            xyi--;
                            xyj--;
                        }
                    }
                }

                //SINGLE LINE
                loopx = j;
                resultx = 0;
                while (loopx > -1) {
                    if (matrix[i][loopx] == 1) {
                        loopy = i;
                        resulty = 0;
                        while (loopy > -1) {
                            if (matrix[loopy][loopx] == 1) {
                                resulty++;
                            } else {
                                break;
                            }
                            loopy--;
                        }
                        if (resulty > result) result = resulty;
                        resultx++;
                    } else {
                        break;
                    }
                    loopx--;
                }
                if (resultx > result) result = resultx;
                Log.d("stampedX", "sum of " + j);
            }
        }

        return result;
    }
}
