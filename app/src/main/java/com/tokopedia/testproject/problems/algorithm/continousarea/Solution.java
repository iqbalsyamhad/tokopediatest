package com.tokopedia.testproject.problems.algorithm.continousarea;

/**
 * Created by hendry on 18/01/19.
 */
public class Solution {
    public static int maxContinuousArea(int[][] matrix) {
        // TODO, return the largest continuous area containing the same integer, given the 2D array with integers
        int[][] stamped = new int[matrix.length][matrix[0].length];
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                stamped[x][y] = 0;
            }
        }

        int finalresult = 0;
        int current;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (stamped[i][j] != 1) {
                    current = matrix[i][j];
                    stamped[i][j] = 1;
                    int result = 1;
                    int hindex = j + 1, vindex = i + 1;

                    //CHECK HORIZONTAL TO VERTICAL
                    while (hindex < matrix[i].length) {
                        if (matrix[i][hindex] == current && stamped[i][hindex] != 1) {
                            stamped[i][hindex] = 1;
                            result++;
                            int hindexv = i + 1;
                            while (hindexv < matrix.length) {
                                if (matrix[hindexv][hindex] == current && stamped[hindexv][hindex] != 1) {
                                    stamped[hindexv][hindex] = 1;
                                    result++;
                                } else {
                                    break;
                                }
                                hindexv++;
                            }
                        } else {
                            break;
                        }

                        hindex++;
                    }

                    //CHECK VERTICAL TO HORIZONTAL FORWARD
                    while (vindex < matrix.length) {
                        if (matrix[vindex][j] == current && stamped[vindex][j] != 1) {
                            stamped[vindex][j] = 1;
                            result++;
                            int vindexh = j + 1;
                            while (vindexh < matrix[vindex].length) {
                                if (matrix[vindex][vindexh] == current && stamped[vindex][vindexh] != 1) {
                                    stamped[vindex][vindexh] = 1;
                                    result++;
                                } else {
                                    break;
                                }

                                //CHECK TOP
                                int hindextop = vindex - 1;
                                while (hindextop >= 0) {
                                    if (matrix[hindextop][vindexh] == current && stamped[hindextop][vindexh] != 1) {
                                        stamped[hindextop][vindexh] = 1;
                                        result++;
                                    } else {
                                        break;
                                    }
                                    hindextop--;
                                }
                                //CHECK BOTTOM
                                int hindexbottom = vindex + 1;
                                while (hindexbottom < matrix.length) {
                                    if (matrix[hindexbottom][vindexh] == current && stamped[hindexbottom][vindexh] != 1) {
                                        stamped[hindexbottom][vindexh] = 1;
                                        result++;
                                    } else {
                                        break;
                                    }
                                    hindexbottom--;
                                }

                                vindexh++;
                            }

                            //CHECK BACK
                            int hindexback = j - 1;
                            while (hindexback >= 0) {
                                if (matrix[vindex][hindexback] == current && stamped[vindex][hindexback] != 1) {
                                    stamped[vindex][hindexback] = 1;
                                    result++;

                                    //CHECK TOP
                                    int hbacktop = vindex - 1;
                                    while (hbacktop >= 0) {
                                        if (matrix[hbacktop][hindexback] == current && stamped[hbacktop][hindexback] != 1) {
                                            stamped[hbacktop][hindexback] = 1;
                                            result++;
                                        } else {
                                            break;
                                        }
                                        hbacktop--;
                                    }
                                    //CHECK BOTTOM
                                    int hbackbottom = vindex + 1;
                                    while (hbackbottom < matrix.length) {
                                        if (matrix[hbackbottom][hindexback] == current && stamped[hbackbottom][hindexback] != 1) {
                                            stamped[hbackbottom][hindexback] = 1;
                                            result++;
                                        } else {
                                            break;
                                        }
                                        hbackbottom--;
                                    }
                                } else {
                                    break;
                                }
                                hindexback--;
                            }
                        } else {
                            break;
                        }
                        vindex++;
                    }

                    if (result > finalresult) finalresult = result;
                }
            }
        }

        return finalresult;
    }
}
