package com.teo_finds_games.miniJuegos.flip;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

public class FlipMenuScreen implements Screen {

    private final Application app;
    private Stage stage;
    private Label difficultyLabel, srLabel, scLabel;
    private Slider sliderDifficulty, sliderRows, sliderColumns;
    public static int difficultyLevel = 1, numRows = 0, numColumns = 0;
    private TextButton exitButton, playButton;

    public FlipMenuScreen(Application app) {
        this.app = app;
    }

    @Override
    public void show() {
        this.stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera));
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        difficultyLevel = 1;
        numRows = 0;
        numColumns = 0;

        initSliders();
        initButtons();
    }

    public void update(float delta){
        stage.act(delta);
        if(sliderDifficulty.isDragging()){
            difficultyLabel.setText("Nivel de dificultad [1,4]: "+(int)sliderDifficulty.getValue());
        }
        if (sliderRows.isDragging()){
            srLabel.setText("Numero de filas [3,6]: "+(int)sliderRows.getValue());
        }
        if (sliderColumns.isDragging()){
            scLabel.setText("Numero de columnas [3,6]: "+(int)sliderColumns.getValue());
        }
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

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void initSliders(){
        difficultyLabel = new Label("Nivel de dificultad [1,4]: 1", app.skin);
        difficultyLabel.setFontScale(2);
        difficultyLabel.setColor(Color.BLACK);
        difficultyLabel.setPosition((app.vpWidth - 600)/2, app.vpHeight - 100);

        sliderDifficulty = new Slider(1, 4, 1, false, app.skin);
        sliderDifficulty.setWidth(600);
        sliderDifficulty.setPosition((app.vpWidth - 600)/2, app.vpHeight - 150);

        srLabel = new Label("Numero de filas [3,6]: 3", app.skin);
        srLabel.setFontScale(2);
        srLabel.setColor(Color.BLACK);
        srLabel.setPosition((app.vpWidth - 600)/2, app.vpHeight - 250);

        sliderRows = new Slider(3, 6, 1, false, app.skin);
        sliderRows.setWidth(600);
        sliderRows.setPosition((app.vpWidth - 600)/2, app.vpHeight - 300);

        scLabel = new Label("Numero de columnas [3,6]: 3", app.skin);
        scLabel.setPosition((app.vpWidth - 600)/2, app.vpHeight - 400);
        scLabel.setFontScale(2);
        scLabel.setColor(Color.BLACK);

        sliderColumns = new Slider(3, 6, 1, false, app.skin);
        sliderColumns.setWidth(600);
        sliderColumns.setPosition((app.vpWidth - 600)/2, app.vpHeight - 450);

        stage.addActor(difficultyLabel);
        stage.addActor(sliderDifficulty);
        stage.addActor(srLabel);
        stage.addActor(sliderRows);
        stage.addActor(scLabel);
        stage.addActor(sliderColumns);
    }


    public void initButtons(){

        exitButton = new TextButton("SALIR", app.skin);
        exitButton.setSize(app.vpWidth/4 - 10, 150);
        //exitButton.setPosition(app.vpWidth/4 - 10, colorsButton.getY() - 200);
        exitButton.setPosition(app.vpWidth/4 - 10, sliderColumns.getY() - 250);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });

        playButton = new TextButton("JUGAR", app.skin);
        playButton.setSize(app.vpWidth/4 - 10, 150);
        playButton.setPosition(2*(app.vpWidth/4) + 10, exitButton.getY());
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                difficultyLevel = (int)sliderDifficulty.getValue();
                numRows =  (int)sliderRows.getValue();
                numColumns = (int)sliderColumns.getValue();
                app.setScreen(app.flipPlayScreen);
            }
        });
        stage.addActor(exitButton);
        stage.addActor(playButton);
    }

}
