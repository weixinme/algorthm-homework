package ex1;

import java.util.Random;

public class MaxSubarray {

    private static int maximum(int a, int b, int c)
    {
        if (a>=b && a>=c)
            return a;
        else if (b>=a && b>=c)
            return b;
        return c;
    }

    private static int maxCrossingSubarray(int[] ar, int low, int mid, int high)
    {
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        int i;

        for (i=mid; i>=low; i--)
        {
            sum = sum + ar[i];
            if (sum>leftSum)
                leftSum = sum;
        }

        int rightSum = Integer.MIN_VALUE;
        sum = 0;

        for (i=mid+1; i<=high; i++)
        {
            sum=sum+ar[i];
            if (sum>rightSum)
                rightSum = sum;
        }

        return (leftSum+rightSum);
    }


    private static int maxSumSubarray(int[] ar, int low, int high)
    {
        if (high == low)
            return ar[high];

        int mid = (high+low)/2;

        int maximumSumLeftSubarray = maxSumSubarray(ar, low, mid);
        int maximumSumRightSubarray = maxSumSubarray(ar, mid+1, high);
        int maximumSumCrossingSubarray = maxCrossingSubarray(ar, low, mid, high);

        return maximum(maximumSumLeftSubarray, maximumSumRightSubarray, maximumSumCrossingSubarray);
    }


    private static int maxSubarrayBruteForce(int[] arr)
    {
        int N = arr.length, max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++)
        {
            int sum = 0;
            for (int j = i; j < N; j++)
            {
                sum += arr[j];
                if (sum > max)
                    max = sum;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int[] a = new int[5000];
        for (int i=0; i<a.length; i++)
            a[i] = random.nextInt(40) - 20;
        long startTime, endTime;

        startTime = System.currentTimeMillis();
        int max_1 = maxSumSubarray(a, 0, a.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("Divide and conquer: max sum is " + max_1 + ", and run time is " + (endTime-startTime) + " ms.");

        startTime = System.currentTimeMillis();
        int max_2 = maxSubarrayBruteForce(a);
        endTime = System.currentTimeMillis();
        System.out.println("Brute force: max sum is " + max_2 + ", and run time is " + (endTime-startTime) + " ms.");
    }
}

