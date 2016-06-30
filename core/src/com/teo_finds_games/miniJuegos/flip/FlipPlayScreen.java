package com.teo_finds_games.miniJuegos.flip;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

public class FlipPlayScreen implements Screen {

    private final Application app;
    private Stage stage;
    private Sound sound;

    // Game Board
    private int numRows, numColumns;
    private int[][] startMatrix;
    private FlipClickButton[][] buttonBoard;

    private TextButton backButton;

    private int numberOfTouches;
    private Label ntLabel;

    public FlipPlayScreen(Application app){
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
        sound = Gdx.audio.newSound( Gdx.files.internal("sounds/flipClickSound.wav"));
        numberOfTouches = 0;
        numRows = FlipMenuScreen.numRows;
        numColumns = FlipMenuScreen.numColumns;

        startMatrix = new int[numRows][numColumns];
        initStartMatrix();

        initLabels();
        initButtons();
        initBoard();
    }

    public void update(float delta){
        stage.act(delta);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.7f, 0.1f, 0.9f, 1);
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
                app.setScreen(app.flipMenuScreen);
            }
        });
        stage.addActor(backButton);
    }


    public void initBoard(){
        int cbWidth = app.vpHeight/(numRows+2); //clickButton width
        int cbHeight = cbWidth; //clickButton height

        buttonBoard = new FlipClickButton[numRows][numColumns];
        for(int i=0; i<numRows; i++){
            for(int j=0; j<numColumns; j++){
                int id = j + 1 + (i*numColumns);
                int num = startMatrix[i][j];
                buttonBoard[i][j] = new FlipClickButton(num+"",num, app.skin, id);
                buttonBoard[i][j].setSize(cbWidth, cbHeight);
                buttonBoard[i][j].setPosition( ((app.vpWidth - (numColumns * (cbWidth + 1)))/2) + j*(cbWidth + 1) , ((app.vpHeight - (numRows * (cbHeight + 1)))/2) + i*(cbHeight + 1));
                buttonBoard[i][j].addAction(sequence(alpha(0), delay((j + 1 + (i * numColumns))/15f),
                        parallel(fadeIn(0.5f), moveBy(0, -10, 0.25f, Interpolation.pow5Out))));

                buttonBoard[i][j].addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        FlipClickButton clickedButton = (FlipClickButton) event.getListenerActor();

                        numberOfTouches++; //Increase touches' number
                        ntLabel.setText("Numero de pulsaciones = "+numberOfTouches);

                        sound.play(0.5f);
                        increaseSquare(clickedButton);
                        if(solutionFound()){
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    app.flipCS.setNumCoins((numRows-3)+(numColumns-3)+FlipMenuScreen.difficultyLevel);
                                    app.setScreen(app.flipCS);
                                }
                            })));;
                        }
                    }

                });

                stage.addActor(buttonBoard[i][j]);
            }
        }
    }


    public boolean isBorderSquare(int id){
        if (!isCornerSquare(id)){
            if( (id%numColumns == 0) || (id%numColumns == 1) || (id <= numColumns) || (id>(numRows-1)*numColumns) ){
                return true;
            }
        }
        return false;
    }


    public boolean isCornerSquare(int id){
        return (id == 1 || id == numColumns || id == 1 + (numColumns * (numRows-1)) || id == numRows * numColumns );
    }


    public void increaseSquare(FlipClickButton button) {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (button == buttonBoard[i][j]) {
                    if (isCornerSquare(button.getId())) { //When is in a corner

                        buttonBoard[i][j].setNum((buttonBoard[i][j].getNum() % 6) + 1);
                        if (buttonBoard[i][j].getId() == 1) { //lower left corner
                            buttonBoard[i + 1][j].setNum((buttonBoard[i + 1][j].getNum() % 6) + 1);
                            buttonBoard[i + 1][j + 1].setNum((buttonBoard[i + 1][j + 1].getNum() % 6) + 1);
                            buttonBoard[i][j + 1].setNum((buttonBoard[i][j + 1].getNum() % 6) + 1);

                        } else if (buttonBoard[i][j].getId() == numColumns) { //lower right corner
                            buttonBoard[i][j - 1].setNum((buttonBoard[i][j - 1].getNum() % 6) + 1);
                            buttonBoard[i + 1][j - 1].setNum((buttonBoard[i + 1][j - 1].getNum() % 6) + 1);
                            buttonBoard[i + 1][j].setNum((buttonBoard[i + 1][j].getNum() % 6) + 1);

                        } else if (buttonBoard[i][j].getId() == 1 + (numColumns * (numRows - 1))) { //left upper corner
                            buttonBoard[i - 1][j].setNum((buttonBoard[i - 1][j].getNum() % 6) + 1);
                            buttonBoard[i - 1][j + 1].setNum((buttonBoard[i - 1][j + 1].getNum() % 6) + 1);
                            buttonBoard[i][j + 1].setNum((buttonBoard[i][j + 1].getNum() % 6) + 1);

                        } else if (buttonBoard[i][j].getId() == numRows * numColumns) { //right upper corner
                            buttonBoard[i][j - 1].setNum((buttonBoard[i][j - 1].getNum() % 6) + 1);
                            buttonBoard[i - 1][j - 1].setNum((buttonBoard[i - 1][j - 1].getNum() % 6) + 1);
                            buttonBoard[i - 1][j].setNum((buttonBoard[i - 1][j].getNum() % 6) + 1);
                        }

                    }else if (isBorderSquare(button.getId())){ //when it is in an edge
                        buttonBoard[i][j].setNum( (buttonBoard[i][j].getNum() % 6) + 1);
                        if(button.getId()%numColumns==1){
                            buttonBoard[i+1][j].setNum( (buttonBoard[i+1][j].getNum() % 6) + 1);
                            buttonBoard[i][j+1].setNum( (buttonBoard[i][j+1].getNum() % 6) + 1);
                            buttonBoard[i-1][j].setNum( (buttonBoard[i-1][j].getNum() % 6) + 1);

                        }else if(button.getId()%numColumns==0){
                            buttonBoard[i+1][j].setNum( (buttonBoard[i+1][j].getNum() % 6) + 1);
                            buttonBoard[i][j-1].setNum( (buttonBoard[i][j-1].getNum() % 6) + 1);
                            buttonBoard[i-1][j].setNum( (buttonBoard[i-1][j].getNum() % 6) + 1);

                        }else if(button.getId()<numColumns){
                            buttonBoard[i][j-1].setNum( (buttonBoard[i][j-1].getNum() % 6) + 1);
                            buttonBoard[i+1][j].setNum( (buttonBoard[i+1][j].getNum() % 6) + 1);
                            buttonBoard[i][j+1].setNum( (buttonBoard[i][j+1].getNum() % 6) + 1);

                        }else{
                            buttonBoard[i][j-1].setNum( (buttonBoard[i][j-1].getNum() % 6) + 1);
                            buttonBoard[i-1][j].setNum( (buttonBoard[i-1][j].getNum() % 6) + 1);
                            buttonBoard[i][j+1].setNum( (buttonBoard[i][j+1].getNum() % 6) + 1);
                        }

                    } else { //When it is not in a corner or an edge
                        buttonBoard[i][j].setNum( (buttonBoard[i][j].getNum() % 6) + 1);
                        buttonBoard[i][j - 1].setNum( (buttonBoard[i][j - 1].getNum() % 6) + 1);
                        buttonBoard[i][j + 1].setNum( (buttonBoard[i][j + 1].getNum() % 6) + 1);
                        buttonBoard[i - 1][j].setNum( (buttonBoard[i - 1][j].getNum() % 6) + 1);
                        buttonBoard[i + 1][j].setNum( (buttonBoard[i + 1][j].getNum() % 6) + 1);
                    }
                }
            }
        }

    }





    public boolean solutionFound(){
        int sol = buttonBoard[0][0].getNum();
        for(int i=0; i<numRows; i++){
            for(int j=0; j<numColumns;j++){
                if (sol== buttonBoard[i][j].getNum()){
                    if (i==numRows-1 && j==numColumns-1){
                        return true;
                    }
                }else{
                    return false;
                }
            }
        }
        return false;
    }







    private void initStartMatrix(){
        int num = MathUtils.random(1,6);
        for(int row=0; row<numRows; row++){
            for(int column=0; column<numColumns; column++){
                startMatrix[row][column] = num;
            }
        }


        //At least a minimum of difficulty, it depends on number row and columns
        for(int i = 0; i<((numRows-3)+(numColumns-3)); i++){
            changeNumber();
        }
        for(int i=0; i<FlipMenuScreen.difficultyLevel; i++){
            changeNumber();
        }
    }

    private void changeNumber(){
        int row = MathUtils.random(0,2);
        int column = MathUtils.random(0,2);
        decreaseSquare(row, column);
    }

    private void decrease(int row, int column){
        if(startMatrix[row][column] == 1){
            startMatrix[row][column] = 6;
        }else{
           startMatrix[row][column]--;
        }
    }

    public void decreaseSquare(int row, int column) {
        int id = column + 1 + (row*numColumns);

        decrease(row, column); // The button clicked is always decreased
        if(isCornerSquare(id)){
            if(id==1){
                decrease((row+1),column);
                decrease((row+1),(column+1));
                decrease(row,(column+1));
            }else if(id == numColumns){
                decrease(row,(column-1));
                decrease((row+1),(column-1));
                decrease((row+1),column);
            }else if(id == 1 + (numColumns * (numRows - 1))){
                decrease((row-1),column);
                decrease((row-1),(column+1));
                decrease(row,(column+1));
            }else{
                decrease(row,(column-1));
                decrease((row-1),(column-1));
                decrease((row-1),column);
            }

        }else if (isBorderSquare(id)){
            int numCase = id%numColumns;
            if(numCase == 1) {
                decrease((row+1),column);
                decrease(row,(column+1));
                decrease((row-1),column);
            }else if(numCase == 0) {
                decrease((row+1),column);
                decrease(row,(column-1));
                decrease((row-1),column);
            }else if (numCase < numColumns) {
                decrease(row,(column-1));
                decrease((row+1),column);
                decrease(row,(column+1));
            }else {
                decrease(row,(column-1));
                decrease((row-1),column);
                decrease(row,(column+1));
            }
        }else{
            decrease(row,(column-1));
            decrease(row,(column+1));
            decrease((row-1),column);
            decrease((row+1),column);
        }

    }

}
