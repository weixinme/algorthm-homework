package ex2;

public class AssemblyLineScheduling {
    private int[][] a = new int[][]{ {7,9,3,4,8,4}, {8,5,6,4,5,7} };  //装配时间
    private int[][] t = new int[][]{ {2,3,1,3,4},{2,1,2,2,1} };  //跨越时间
    private int[] e = new int[]{ 2,4 };  //进入时间
    private int[] x = new int[]{ 3,2 };  //离开时间
    private int[][] l = new int[2][6];  //经过的站
    private int[][] f = new int[2][6];  //到j的时间

    private int start, end;
    private int minLTRT;  //左上到右上的最短距离
    private int minLTRB;  //左上到右下的最短距离
    private int minLBRT;  //左下到右上的最短距离
    private int minLBRB;  //左下到右下的最短距离
    private int MAX_TIME = 99999;

    public static void main(String[] args) {
        long startTime, endTime;
        AssemblyLineScheduling als = new AssemblyLineScheduling();

        startTime = System.currentTimeMillis();
        als.fastestWay();
        endTime = System.currentTimeMillis();
        System.out.println("，运行时间为 " + (endTime-startTime) + " ms.");

        startTime = System.currentTimeMillis();
        als.fastestWayDC();
        endTime = System.currentTimeMillis();
        System.out.println("，运行时间为 " + (endTime-startTime) + " ms.");


    }

    private AssemblyLineScheduling(){
        start = end = -1;
        minLTRT = minLTRB = minLBRT = minLBRB = MAX_TIME;
    }

    private void fastestWay() {
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

        //输出最优路线
//        System.out.println("line:"+bestLine+" "+"station"+a[0].length);
        int temp = bestLine;
        for(int j=a[0].length-1;j>0;j--) {
        temp = l[temp-1][j];
//        System.out.println("line:" + temp + " " + "station" + j);
        }

        System.out.print("动态规划: 最短时间为 " + bestf);
    }

    private void fastestWayDC()
    {
        AssemblyLineScheduling ret = ALS_DC(0, 5);

        int totalMin = minOf4(e[0] + ret.minLTRT + x[0], e[0] + ret.minLTRB + x[1],
                e[1] + ret.minLBRT + x[0], e[1] + ret.minLBRB + x[1]);

        System.out.print("分治法：最短时间为 " + totalMin);
    }

    private int minOf4(int i1, int i2, int i3, int i4) {
        int ret = MAX_TIME;

        if (i1 < ret) ret = i1;
        if (i2 < ret) ret = i2;
        if (i3 < ret) ret = i3;
        if (i4 < ret) ret = i4;

        return ret;
    }

    private AssemblyLineScheduling MergeWithRight(AssemblyLineScheduling right, int mid){
        AssemblyLineScheduling ret = new AssemblyLineScheduling();
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

    private AssemblyLineScheduling ALS_DC(int start, int end)
    {
        AssemblyLineScheduling ret = new AssemblyLineScheduling();
        if (start == end) {
            ret.start = ret.end = start;
            ret.minLTRT = a[0][start];
            ret.minLTRB = MAX_TIME;
            ret.minLBRT = MAX_TIME;
            ret.minLBRB = a[1][start];

            return ret;
        }
        int mid = (start + end) / 2;

        AssemblyLineScheduling left = ALS_DC(start, mid);
        AssemblyLineScheduling right = ALS_DC(mid + 1, end);
        ret = left.MergeWithRight(right, mid);

        return ret;
    }
}

