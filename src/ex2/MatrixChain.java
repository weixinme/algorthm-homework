package ex2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MatrixChain {

    public static void main(String[] args) {
        Random random = new Random();
        int n = random.nextInt(5) + 4;
        Integer[] p = new Integer[n + 1];
        for (int i = 0; i < p.length; i++)
            p[i] = random.nextInt(200) + 5;

        System.out.println("矩阵数量为 " + n);
        System.out.println("矩阵行列数为 " + Arrays.toString(p));

        //动态规划
        int[][] m = new int[n+1][n+1];
        int[][] s = new int[n+1][n+1];

        matrixChain(p,n,m,s);
        System.out.println("动态规划：最小连乘数为 " + m[1][n]);
        Traceback(s,1,n);
        System.out.println();


        //贪心法
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(p));
        int[] result = new int[n - 1];

        for(int j = 0; j < n -1 ; j++){
            int max = 0, temp = 1;
            //不包括头尾
            for(int i=1; i < list.size() - 1; i++){
                if(list.get(i) > max){
                    max = list.get(i);
                    temp = i;//得到最大的那个值的下标
                }
            }

            if(list.size() > 3){
                result[j]=list.get(temp - 1) * list.get(temp) * list.get(temp + 1);
                list.remove(temp);
            }else {
                result[j] = list.get(0)* list.get(1)* list.get(2);
            }

        }

        int sum = 0;
        for(int num: result){
            sum += num;
        }
        System.out.println("贪心法：最小连乘数为 " + sum);

    }

    private static void Traceback(int[][] s, int i,int j){//递归构造最优解
        if(i == j)
            System.out.print("A" + i);
        else {
            System.out.print("(");
            Traceback(s, i, s[i][j]);
            Traceback(s, s[i][j] + 1, j);
            System.out.print(")");
        }
    }

    private static void matrixChain(Integer[] p,int n,int[][] m,int[][] s) {
        for(int i = 1;i <= n;i++){//初始化,矩阵长度为1时，从i到i的矩阵连乘子问题只有一个矩阵，操作次数是0
            m[i][i] = 0;
        }
        for(int r = 2;r <= n;r++){//矩阵的的长度，从长度2开始逐渐边长。
            for(int i = 1;i <= n-r+1;i++){//从第i个矩阵开始，长度为r，则矩阵为（Ai-A(i+r-1)）
                int j = i+r-1;
                m[i][j] = m[i+1][j] + p[i-1]*p[i]*p[j];
                s[i][j] = i;//断开点的索引
                for(int k = i+1;k < j;k++){//k从i+1循环找m[i][j]的最小值
                    int t = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j];
                    if(t < m[i][j]){//找到比原来的断开点更小的值
                        m[i][j] = t;
                        s[i][j] = k;//最小值的断开点
                    }
                }
            }
        }
    }
}
