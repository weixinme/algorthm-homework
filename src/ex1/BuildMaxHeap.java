package ex1;

import java.util.Arrays;

public class BuildMaxHeap {
    private static void maxHeap(int[] array, int heapSize, int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;

        int largest = index;
        if (left < heapSize && array[left] > array[index]) {
            largest = left;
        }

        if (right < heapSize && array[right] > array[largest]) {
            largest = right;
        }

        if (index != largest) {
            int temp = array[index];
            array[index] = array[largest];
            array[largest] = temp;

            maxHeap(array, heapSize, largest);
        }
    }
    private static void buildMaxHeap(int[] array, int i){
        if(array == null || array.length <= 1)
            return;

        int half = array.length / 2;
        int quarter = array.length / 4 - 1;

        if(i >= quarter && i <= half)
            maxHeap(array, array.length, i);
        else {
            buildMaxHeap(array, 2 * i + 1);
            buildMaxHeap(array, 2 * i + 2);
            maxHeap(array, array.length, i);
        }
    }

    public static void main(String[] args) {
        int[] array = { 4, 8, 5, 6, 12, 4, 3, 11, 1, 10, -10, -8, -3 };

        System.out.println("Before heap:");
        System.out.println(Arrays.toString(array));

        buildMaxHeap(array, 0);

        System.out.println("After heap sort:");
        System.out.println(Arrays.toString(array));
    }
}
