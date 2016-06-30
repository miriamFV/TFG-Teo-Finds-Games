package com.teo_finds_games.miniJuegos.ochoNumeros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

import java.util.Arrays;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public class OchoNumerosPlayScreen implements Screen {

    private final Application app;
    private Stage stage;
    private Sound sound;

    // Game Board
    private int numRows = 4, numColumns = 3;
    private OchoNumerosClickButton[][] buttonBoard;

    private TextButton backButton;

    private int numberOfTouches = 0;
    private Label ntLabel;

    public OchoNumerosPlayScreen(Application app) {
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
        sound = Gdx.audio.newSound( Gdx.files.internal("sounds/ochonumeros/clickSound.wav"));

        initLabels();
        initButtons();
        initBoard();
    }

    public void update(float delta){
        stage.act(delta);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.8f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
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
        sound.dispose();
    }

    public void initLabels(){
        ntLabel = new Label("Numero de pulsaciones = 0", app.skin);
        ntLabel.setFontScale(2);
        ntLabel.setColor(Color.BLACK);
        ntLabel.setPosition( (app.vpWidth - 400)/2 , app.vpHeight-ntLabel.getHeight()*3);

        stage.addActor(ntLabel);
    }

    public void initButtons(){
        backButton = new TextButton("ATRAS", app.skin);
        backButton.setSize(150, 100);
        backButton.setPosition(50, app.vpHeight - (backButton.getHeight() + 50));
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });
        stage.addActor(backButton);
    }


    public void initBoard(){
        int cbWidth = app.vpHeight/(numRows+2); //clickButton width
        int cbHeight = cbWidth; //clickButton height

        buttonBoard = new OchoNumerosClickButton[numRows][numColumns];
        for(int i=0; i<numRows; i++){
            for(int j=0; j<numColumns; j++){

                if ( (i==0 && (j==0 || j== numColumns-1)) || (i==numRows-1 && (j==0 || j== numColumns-1)) ){
                    //The corners of the board can not be seen
                    int num = -1;
                    buttonBoard[i][j] = new OchoNumerosClickButton(num, app.skin, i, j);

                }else {
                    int num = 1;
                    buttonBoard[i][j] = new OchoNumerosClickButton(num, app.skin, i, j);
                    buttonBoard[i][j].setSize(cbWidth, cbHeight);
                    buttonBoard[i][j].setPosition( ((app.vpWidth - (numColumns * (cbWidth + 1)))/2) + j*(cbWidth + 1) , ((app.vpHeight - (numRows * (cbHeight + 1)))/2) + i*(cbHeight + 1));
                    buttonBoard[i][j].addAction(sequence(alpha(0), delay((j + 1 + (i * numColumns))/15f),
                            parallel(fadeIn(0.5f), moveBy(0, -10, 0.25f, Interpolation.pow5Out))));

                    buttonBoard[i][j].addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            OchoNumerosClickButton clickedButton = (OchoNumerosClickButton) event.getListenerActor();

                            numberOfTouches++; //Increase touches' number
                            ntLabel.setText("Numero de pulsaciones = "+numberOfTouches);

                            sound.play(0.5f);
                            increaseSquare(clickedButton);

                            if(solutionFound()){
                                stage.addAction(Actions.sequence(Actions.delay(3f), Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        app.ochoNumerosCS.setNumCoins(6);
                                        app.setScreen(app.ochoNumerosCS);
                                    }
                                })));
                            }

                        }

                    });

                    stage.addActor(buttonBoard[i][j]);
                }

            }
        }
    }

    public boolean isInMatrix(int num, int i, int j){
        for(int n=0; n<numRows; n++){
            for(int m=0; m<numColumns; m++){
                if((n!=i && m!=j) && buttonBoard[n][m].getNum() == num){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean allDifferents(){
        int[] array = new int[numRows*numColumns];
        int index=0;
        for(int i=0; i<numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                array[index] = buttonBoard[i][j].getNum();
                index++;
            }
        }
        Arrays.sort(array);
        int aux = array[4];
        for(int i=5; i<array.length; i++){
            if ( aux == array[i]){
                return false;
            }else{
                aux = array[i];
            }
        }
        return true;
    }


    public void increaseSquare(OchoNumerosClickButton button){
        button.setNum((button.getNum()%8)+1);
    }

    public boolean surroundedByConsecutive(int row, int column){
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                if ( (row+i>=0 && row+i<numRows) && (column+j>=0 && column+j<numColumns) ) {
                    //If this within the limits of the matrix
                    if(Math.abs(buttonBoard[row+i][column+j].getNum() - buttonBoard[row][column].getNum()) == 1){
                        //If the difference (with an adjoining number) is 1, it is surrounded by a row
                        return true;
                    }
                }else{
                    //Outside the bounds of the array
                }

            }
        }
        return false;
    }

    public boolean solutionFound(){

        if (allDifferents()){
            for(int i=0; i<numRows; i++) {
                for (int j = 0; j < numColumns; j++) {
                    if(surroundedByConsecutive(i, j)){
                        return false;
                    }
                }
            }
            return true;

        }else{
            return false;
        }

    }

}
