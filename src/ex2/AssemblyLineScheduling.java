package ex2;

public class AssemblyLineScheduling {
    public static void main(String[] args) {
        int[][] a = new int[][]{ {7,9,3,4,8,4}, {8,5,6,4,5,7} };  //装配时间
        int[][] t = new int[][]{ {2,3,1,3,4},{2,1,2,2,1} };  //跨越时间
        int[] e = new int[]{ 2,4 };  //进入时间
        int[] x = new int[]{ 3,2 };  //离开时间
        int[][] l = new int[2][6];  //经过的站
        int[][] f = new int[2][6];  //到j的时间

        long startTime, endTime;

        startTime = System.currentTimeMillis();
        int best = fastestWay(a,f,t,e,x,l);
        endTime = System.currentTimeMillis();
        System.out.println("Dynamic Programming: total minimum time is " + best + ", and run time is " + (endTime-startTime) + " ms.");

        startTime = System.currentTimeMillis();
        MinTimeContainer container = new MinTimeContainer();
        container.fastestWayDC();
        endTime = System.currentTimeMillis();
        System.out.println(", and run time is " + (endTime-startTime) + " ms.");

    }

    private static int fastestWay(int[][] a, int[][] f, int[][] t, int[] e, int[] x, int[][] l) {
        int bestf, bestLine;
        f[0][0] = e[0] + a[0][0];
        f[1][0] = e[1]+a[1][0];
        for(int j=1; j<a[0].length; j++) {
            if(f[0][j-1]+a[0][j]<=f[1][j-1]+t[1][j-1]+a[0][j]) {
                f[0][j] = f[0][j-1] + a[0][j];
                l[0][j] = 1;
            }
            else {
                f[0][j]=f[1][j-1]+t[1][j-1]+a[0][j];
                l[0][j] = 2;
            }
            if(f[1][j-1]+a[1][j]<=f[0][j-1]+t[0][j-1]+a[1][j]) {
                f[1][j] = f[1][j-1] + a[1][j];
                l[1][j] = 2;
            }
            else {
                f[1][j] = f[0][j-1]+t[0][j-1]+a[1][j];
                l[1][j]=1;
            }
        }
        if(f[0][a[0].length-1]+x[0]<=f[1][a[0].length-1]+x[1]) {
            bestf = f[0][a[0].length-1] + x[0];
            bestLine=1;
        }
        else{
            bestf = f[1][a[0].length-1] + x[1];
            bestLine = 2;
        }

        int temp = bestLine;
        for(int j=a[0].length-1;j>0;j--) {
        temp = l[temp-1][j];

        }

        return bestf;
    }
    
}


class MinTimeContainer {
    private int[][] a = new int[][]{ {7,9,3,4,8,4}, {8,5,6,4,5,7} };  //装配时间
    private int[][] t = new int[][]{ {2,3,1,3,4},{2,1,2,2,1} };  //跨越时间
    private int[] e = new int[]{ 2,4 };  //进入时间
    private int[] x = new int[]{ 3,2 };  //离开时间

    private int start, end;
    private int minLTRT;  //mininmum time from left-top(S<1,start>) to right-top(S<1,end>)
    private int minLTRB;  //mininmum time from left-top(S<1,start>) to right-bottom(S<2,end>)
    private int minLBRT;  //mininmum time from left-bottom(S<2,start>) to right-top(S<1,end>)
    private int minLBRB;  //mininmum time from left-bottom(S<2,start>) to right-bottom(S<2,end>)

    private int MAX_TIME = 99999;

    private int minOf4(int i1, int i2, int i3, int i4) {
        int ret = MAX_TIME;

        if (i1 < ret) ret = i1;
        if (i2 < ret) ret = i2;
        if (i3 < ret) ret = i3;
        if (i4 < ret) ret = i4;

        return ret;
    }

    MinTimeContainer() {
        start = end = -1;
        minLTRT = minLTRB = minLBRT = minLBRB = MAX_TIME;
    }

    private MinTimeContainer MergeWithRight(MinTimeContainer right, int mid){
        MinTimeContainer ret = new MinTimeContainer();
        ret.minLTRT = minOf4(minLTRT + right.minLTRT, minLTRT + t[0][mid] + right.minLBRT,
                minLTRB + t[1][mid] + right.minLTRT, minLTRB + right.minLBRT);
        ret.minLTRB = minOf4(minLTRT + right.minLTRB, minLTRT + t[0][mid] + right.minLBRB,
                minLTRB + t[1][mid] + right.minLTRB, minLTRB + right.minLBRB);
        ret.minLBRT = minOf4(minLBRT + right.minLTRT, minLBRT + t[0][mid] + right.minLBRT,
                minLBRB + t[1][mid] + right.minLTRT, minLBRB + right.minLBRT);
        ret.minLBRB = minOf4(minLBRT + right.minLTRB, minLBRT + t[0][mid] + right.minLBRB,
                minLBRB + t[1][mid] + right.minLTRB, minLBRB + right.minLBRB);
        ret.start = start;
        ret.end = right.end;

        return ret;
    }

    private MinTimeContainer ALS_DC(int start, int end)
    {
        MinTimeContainer ret = new MinTimeContainer();
        if (start == end) {
            ret.start = ret.end = start;
            ret.minLTRT = a[0][start];
            ret.minLTRB = MAX_TIME;
            ret.minLBRT = MAX_TIME;
            ret.minLBRB = a[1][start];

            return ret;
        }
        int mid = (start + end) / 2;

        MinTimeContainer left = ALS_DC(start, mid);
        MinTimeContainer right = ALS_DC(mid + 1, end);
        ret = left.MergeWithRight(right, mid);

        return ret;
    }

    void fastestWayDC()
    {
        MinTimeContainer ret = ALS_DC(0, 5);

        int totalMin = minOf4(e[0] + ret.minLTRT + x[0], e[0] + ret.minLTRB + x[1],
                e[1] + ret.minLBRT + x[0], e[1] + ret.minLBRB + x[1]);

        System.out.print("Divide and Conquer: total minimum time is " + totalMin);
    }

}
