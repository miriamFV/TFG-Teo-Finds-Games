package com.teo_finds_games.miniJuegos.tresEnRaya;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.teo_finds_games.Application;

import java.util.Random;


public class TresEnRayaPlayScreen implements Screen{

    private final Application app;

    private Stage stage;
    private Texture boardTexture, crossTexture, circleTexture;
    private TresEnRayaBoardActor board;
    /*
    There will be at most 5 pieces of each player.
    Dado que el tablero tiene 9 huecos, y el juego acaba cuando un jugador gane o cuando se llene el tablero, como mucho
    podra haber 5 piezas de un jugador (del otro jugador habra 4), es por eso que se crean 5 piezas para el circulo (o1 etc)
    y 5 para la cruz (x1 etc)
     */

    private TresEnRayaPieceActor[] circlesArray = new TresEnRayaPieceActor[5], crossesArray = new TresEnRayaPieceActor[5];


    public static int winnerNumber = 0;
    public static int turn = -1; // -1 player turn,  1 machine turn

    //moveNumber increases when both players make a move.
    private int moveNumber;
    private TextButton backButton;

    public TresEnRayaPlayScreen(Application app) {
        this.app = app;
    }

    @Override
    public void show() {
        moveNumber = 0;
        stage = new Stage(new FitViewport(app.vpWidth,app.vpHeight)){
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    System.exit(0);
                }
                return super.keyDown(keyCode);
            }
        }; //Fitviewport keeps aspect ratio with black bars
        Gdx.input.setInputProcessor(stage);

        initTextures();
        initActors();
        initButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 0.4f, 0.4f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(); //Calls each Actor's act method

        if ( (Gdx.input.isTouched() && Gdx.input.getX()>200) && turn == -1){ //If player's turn, player moves and then machine moves
            Vector2 squareNumber = board.indexClickedSquare(Gdx.input.getX(), Gdx.input.getY());
            if ( board.acceptableSquare(squareNumber) && winnerNumber == 0){
                board.boardMatrix[(int) squareNumber.x][(int) squareNumber.y] = -1;
                circlesArray[moveNumber].putPiece(squareNumber); //Putting object's image in its square. Pone la imagen del objeto en la casilla correspondiente.
                winnerNumber = board.whoWins();
                machineMove();

            }
        }
        if(winnerNumber == -1){ //If player have won
            stage.addAction(Actions.sequence(Actions.delay(2f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    app.tresEnRayaCS.setNumCoins(4);
                    app.setScreen(app.tresEnRayaCS);
                }
            })));
        }else if (winnerNumber == 1 || (winnerNumber == 0 && board.fullBoard())){ //If player loses or ties
            stage.addAction(Actions.sequence(Actions.delay(2f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    app.setScreen(app.tresEnRayaSS);
                }
            })));
        }

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
        boardTexture.dispose();
        crossTexture.dispose();
        circleTexture.dispose();
    }

    public void initTextures(){
        boardTexture = new Texture(Gdx.files.internal("images/tresenraya/board.png"));
        crossTexture = new Texture(Gdx.files.internal("images/tresenraya/crossPiece.png"));
        circleTexture = new Texture(Gdx.files.internal("images/tresenraya/circlePiece.png"));
    }

    public void initActors(){
        //Board Actor
        board = new TresEnRayaBoardActor(boardTexture);
        stage.addActor(board);
        // Circle and cross piece Actors
        for(int i = 0; i < circlesArray.length; i++){
            circlesArray[i] = new TresEnRayaPieceActor(circleTexture);
            stage.addActor(circlesArray[i]);
            crossesArray[i] = new TresEnRayaPieceActor(crossTexture);
            stage.addActor(crossesArray[i]);
        }
    }

    private void initButtons(){
        backButton = new TextButton("ATRAS", app.skin);
        backButton.setSize(150, 100);
        backButton.setPosition(50, app.vpHeight - (backButton.getHeight() + 50));
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });
        stage.addActor(backButton);
    }

    public boolean gameEnded(){
        return board.fullBoard() || board.whoWins() != 0;
    }

    //FUNCIONA, SE PUEDE GANAR A LA IA
    public void machineMove(){
        // For a first random movement of machine
        if (moveNumber == 0) {//Only one player has moved.
            Random r = new Random();
            int f,c;
            do {f = r.nextInt(3);
                c = r.nextInt(3);}
            while(board.boardMatrix[f][c] != 0);

            board.boardMatrix[f][c] = 1;
            crossesArray[moveNumber].putPiece(new Vector2(f, c)); //Putting object's image in its square.
            winnerNumber = board.whoWins(); //Checking if there is winner
            moveNumber++;

        }else if (!gameEnded()){
            int f=0, c=0, v=Integer.MIN_VALUE, aux;
            for(int i=0; i<board.BOARD_SIZE; i++){
                for(int j=0; j<board.BOARD_SIZE; j++){
                    if(board.boardMatrix[i][j]==0){
                        board.boardMatrix[i][j] = 1;
                        aux = min();
                        if (aux>v){
                            v=aux;
                            f=i;
                            c=j;
                        }
                        board.boardMatrix[i][j] = 0;
                    }
                }
            }
            board.boardMatrix[f][c] = 1;
            crossesArray[moveNumber].putPiece(new Vector2(f, c)); //Putting object's image in its square.
            winnerNumber = board.whoWins(); //Checking if there is winner.
            moveNumber++;
        }

    }
    public int max(){
        if( gameEnded() ){
            if( board.whoWins() != 0){
                return -1;
            }else{
                return 0;
            }
        }
        int v = Integer.MIN_VALUE;
        int aux;
        for( int i=0; i<board.BOARD_SIZE; i++){
            for( int j=0; j<board.BOARD_SIZE; j++){
                if( board.boardMatrix[i][j] == 0){
                    board.boardMatrix[i][j] = 1;
                    aux = min();
                    if(aux > v){
                        v = aux;
                    }
                    board.boardMatrix[i][j] = 0;
                }
            }
        }
        return v;
    }

    public int min(){
        if( gameEnded() ){
            if( board.whoWins() != 0){
                return 1;
            }else{
                return 0;
            }
        }
        int v = Integer.MAX_VALUE;
        int aux;
        for( int i=0; i<board.BOARD_SIZE; i++){
            for( int j=0; j<board.BOARD_SIZE; j++){
                if( board.boardMatrix[i][j] == 0){
                    board.boardMatrix[i][j] = -1;
                    aux = max();
                    if(aux < v){
                        v = aux;
                    }
                    board.boardMatrix[i][j] = 0;
                }
            }
        }
        return v;
    }

}
