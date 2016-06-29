package com.teo_finds_games.miniJuegos.tresEnRaya;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.teo_finds_games.Application;

public class TresEnRayaBoardActor extends Actor {

    /*
    The matrix' indexes of the board are: (column,row)
    El tablero sera de la siguiente forma: (columna,fila)
    (0,2) | (1,2) | (2,2)
    ______|_______|______
    (0,1) | (1,1) | (2,1)
    ______|_______|______
    (0,0) | (1,0) | (2,0)
          |       |
     */

    public static int[][] boardMatrix = new int[3][3];
    public static final int BOARD_SIZE = boardMatrix.length;
    public static Vector2 boardPosition;

    /* The board is square so we only need boardSideLength instead of boardWidth and boardHeight.
    For each square we need squareSideLength instead of squareWidth and squareHeight too.*/
    public static int boardSideLength, squareSideLength;
    private Texture boardTexture;

    public TresEnRayaBoardActor(Texture boardTexture) {
        emptyBoard(); //We fill the matrix with zeros, 0 means empty square, Llenamos la matriz con 0 (que significa casilla vacia)
        this.boardTexture = boardTexture;
        /*
        Si quisieramos que el juego se pueda ejecutar tanto en posicion horizontal (landscape) como vertical (portrait)
        if(Application.vpWidth < Application.vpHeight){
            boardSideLength = Application.vpWidth - 100;
        }else{
            boardSideLength = Application.vpHeight - 100;
        }
        */
        boardSideLength = Application.vpHeight - 100;
        squareSideLength = boardSideLength/3;

        boardPosition = new Vector2((Application.vpWidth - boardSideLength) / 2, (Application.vpHeight - boardSideLength) / 2);
        setPosition(boardPosition.x, boardPosition.y);
        setSize(boardSideLength, boardSideLength);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(boardTexture, getX(), getY(), boardSideLength, boardSideLength);
    }

    public void emptyBoard() {//start game
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardMatrix[i][j] = 0;
            }
        }
        TresEnRayaPlayScreen.winnerNumber = 0;
    }

    public boolean fullBoard(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if(boardMatrix[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    public Vector2 indexClickedSquare(int x, int y) {
        //Returns the matrix' indexes of the clicked square

        Vector2 square;
        int squareX = (int) (x - boardPosition.x) / squareSideLength;
        int squareY = (int) ((Application.vpHeight - y) - boardPosition.y) /squareSideLength;

        if ((squareX < 0 || squareX > 3) || (squareY < 0 || squareY > 3)) {
            //If you have clicked out of the board's lateral boundaries. Se ha pulsado fuera del tablero (laterales)
            square = new Vector2(-1, -1);
        } else {
            square = new Vector2(squareX, squareY);
        }
        return square;
    }

    public boolean insideBoard(Vector2 squareIndexes) {
        if (squareIndexes != new Vector2(-1, -1)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean emptySquare(Vector2 casilla) {
        if (boardMatrix[((int) casilla.x)][(int) casilla.y] == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean acceptableSquare(Vector2 casilla) {
        return insideBoard(casilla) && emptySquare(casilla);
    }

    public int whoWins() {
        /*
        It returns:
           -1 if player wins
           0 if tie
           1 if machine wins
        */

        // Diagonal \
        if (boardMatrix[0][0] != 0 && (boardMatrix[0][0] == boardMatrix[1][1]) && (boardMatrix[0][0] == boardMatrix[2][2])) {
            return boardMatrix[0][0];

        } else if (boardMatrix[0][2] != 0 && (boardMatrix[0][2] == boardMatrix[1][1]) && (boardMatrix[0][2] == boardMatrix[2][0])) { // Diagonal /
            return boardMatrix[0][2];

        } else { //Horizontal and vertical
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (boardMatrix[i][0] != 0 && (boardMatrix[i][0] == boardMatrix[i][1]) && (boardMatrix[i][0] == boardMatrix[i][2])) {
                    return boardMatrix[i][0];
                } else if (boardMatrix[0][i] != 0 && (boardMatrix[0][i] == boardMatrix[1][i]) && (boardMatrix[0][i] == boardMatrix[2][i])) {
                    return boardMatrix[0][i];
                }
            }
            return 0;
        }
    }

}
