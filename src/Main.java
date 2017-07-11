/**
 * Created by Samin on 7/2/17.
 */

import java.util.Arrays;
import java.util.Scanner;

public class Main{
    //global variables
    final static int NUM_COLUMNS = 3;
    final static int NUM_ROWS =3;
    final static int BOTTOM_ROW = NUM_COLUMNS - 1;
    public static int recursiveCalls=0;
    static int firstPlayer;
    static int player1Wins =0, player2Wins =0;

    //game board
    static int[][] board = new int[NUM_COLUMNS][NUM_ROWS];

    //creates scanner
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){




//        for (int i=0; i<NUM_COLUMNS; i++) {
//            //int[ ][ ] board = new int[NUM_COLUMNS][NUM_COLUMNS];
//            firstPlayer = 1;
//            recursiveCalls =0;
//            board[i][i]= firstPlayer;
//            System.out.println ("NetWins: for col "+i + " : "+Play(board,0));
//            System.out.println("X-wins:  "+player1Wins +" O-Wins:"+player2Wins);
//            System.out.println ("Recursion calls: " + recursiveCalls);
//            System.out.println ("************************");
//            System.out.println ();
//
//        }



        CreateBoard();
        while (true){
            PrintBoard();
            DropChip(0,1);

        }



    }

    public static void PlayGame(){
        //creates board
        CreateBoard();

        //tells player how to play
        System.out.println("Choose what column you want");

        //displays board
        PrintBoard();

        //creates boolean to determine status of game
        boolean flag = true;

        int Xwins =0;
        int Owins =0;




        //main game loop
        while(flag){
            //activates player 1s turn, then prints board
            int turnCount =0;
            int totalSpots = NUM_COLUMNS * NUM_ROWS;
            for (int i = 0; i < totalSpots; i++) {
                DropX();
                PrintBoard();
                //determines if player 1 has won
                if(CheckWinner(1)){
                    flag = false; //sets flag to false so loop is not repeated if player 1 won
                    Xwins++;
                    ResetBoard();

                    break; //break to skip player 2s turn if won
                }

                //activates player 2s turn, then prints board
                DropO();
                PrintBoard();

                //determines if player 1 has won
                if(CheckWinner(2)){
                    flag = false; //sets flag to false so loop is not repeated if player 2 won
                    Owins++;
                    ResetBoard();
                    break; // break for consistency
                }
            }
        }
    }


    public static int Play(int[][] board,int player) {
        recursiveCalls++;
        // 0 - board full, 1- X wins  2 = O wins   3-keep playing
        int checkWinner = CheckBoard(board);
        if (checkWinner < 3) {
            if (checkWinner == 0) {
                return 0;
            } else {
                if (checkWinner == 1) {
                    player1Wins++;
                    return 1;
                } else if (checkWinner == 2) {
                    player2Wins++;
                    return -1;
                }
            }
        }
        checkWinner = 0;


        for (int col = 0; col < NUM_COLUMNS; col++) {
            if (board[col][col]==0) {
                int[][] clonelist = new int[NUM_COLUMNS][NUM_COLUMNS];
                for (int row2 = 0; row2 < NUM_COLUMNS; row2++) {
                    for (int col2 = 0; col2 < NUM_COLUMNS; col2++) {
                        clonelist[row2][col2] = board[row2][col2];
                    }
                }
                clonelist[col][col] = player;
            }


        }
            for (int col = 0; col < NUM_ROWS; col++) {
                if (!colFull(board, col) && !CheckWinner(1) && !CheckWinner(2)) {
                    int turnCount = 0;
                    if (turnCount % 2 == 0) {
                        Play(board, 1);
                    } else {
                        Play(board, 2);
                    }
                }
            }

            //default
            return checkWinner;

        }




    public static boolean isFull(int[][] inlist){
        boolean empty = true;
        for (int i = 0 ; i<NUM_COLUMNS ; i++ ) {
            for (int i2 = 0 ; i2<NUM_COLUMNS ; i2++ ) {
                if (inlist[i][i2] ==0 ) {
                    empty = false;
                    break;
                }
            }
        }
        return empty;
    }

    public static boolean colFull(int[][] board,int column){

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (col==column&& board[row][col]!=0){
                    return true;
                }
            }
        }
        return false;

    }



    public static void ResetBoard(){
        for (int row = 0; row < NUM_COLUMNS; row++) {
            for (int col = 0; col < NUM_ROWS; col++) {
                board[row][col]=0;
            }
        }
    }

    public static void CreateBoard() {
        //fills board with 0 for the width and height
        for (int w = 0; NUM_COLUMNS > w; w += 1) {
            for (int h = 0; NUM_ROWS > h; h += 1) {
                board[w][h] = 0;
            }
        }
    }

    public static void PrintBoard() {
        //prints the board
        for (int w = 0; NUM_COLUMNS > w; w ++) {
            for (int h = 0; NUM_ROWS > h; h ++ ) {
                System.out.print(board[w][h]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void DropX(){
        //creates a counter
        int counter = 1;

        //shows whos turn
        System.out.println("Player 1 turn");

        //gets input
        int column = scanner.nextInt();

        while(true){
            if(column > NUM_COLUMNS){
                System.out.println("That's not a valid column");
                break;
            }

            if (board[BOTTOM_ROW][column] == 0) { //checks to see if space is blank, puts X there if it is
                board[BOTTOM_ROW][column] = 1;
                break; //breaks loop after placing
            }else if(board[BOTTOM_ROW][column] == 1 || board[BOTTOM_ROW][column] ==2){ //if space isn't blank, checks to see if one above is
                if(board[BOTTOM_ROW - counter][column] == 0){ //puts X if blank
                    board[BOTTOM_ROW - counter][column] = 1;
                    break; //breaks loop after placing
                }
            }
            counter += 1; //adds one to counter if the space wasn't blank, then loops again
            if(counter == NUM_COLUMNS){ //checks to see if at end of column
                System.out.println("That column is full");
                break;
            }
        }
    }

    //attempts to drop a chip in directed column
    // drops assigned player chp and retruns true
    // if column full retruns false
    public static boolean DropChip(int column,int player){
        //creates a counter
        int counter = 1;
        while(true){
            if (board[BOTTOM_ROW][column] == 0) { //checks to see if space is blank, puts X there if it is
                board[BOTTOM_ROW][column] = player;
                return true;
            }else if(board[BOTTOM_ROW][column] ==1 || board[BOTTOM_ROW][column] ==2){ //if space isn't blank, checks to see if one above is
                if(board[BOTTOM_ROW - counter][column] == 0){ //puts X if blank
                    board[BOTTOM_ROW - counter][column] = player;
                    return true;
                }
            }
            counter += 1; //adds one to counter if the space wasn't blank, then loops again
            if(counter == NUM_COLUMNS){ //checks to see if at end of column
                //System.out.println("That column is full");
                return false;
            }
        }
    }

    public static void DropX(int col){
        //creates a counter
        int counter = 1;

        //shows whos turn
        System.out.println("Player 1 turn");

        //gets input
        int column = col;

        while(true){
            if(column > NUM_COLUMNS){
                System.out.println("That's not a valid column");
                break;
            }

            if (board[BOTTOM_ROW][column] == 0) { //checks to see if space is blank, puts X there if it is
                board[BOTTOM_ROW][column] = 1;
                break; //breaks loop after placing
            }else if(board[BOTTOM_ROW][column] ==1 || board[BOTTOM_ROW][column] ==2){ //if space isn't blank, checks to see if one above is
                if(board[BOTTOM_ROW - counter][column] == 0){ //puts X if blank
                    board[BOTTOM_ROW - counter][column] = 1;
                    break; //breaks loop after placing
                }
            }
            counter += 1; //adds one to counter if the space wasn't blank, then loops again
            if(counter == NUM_COLUMNS){ //checks to see if at end of column
                System.out.println("That column is full");
                break;
            }
        }
    }



    public static void DropO(){
        //creates a counter
        int counter = 1;

        //shows whos turn
        System.out.println("Player 2 turn");

        //gets input
        int column = scanner.nextInt();

        while(true){
            if(column > NUM_COLUMNS){
                System.out.println("That's not a valid column");
                break;
            }

            if (board[BOTTOM_ROW][column] == 0) { //checks to see if space is blank, puts O there if it is
                board[BOTTOM_ROW][column] =2;
                break; //breaks loop after placing
            }else if(board[BOTTOM_ROW][column] == 1 || board[BOTTOM_ROW][column] ==2){ //if space isn't blank, checks to see if one above is
                if(board[BOTTOM_ROW - counter][column] == 0){ //puts O if blank
                    board[BOTTOM_ROW - counter][column] =2;
                    break; //breaks loop after placing
                }
            }
            counter += 1; //adds one to counter if the space wasn't blank, then loops again
            if(counter == NUM_COLUMNS){ //checks to see if at end of column
                System.out.println("That column is full");
                break;
            }
        }
    }

    public static void DropO(int col){
        //creates a counter
        int counter = 1;

        //shows whos turn
        System.out.println("Player 2 turn");

        //gets input
        int column = col;

        while(true){
            if(column > NUM_COLUMNS){
                System.out.println("That's not a valid column");
                break;
            }

            if (board[BOTTOM_ROW][column] == 0) { //checks to see if space is blank, puts O there if it is
                board[BOTTOM_ROW][column] =2;
                break; //breaks loop after placing
            }else if(board[BOTTOM_ROW][column] == 1 || board[BOTTOM_ROW][column] ==2){ //if space isn't blank, checks to see if one above is
                if(board[BOTTOM_ROW - counter][column] == 0){ //puts O if blank
                    board[BOTTOM_ROW - counter][column] =2;
                    break; //breaks loop after placing
                }
            }
            counter += 1; //adds one to counter if the space wasn't blank, then loops again
            if(counter == NUM_COLUMNS){ //checks to see if at end of column
                System.out.println("That column is full");
                break;
            }
        }
    }


    public static int CheckBoard(int[][] board ,int check){

        if (isFull(board)){
            return 0;
        }
        if (CheckWinner(1)){
            return 1;
        }else if (CheckWinner(2)){
            return 2;
        }else{
            return 3;
        }
    }

    public static int CheckBoard(int[][] board){

        if (isFull(board)){
            return 0;
        }
        if (CheckWinner(1)){
            return 1;
        }else if (CheckWinner(2)){
            return 2;
        }else{
            return 3;
        }
    }


    public static boolean CheckWinner(int player){

        // horizontalCheck

        for (int j = 0; j<NUM_ROWS-3 ; j++ ){
            for (int i = 0; i<NUM_COLUMNS; i++){

                if (board[i][j] == player && board[i][j+1] == player && board[i][j+2] == player && board[i][j+3] == player){
                    return true;
                }
            }
        }
        // verticalCheck
        for (int i = 0; i<NUM_COLUMNS-3 ; i++ ){
            for (int j = 0; j<NUM_ROWS; j++){

                if (board[i][j] == player && board[i+1][j] == player && board[i+2][j] == player && board[i+3][j] == player){
                    return true;
                }
            }
        }
        // ascendingDiagonalCheck
        for (int i=3; i<NUM_COLUMNS; i++){
            for (int j=0; j<NUM_ROWS-3; j++){

                if (board[i][j] == player && board[i-1][j+1] == player && board[i-2][j+2] == player && board[i-3][j+3] == player)
                    return true;
            }
        }
        // descendingDiagonalCheck
        for (int i=3; i<NUM_COLUMNS; i++){
            for (int j=3; j<NUM_ROWS; j++){

                if (board[i][j] == player && board[i-1][j-1] == player && board[i-2][j-2] == player && board[i-3][j-3] == player)
                    return true;
            }
        }
        return false;
    }

    public static int checkBoard(int[][] board ,int check){
        int chkclr = 3-check;

        for (int row = 0 ; row<NUM_COLUMNS; row++ ) {
            int colcnt = 0;
            for (int col=0; col<NUM_COLUMNS; col++) {
                if (board[row][col] == chkclr) {
                    colcnt++;
                    if (colcnt == NUM_ROWS)  {
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
                    if (colcnt == NUM_ROWS)  {
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
                if (colcnt == NUM_ROWS)  {
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
                if (colcnt == NUM_ROWS)  {
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


}