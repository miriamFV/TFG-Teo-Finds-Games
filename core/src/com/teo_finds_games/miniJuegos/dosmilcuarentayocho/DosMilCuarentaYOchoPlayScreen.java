package com.teo_finds_games.miniJuegos.dosmilcuarentayocho;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public class DosMilCuarentaYOchoPlayScreen implements Screen {

    /*
    THE MATRIX IS
     |(0,0)|(0,1)|(0,2)|(0,3)|
     -----------------------
     |(1,0)|(1,1)|(1,2)|(1,3)|
     -----------------------
     |(2,0)|(2,1)|(2,2)|(2,3)|
     -----------------------
     |(3,0)|(3,1)|(3,2)|(3,3)|
     -----------------------
    */
    private final Application app;
    private Stage stage;
    public static final int NUM = 4; //The board is 4x4
    public Vector2 buttonSize;
    public Vector2 boardPosition;
    private TextButton backButton, upButton, downButton, rightButton, leftButton;
    private DosMilCuarentaYOchoButton[][] buttonBoard;

    private Vector2 lastTouch;
    private Vector2 difference;
    private int direction;


    public DosMilCuarentaYOchoPlayScreen(Application app) {
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
        buttonSize = new Vector2(app.vpHeight / (NUM + 2), app.vpHeight / (NUM + 2));
        boardPosition = new Vector2( (app.vpWidth - buttonSize.x*4 - 3)/2, (app.vpHeight - buttonSize.y*4 - 3)/2 ); //We have one pixel between each square as separation, we have 4 squares so 3 pixels
        initButtons();
        initDirectionButtons();
        initBoard();
        initBoardNumbers();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        app.batch.begin();

        app.batch.end();
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

    public int calculateDirection(Vector2 difference){
        if( Math.abs(difference.x)> Math.abs(difference.y)){ //If there has been more movement in the x axis
            if(difference.x>0){ return 2;
            }else{ return 4;}
        }else{
            if(difference.y>0) {
                return 3;
            }else{ return 1;}
        }
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

    public void initDirectionButtons(){
        upButton = new TextButton("UP", app.skin);
        upButton.setSize(60,60);
        upButton.setPosition(120,180);
        upButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                direction = 1;
                slide(direction);
                addNumber();
                if(haveWon()){
                    stage.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            app.dosMilCuarentaYOchoCS.setNumCoins(14);
                            app.setScreen(app.dosMilCuarentaYOchoCS);
                        }
                    })));
                }
            }
        });
        stage.addActor(upButton);

        downButton = new TextButton("DOWN", app.skin);
        downButton.setSize(60,60);
        downButton.setPosition(120,60);
        downButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                direction = 3;
                slide(direction);
                addNumber();
                if(haveWon()){
                    stage.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            app.setScreen(app.dosMilCuarentaYOchoCS);
                        }
                    })));
                }
            }
        });
        stage.addActor(downButton);

        rightButton = new TextButton("RIGHT", app.skin);
        rightButton.setSize(60,60);
        rightButton.setPosition(180,120);
        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                direction = 2;
                slide(direction);
                addNumber();
                if(haveWon()){
                    stage.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            app.setScreen(app.dosMilCuarentaYOchoCS);
                        }
                    })));
                }
            }
        });
        stage.addActor(rightButton);

        leftButton = new TextButton("LEFT", app.skin);
        leftButton.setSize(60,60);
        leftButton.setPosition(60,120);
        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                direction = 4;
                slide(direction);
                addNumber();
                if(haveWon()){
                    stage.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            app.setScreen(app.dosMilCuarentaYOchoCS);
                        }
                    })));
                }
            }
        });
        stage.addActor(leftButton);

    }

    public void initBoard(){
        buttonBoard = new DosMilCuarentaYOchoButton[NUM][NUM];
        for(int row = 0; row < NUM; row++){
            for(int column = 0; column < NUM; column++){
                buttonBoard[row][column] = new DosMilCuarentaYOchoButton(" ", 0, app.skin);
                buttonBoard[row][column].setSize(buttonSize.x, buttonSize.y);
                buttonBoard[row][column].setPosition(boardPosition.x + (column * (buttonSize.x + 1)), boardPosition.y + (NUM - (row + 1)) * (buttonSize.y + 1));
                buttonBoard[row][column].addAction(sequence(alpha(0), delay(column+1+(row*NUM)/15f),
                        parallel(fadeIn(0.5f), moveBy(0, -10, 0.25f, Interpolation.pow5Out))));
                stage.addActor(buttonBoard[row][column]);
            }
        }
    }

    public void initBoardNumbers() {
        int row1, column1, row2, column2;
        do {
            row1 = MathUtils.random(0, 3); //A random number between 0-3
            column1 = MathUtils.random(0, 3);//A random number between 0-3

            row2 = MathUtils.random(0, 3);
            column2 = MathUtils.random(0, 3);
        } while ((row1 == row2) && (column1 == column2));

        buttonBoard[row1][column1].setNum(2);
        buttonBoard[row2][column2].setNum(2);
    }

    public void addNumber() {
        //Adds a number to a "empty" board's square
        int row, column;
        do {
            row = MathUtils.random(0, 3);
            column = MathUtils.random(0, 3);
        } while (buttonBoard[row][column].getNum() != 0); //while this square is empty go on searching

        buttonBoard[row][column].setNum(2);
    }

    public void slide(int direction) {
        //direction 1 = up
        //direction 2 = right
        //direction 3 = down
        //direction 4 = left
        switch (direction) {
            case 1: moveUp();
                break;
            case 2: moveRight();
                break;
            case 3: moveDown();
                break;
            case 4: moveLeft();
                break;
            default:
                break;
        }
    }


    public void moveUp(){
        for(int row=0; row<NUM; row++){
            for(int column=0; column<NUM; column++){
                if(row>0 && buttonBoard[row][column].getNum()!=0){ //If we can move up (because we are not in row=0) and the box we are studying is not empty
                    int aux = 1;
                    while((row-aux>0)&&(buttonBoard[row-aux][column].getNum()==0)){//while we can move up and the box above is empty
                        aux++;
                    }
                    //Now [row-aux][column] is the upper empty box in that column inside the matrix or the next not empty box above
                    if(buttonBoard[row-aux][column].getNum() == 0){//If that box is empty we have to fill it with our number and empty the old box
                        buttonBoard[row-aux][column].setNum(buttonBoard[row][column].getNum());
                        buttonBoard[row][column].setNum(0);
                    }else{//If that box is not empty
                        if(buttonBoard[row-aux][column].getNum()==buttonBoard[row][column].getNum()){//If that box has the same number than our box, we add them in the new box
                            buttonBoard[row-aux][column].setNum(buttonBoard[row-aux][column].getNum()+buttonBoard[row][column].getNum());
                            buttonBoard[row][column].setNum(0);
                        }else{//If that box has a different number from our box, we have to move up our number until the last empty box above
                            aux--;
                            if(aux!=0){
                                buttonBoard[row-aux][column].setNum(buttonBoard[row][column].getNum());
                                buttonBoard[row][column].setNum(0);
                            }
                        }
                    }
                }
            }
        }
    }

    public void moveDown(){
        for(int row=NUM-1; row>=0; row--){
            for(int column=0; column<NUM; column++){
                if(row<NUM-1 && buttonBoard[row][column].getNum()!=0){
                    int aux = 1;
                    while((row+aux<NUM-1)&&(buttonBoard[row+aux][column].getNum()==0)){//while we can move up and the box above is empty
                        aux++;
                    }
                    if(buttonBoard[row+aux][column].getNum() == 0){
                        buttonBoard[row+aux][column].setNum(buttonBoard[row][column].getNum());
                        buttonBoard[row][column].setNum(0);
                    }else{
                        if(buttonBoard[row+aux][column].getNum()==buttonBoard[row][column].getNum()){
                            buttonBoard[row+aux][column].setNum(buttonBoard[row+aux][column].getNum()+buttonBoard[row][column].getNum());
                            buttonBoard[row][column].setNum(0);
                        }else{
                            aux--;
                            if(aux!=0){
                                buttonBoard[row+aux][column].setNum(buttonBoard[row][column].getNum());
                                buttonBoard[row][column].setNum(0);
                            }
                        }
                    }
                }
            }
        }
    }


    public void moveLeft(){
        for(int column=0; column<NUM; column++) {
            for (int row = 0; row < NUM; row++) {
                if(column>0 && buttonBoard[row][column].getNum()!=0){
                    int aux=1;
                    while((column-aux>0)&&(buttonBoard[row][column-aux].getNum()==0)){
                        aux++;
                    }
                    if(buttonBoard[row][column-aux].getNum() == 0){
                        buttonBoard[row][column-aux].setNum(buttonBoard[row][column].getNum());
                        buttonBoard[row][column].setNum(0);
                    }else{
                        if(buttonBoard[row][column-aux].getNum()==buttonBoard[row][column].getNum()){
                            buttonBoard[row][column-aux].setNum(buttonBoard[row][column-aux].getNum()+buttonBoard[row][column].getNum());
                            buttonBoard[row][column].setNum(0);
                        }else{
                            aux--;
                            if(aux!=0){
                                buttonBoard[row][column-aux].setNum(buttonBoard[row][column].getNum());
                                buttonBoard[row][column].setNum(0);
                            }
                        }
                    }
                }
            }
        }
    }

    public void moveRight(){
        for(int column=NUM-1; column>=0; column--){
            for (int row=0; row<NUM; row++){
                if(column<NUM-1 && buttonBoard[row][column].getNum()!=0){
                    int aux=1;
                    while((column+aux<NUM-1)&&(buttonBoard[row][column+aux].getNum()==0)){
                        aux++;
                    }
                    if(buttonBoard[row][column+aux].getNum() == 0){
                        buttonBoard[row][column+aux].setNum(buttonBoard[row][column].getNum());
                        buttonBoard[row][column].setNum(0);
                    }else{
                        if(buttonBoard[row][column+aux].getNum()==buttonBoard[row][column].getNum()){
                            buttonBoard[row][column+aux].setNum(buttonBoard[row][column+aux].getNum()+buttonBoard[row][column].getNum());
                            buttonBoard[row][column].setNum(0);
                        }else{
                            aux--;
                            if(aux!=0){
                                buttonBoard[row][column+aux].setNum(buttonBoard[row][column].getNum());
                                buttonBoard[row][column].setNum(0);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean haveWon(){
        boolean found2048 = false;
        for(int row=0; row<NUM; row++){
            for(int column = 0; column<NUM; column++){
                if(buttonBoard[row][column].getNum() == 2048){
                    found2048 = true;
                }
            }
        }
        return found2048;
    }

}
