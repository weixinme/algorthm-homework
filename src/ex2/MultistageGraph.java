package ex2;

import java.util.ArrayList;

public class MultistageGraph {
    public static void main(String[] args) {
        int d;
        Node[] n = new Node[16];
        int[][] a = new int[16][16];

        for (int i = 0; i < 16; i++)
            n[i] = new Node(i);
        n[0].value = 0;

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++)
                a[i][j] = 0;
        }
        a[0][1] = 5;  a[0][2] = 3;
        a[1][3] = 1;  a[1][4] = 3;  a[1][5] = 6;
        a[2][4] = 8;  a[2][5] = 7;  a[2][6] = 6;
        a[3][7] = 6;  a[3][8] = 8;
        a[4][7] = 3;  a[4][8] = 5;
        a[5][8] = 3;  a[5][9] = 3;
        a[6][8] = 8;  a[6][9] = 4;
        a[7][10] = 2;  a[7][11] = 2;
        a[8][11] = 1;  a[8][12] = 2;
        a[9][11] = 3;  a[9][12] = 3;
        a[10][13] = 3;  a[10][14] = 5;
        a[11][13] = 5;  a[11][14] = 2;
        a[12][13] = 6;  a[12][14] = 6;
        a[13][15] = 4;
        a[14][15] = 3;


        //从矩阵a的第一行开始，一行行找相连的节点
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                //找到了相连节点
                if(a[i][j] != 0){
                    //上一个节点的最短路径的值+与下一个节点相连路径上的值
                    d = n[i].value + a[i][j];
                    //判断是否比原先的值要小，如果小就将0-j节点的长度替换
                    if(d < n[j].value){
                        n[j].value = d;
                        //记录前一个节点的序号
                        n[j].prev = n[i];
                    }
                }
            }
        }

        System.out.print("最短路径为：");

        ArrayList<Integer> res = new ArrayList<>();
        Node node = n[15];
        while (node != null){
            res.add(node.number);
            node = node.prev;
        }
        for(int i = res.size() - 1; i >= 0; i--)
            System.out.print(res.get(i) + " ");
        System.out.println();

        System.out.println("最短长度为：" + n[15].value);

    }
}


class Node{
    public int number;
    public int value = Integer.MAX_VALUE;

    public Node prev = null;

    Node(int num){
        number = num;
    }
}