package ex1;

import java.util.Arrays;

public class MaxValueOfArray {
    private static int findMaxValue(int[] array, int start, int end){
        int mid = (start + end) / 2;
        int max, m1, m2;
        if(start < end){
            m1 = findMaxValue(array, start, mid);
            m2 = findMaxValue(array, mid+1, end);
            return max = (m1 > m2 ? m1 : m2);
        }

        return array[start];
    }

    public static void main(String[] args) {
        int[] x = {23, 35, 234, 645, 34, 54, 7, 3782};
        int max = findMaxValue(x, 0, x.length-1);
        System.out.print("数组 " + Arrays.toString(x) + " 中的最大值为 " + max + ".");
    }
}
