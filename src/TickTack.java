import java.util.Scanner;


public class TickTack {
    static final int X =1, O=2;
    static final int NUM_COLUMNS = 3;
    static final int NUM_IN_ROW=3;
    static int firstplayer;
    static int p1=0,p2=0;
    static long recursiveCalls =0;

    public static void main(String[] args) {

        for (int i=0; i<NUM_COLUMNS; i++) {
            int[ ][ ] board = new int[NUM_COLUMNS][NUM_COLUMNS];
            firstplayer = X;
            p1=0;p2=0;
            recursiveCalls =0;
            board[i][i]= X;
            Play(board , O);
            System.out.println ("NetWins for column "+i+" : "+Math.abs(p1-p2));
            System.out.println("X-wins:  "+p1 +" O-Wins:"+p2);
            System.out.println ("Recursion calls: " + recursiveCalls);
        }
    }



    public static int Play(int[][] board, int player) {
        recursiveCalls++;


        int checkWinner=checkBoard(board,player);
        // 0 - board full, 1- X wins  2 = O wins   3-keep playing
        if (checkWinner < 3) {
            if (checkWinner == 0) {
                return 0;
            } else {
                if (checkWinner == firstplayer) {
                    p1++;
                    return 1;
                } else {
                    p2++; return -1;
                }
            }
        }
        checkWinner = 0;

        //   update the board for this move

        for (int row = 0; row < NUM_COLUMNS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                //spot in question
                if (board[row][col]==0){
                    int[][] clonelist = new int[NUM_COLUMNS][NUM_COLUMNS];
                    for (int row2 = 0; row2 < NUM_COLUMNS; row2++) {
                        for (int col2 = 0; col2 < NUM_COLUMNS; col2++) {
                            clonelist[row2][col2]=board[row2][col2];
                        }
                    }
                    clonelist[row][col]=player;
                    Play(clonelist,3-player);
                }
            }



        }
        //  recursively call Play
        return checkWinner;
    }






    public static boolean isFull(int[][] inlist){
        boolean empty = true;
        for (int i = 0 ; i<NUM_COLUMNS ; i++ ) {
            for (int i2 = 0 ; i2<NUM_COLUMNS ; i2++ ) {
                if (inlist[i][i2] ==0   ) {
                    empty = false;
                    break;
                }
            }
        }
        return empty;
    }

    public static int checkBoard(int[][] board ,int clr){
        int chkclr = 3-clr;

        for (int row = 0 ; row<NUM_COLUMNS; row++ ) {
            int colcnt = 0;
            for (int col=0; col<NUM_COLUMNS; col++) {
                if (board[row][col] == chkclr) {
                    colcnt++;
                    if (colcnt == NUM_IN_ROW)  {
                        return chkclr;
                    }
                }  else {
                    colcnt =0;
                }
            }
        }
        for (int row = 0 ; row<NUM_COLUMNS; row++ ) {
            int colcnt = 0;
            for (int col=0; col<NUM_COLUMNS; col++) {
                if (board[col][row] == chkclr) {
                    colcnt++;
                    if (colcnt == NUM_IN_ROW)  {
                        return chkclr;
                    }
                }  else {
                    colcnt =0;
                }
            }
        }
        int colcnt = 0;
        for (int i = 0 ; i<NUM_COLUMNS; i++ ) {
            if (board[i][i] == chkclr) {
                colcnt++;
                if (colcnt == NUM_IN_ROW)  {
                    return chkclr;
                }
            }  else {
                colcnt =0;
            }
        }
        colcnt = 0;
        for (int i = 0 ; i<NUM_COLUMNS; i++ ) {
            if (board[NUM_COLUMNS-1-i][i] == chkclr) {
                colcnt++;
                if (colcnt == NUM_IN_ROW)  {
                    return chkclr;
                }
            }  else {
                colcnt =0;
            }
        }
        if (isFull(board)) {
            return 0;
        } else {
            return 3;
        }


    }
    public static void printlist(int[][] inlist) {
        for (int i =0; i<inlist.length; i++) {
            for (int j =0; j<inlist.length; j++) {
                System.out.print(inlist[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

}