package com.teo_finds_games.miniJuegos.rompecabezas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public class RompecabezasPlayScreen implements Screen {

    private final Application app;
    private Stage stage;
    //Game grid
    private int boardSize = 4;
    private int holeX, holeY;

    private TextButton backButton;
    private TextureRegion[] textureRegionArray = new TextureRegion[boardSize*boardSize];
    private RompecabezasPiece[][] puzzleBoard;


    public RompecabezasPlayScreen(Application app){
        this.app = app;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera)){
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    System.exit(0);
                }
                return super.keyDown(keyCode);
            }
        };
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        initButtons();
        initTextureRegionArray();
        initGrid();
    }

    public void update(float delta){
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.5f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
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
    }

    public void initButtons() {
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

    public void initTextureRegionArray(){
        for(int row=0; row<boardSize; row++){
            for(int column = 0; column<boardSize; column++){
                String path = "images/rompecabezas/husky"+row+column+".png";
                textureRegionArray[boardSize*row + column] = new TextureRegion(new Texture(Gdx.files.internal(path)));
            }
        }
    }

    //Initialize the game grid
    private void initGrid(){
        int pieceWidth = app.vpHeight/5; //piece width
        int pieceHeight = pieceWidth; //piece height

        Array<Integer> nums = new Array<Integer>(); //Each piece has a number to identify its original position
        puzzleBoard = new RompecabezasPiece[boardSize][boardSize];

        // Initialize the grid array
        for(int i = 1; i < boardSize * boardSize; i++) {
            nums.add(i);
        }
        nums.shuffle();

        // Set the hole at the bottom right so the sequence is 1,2,3...,15,hole (solved state) from which to start shuffling.
        holeX = 3;
        holeY = 3;
        for(int row = 0; row < boardSize; row++) {
            for(int column = 0; column < boardSize; column++) {
                if(row != holeY || column != holeX) {
                    int num = nums.removeIndex(0);
                    puzzleBoard[row][column] = new RompecabezasPiece(textureRegionArray[num], num); //We take a random image for each piece (without repetitions)
                    puzzleBoard[row][column].setSize(pieceWidth, pieceHeight);
                    puzzleBoard[row][column].setPosition(app.vpWidth / 2 + (column - 2) * (pieceWidth + 2), app.vpHeight / 2 + (((boardSize - 1) - row) - 2) * (pieceHeight + 2));
                    puzzleBoard[row][column].addAction(sequence(alpha(0), delay((column + 1 + (row * boardSize))/15f),
                            parallel(fadeIn(0.5f), moveBy(0, -10, 0.25f, Interpolation.pow5Out))));

                    //Slide/Move Piece
                    puzzleBoard[row][column].addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            int pieceX = 0, pieceY = 0;
                            boolean pieceFound = false;
                            RompecabezasPiece pieceSelected = (RompecabezasPiece) event.getListenerActor();
                            for(int i = 0; i<boardSize && !pieceFound ; i++){
                                for(int j = 0; j< boardSize && !pieceFound ; j++){
                                    if( puzzleBoard[i][j] != null && pieceSelected == puzzleBoard[i][j]){
                                        pieceX = j;
                                        pieceY = i;
                                        pieceFound = true;
                                    }
                                }
                            }

                            //We can slide/move if...
                            if (holeX == pieceX || holeY == pieceY){
                                moveButtons(pieceX, pieceY);

                                if(solutionFound()){
                                    app.rompecabezasCS.setNumCoins(10);
                                    app.setScreen(app.rompecabezasCS);
                                }
                            }else{
                                //invalid movement
                            }
                        }
                    });
                    stage.addActor(puzzleBoard[row][column]);
                }
            }
        }
    }


    private void moveButtons(int x, int y){
        RompecabezasPiece piece;
        if(x < holeX) { //same row, column lower
            for (; holeX > x; holeX--) { //Move the hole to the left
                piece = puzzleBoard[holeY][holeX - 1];
                piece.addAction(moveBy(piece.getWidth() + 2, 0, 0.5f, Interpolation.pow5Out));
                puzzleBoard[holeY][holeX] = piece;
                puzzleBoard[holeY][holeX - 1] = null;
            }
        }else{ //same row, column higher
            for (; holeX < x; holeX++) { //Move the hole to the left
                piece = puzzleBoard[holeY][holeX + 1];
                piece.addAction(moveBy(-(piece.getWidth() + 2), 0, 0.5f, Interpolation.pow5Out));
                puzzleBoard[holeY][holeX] = piece;
                puzzleBoard[holeY][holeX + 1] = null;
            }
        }

        if(y < holeY) { //same column, row higher
            for(; holeY > y; holeY--) {
                piece = puzzleBoard[holeY - 1][holeX];
                piece.addAction(moveBy(0, -(piece.getHeight() + 2), .5f, Interpolation.pow5Out));
                puzzleBoard[holeY][holeX] = piece;
                puzzleBoard[holeY - 1][holeX] = null;
            }
        } else { //same column, row lower
            for(; holeY < y; holeY++) {
                piece = puzzleBoard[holeY + 1][holeX];
                piece.addAction(moveBy(0, piece.getHeight() + 2, .5f, Interpolation.pow5Out));
                puzzleBoard[holeY][holeX] = piece;
                puzzleBoard[holeY + 1][holeX] = null;
            }
        }
    }


    private boolean solutionFound(){
        int numCheck = 1;
        for(int i=0; i< boardSize; i++){
            for(int j=0; j<boardSize; j++){
                if(puzzleBoard[i][j] != null){
                    if(puzzleBoard[i][j].getNum() == numCheck++){
                        if(numCheck == boardSize*boardSize){
                            return true;
                        }
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }
        return false;
    }

}
