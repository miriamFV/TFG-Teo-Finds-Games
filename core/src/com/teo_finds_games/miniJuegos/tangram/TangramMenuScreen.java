package com.teo_finds_games.miniJuegos.tangram;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

public class TangramMenuScreen implements Screen {

    private final Application app;
    private Stage stage;
    private Label titleLabel;
    private Texture housePuzzle, shoePuzzle;
    private Image housePuzzleImage, shoePuzzleImage;
    private TextButton exitButton;

    public TangramMenuScreen(Application app){
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
        initLabels();
        initTextures();
        initImages();
        initButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.8f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        app.batch.begin();

        app.batch.end();

        stage.act();
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
        Gdx.input.setInputProcessor(stage);
        stage.dispose();
        housePuzzle.dispose();
        shoePuzzle.dispose();
    }

    public void initLabels(){
        titleLabel = new Label("Selecciona la figura que desees:", app.skin);
        titleLabel.setFontScale(2);
        titleLabel.setColor(Color.BLACK);
        titleLabel.setSize(app.vpWidth/3, 25);
        titleLabel.setPosition( app.vpWidth/3, 3*app.vpHeight/4);
        stage.addActor(titleLabel);
    }

    public void initTextures(){
        housePuzzle = new Texture(Gdx.files.internal("images/tangram/casa.png"));
        shoePuzzle = new Texture(Gdx.files.internal("images/tangram/zapato.png"));
    }

    public void initImages(){
        housePuzzleImage = new Image(housePuzzle);
        housePuzzleImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                app.tangramPlayScreen.setNumberOfPuzzle(1);
                app.setScreen(app.tangramPlayScreen);
                return false;
            }
        });
        housePuzzleImage.setSize(app.vpWidth / 5, app.vpHeight / 4);
        housePuzzleImage.setPosition(app.vpWidth / 5, app.vpHeight / 4);
        stage.addActor(housePuzzleImage);

        shoePuzzleImage = new Image(shoePuzzle);
        shoePuzzleImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                app.tangramPlayScreen.setNumberOfPuzzle(2);
                app.setScreen(app.tangramPlayScreen);
                return false;
            }
        });
        shoePuzzleImage.setSize(app.vpWidth / 5, app.vpHeight / 4);
        shoePuzzleImage.setPosition(3 * app.vpWidth / 5, app.vpHeight / 4);
        stage.addActor(shoePuzzleImage);
    }

    public void initButtons(){
        exitButton = new TextButton("SALIR", app.skin);
        exitButton.setSize(130, 70);
        exitButton.setPosition(20, app.vpHeight - (exitButton.getHeight() + 20));
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });
        stage.addActor(exitButton);
    }

}
