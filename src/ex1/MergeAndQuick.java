package ex1;

import java.util.Arrays;
import java.util.Random;

public class MergeAndQuick {
    private static void quickSort(int[] array, int low, int high) {
        int pivot, p_pos, i, t;
        if (low < high) {
            p_pos = low;
            pivot = array[p_pos];
            for (i = low + 1; i <= high; i++)
                if (array[i] < pivot) {
                    p_pos++;
                    t = array[p_pos];
                    array[p_pos] = array[i];
                    array[i] = t;
                }
            t = array[low];
            array[low] = array[p_pos];
            array[p_pos] = t;

            quickSort(array, low, p_pos - 1);
            quickSort(array, p_pos + 1, high);
        }
    }

    private static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = a[i++];
        }

        while (j <= high) {
            temp[k++] = a[j++];
        }

        for(int k2 = 0; k2 < temp.length; k2++) {
            a[k2 + low] = temp[k2];
        }
    }

    private static void mergeSort(int[] a, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            mergeSort(a, low, mid);
            mergeSort(a, mid + 1, high);
            merge(a, low, mid, high);
        }

    }

    public static void main(String[] args) {
        Random random = new Random();
        int num = 10000000;
        long startTime, endTime;
        double quickTime, mergeTime;

        int[] a = new int[num];
        int[] b = new int[num];
        for (int i=0; i < a.length; i++) {
            a[i] = random.nextInt(10000000);
            b[i] = a[i];
        }

//        System.out.println("Before sort: \n" + Arrays.toString(a) + "\n") ;

        startTime = System.currentTimeMillis();
        quickSort(a, 0, a.length - 1);
        endTime = System.currentTimeMillis();
        quickTime = (endTime - startTime);
//        System.out.println("After quick sort: \n" + Arrays.toString(a));
        System.out.println("快速排序运行时间为 " + quickTime + " ms");

        startTime = System.currentTimeMillis();
        mergeSort(b, 0, a.length - 1);
        endTime = System.currentTimeMillis();
        mergeTime = (endTime - startTime);
//        System.out.println("After merge sort: \n" + Arrays.toString(b));
        System.out.println("归并排序运行时间为 " + mergeTime + " ms");
    }

}
