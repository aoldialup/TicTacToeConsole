package com;

import java.util.Random;
import java.util.Scanner;

public class Main
{
    static Scanner input = new Scanner(System.in);
    static Random random = new Random();

    static char[][] board;

    final static int BOARD_ROWS = 3;
    final static int BOARD_COLS = 3;

    final static char PLAYER_X = 'X';
    final static char PLAYER_O = 'O';

    final static char BOARD_SPOT_EMPTY = '|';

    final static int SEARCH_DIRECTION_RIGHT = 1;
    final static int SEARCH_DIRECTION_LEFT = -1;

    final static int MAX_PIECES = 9;

    static char currentPlayerSign;

    static int userRow;
    static int userCol;

    static boolean isGameOver = false;
    static boolean isCurrentPlayerWinner = false;

    static int piecesPlaced = 0;


    public static void main(String[] args)
    {
        gameIntro();
        initBoard();
        determineStartingPlayer();

        gameLoop();
        gameOutro();
    }

    private static void gameIntro()
    {
        System.out.println("Welcome to Tic-Tac-Toe!");
    }

    private static void gameOutro()
    {
        System.out.println("Thanks for playing");
    }

    private static void gameLoop()
    {
        while(!isGameOver)
        {
            printBoard();
            askForTurn();
            determineWinner();
            swapTurns();

            if(!isGameOver)
            {
                clearConsole();
            }
        }
    }

    private static void clearConsole()
    {
        System.out.println();
    }

    private static void determineStartingPlayer()
    {
        currentPlayerSign = random.nextInt(1) == 0 ? PLAYER_X : PLAYER_O;
    }

    private static void printBoard()
    {
        System.out.println("1   2   3");

        for(int i = 0; i <= BOARD_ROWS - 1; i++)
        {
            for(int j = 0; j <= BOARD_COLS - 1; j++)
            {
                System.out.printf("%c   ", board[i][j]);
            }

            System.out.println();
        }
    }

    private static void initBoard()
    {
        board = new char[BOARD_ROWS][BOARD_COLS];

        for(int i = 0; i <= BOARD_ROWS - 1; i++)
        {
            for(int j = 0; j <= BOARD_COLS - 1; j++)
            {
                board[i][j] = BOARD_SPOT_EMPTY;
            }
        }
    }

    private static boolean spotExistsOnBoard(int row, int col)
    {
        return row >= 0 && row <= BOARD_ROWS - 1 && col >= 0 && col <= BOARD_COLS - 1;
    }

    private static boolean isSpotAvailable()
    {
        return board[userRow][userCol] == BOARD_SPOT_EMPTY;
    }

    private static boolean pieceBelongsToCurrentPlayer(int row, int col)
    {
        return board[row][col] == currentPlayerSign;
    }

    private static boolean isSpotValid()
    {
        return spotExistsOnBoard(userRow, userCol) && isSpotAvailable();
    }

    private static void askForTurn()
    {
        System.out.println();
        boolean spotValid = false;

        while(!spotValid)
        {
            System.out.printf("ROW,COLUMN [%c]: ", currentPlayerSign);
            String piecePosInput = input.nextLine();

            String[] piecePos = piecePosInput.split(",");

            userRow = Integer.parseInt(piecePos[0]);
            userCol = Integer.parseInt(piecePos[1]);

            userRow -= 1;
            userCol -= 1;

            spotValid = isSpotValid();
        }

        board[userRow][userCol] = currentPlayerSign;
        piecesPlaced++;

        System.out.println();
    }

    private static void swapTurns()
    {
        if(!isGameOver)
        {
            currentPlayerSign = currentPlayerSign == PLAYER_X ? PLAYER_O : PLAYER_X;
        }
    }

    private static boolean verticalWin()
    {
        for(int i = 0; i <= BOARD_ROWS - 1; i++)
        {
            if(!pieceBelongsToCurrentPlayer(i, userCol))
            {
                return false;
            }
        }

        return true;
    }

    private static boolean diagonalWin(int colStart, int colStep, int rowStart, int rowStep)
    {
        while(spotExistsOnBoard(rowStart, colStart))
        {
            if(pieceBelongsToCurrentPlayer(rowStart, colStart))
            {
                rowStart += rowStep;
                colStart += colStep;
            }
            else
            {
                return false;
            }
        }

        return true;
    }

    private static boolean diagonalWin()
    {
        return diagonalWin(BOARD_COLS - 1, SEARCH_DIRECTION_LEFT, 0, SEARCH_DIRECTION_RIGHT) || diagonalWin(BOARD_COLS - 1, SEARCH_DIRECTION_LEFT, BOARD_ROWS - 1, SEARCH_DIRECTION_LEFT);
    }

    private static boolean horizontalWin()
    {
        for(int i = 0; i <= BOARD_COLS - 1; i++)
        {
            if(!pieceBelongsToCurrentPlayer(userRow, i))
            {
                return false;
            }
        }

        return true;
    }

    private static boolean isCurrentPlayerWinner()
    {
        return verticalWin() || horizontalWin() || diagonalWin();
    }

    private static boolean isBoardFull()
    {
        return piecesPlaced == MAX_PIECES;
    }

    private static void determineWinner()
    {
        isCurrentPlayerWinner = isCurrentPlayerWinner();
        isGameOver = isCurrentPlayerWinner() || isBoardFull();

        if(isGameOver)
        {
            printBoard();

            if(isCurrentPlayerWinner)
            {
                System.out.printf("\n%c has won!\n", currentPlayerSign);
            }
            else
            {
                System.out.println("TIE!");
            }
        }
    }
}