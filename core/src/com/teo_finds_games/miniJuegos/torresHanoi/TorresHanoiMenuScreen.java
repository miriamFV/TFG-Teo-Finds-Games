package com.teo_finds_games.miniJuegos.torresHanoi;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

public class TorresHanoiMenuScreen implements Screen {

    private final Application app;
    private Stage stage;

    private Texture menuTexture;
    private Image menuImage;

    private Label npLabel;
    private Slider numPiecesSlider;
    public static int numPieces;

    private TextButton playButton, exitButton;
    private Vector2 buttonSize;


    public TorresHanoiMenuScreen(Application app) {
        this.app = app;
    }

    @Override
    public void show() {
        this.stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera));
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        buttonSize = new Vector2(app.vpWidth/5, app.vpHeight/6);

        initMenuTitle();
        initSliders();
        initButtons();
    }

    public void update(float delta) {
        stage.act(delta);
        if (numPiecesSlider.isDragging()) {
            npLabel.setText("Numero de piezas [3,8] : " + (int)numPiecesSlider.getValue());
        }
        if (numPiecesSlider.isDragging()){
            npLabel.setText("Numero de piezas [3,8] : "+(int)numPiecesSlider.getValue());
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 0.7f, 0.8f, 1);
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
    }

    public void initMenuTitle(){
        menuTexture = new Texture(Gdx.files.internal("images/torreshanoi/menu.png"));

        menuImage = new Image(menuTexture);
        menuImage.setSize(app.vpWidth/2.5f, app.vpHeight/5);
        menuImage.setPosition((app.vpWidth-menuImage.getWidth())/2, 9*(app.vpHeight/12));
        stage.addActor(menuImage);
    }


    public void initSliders(){
        npLabel = new Label("Numero de piezas [3,8]:", app.skin);
        npLabel.setFontScale(2);
        npLabel.setColor(Color.BLACK);
        npLabel.setPosition((app.vpWidth - 600)/2, 7*(app.vpHeight/12));

        numPiecesSlider = new Slider(3, 8, 1, false, app.skin);
        numPiecesSlider.setWidth(600);
        numPiecesSlider.setPosition((app.vpWidth - 600) / 2, 3*(app.vpHeight/6));

        stage.addActor(npLabel);
        stage.addActor(numPiecesSlider);
    }

    public void initButtons(){

        //exitButton
        exitButton = new TextButton("SALIR", app.skin);
        exitButton.setSize(buttonSize.x, buttonSize.y);
        exitButton.setPosition(app.vpWidth/5, buttonSize.y);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });

        //playButton
        playButton = new TextButton("JUGAR", app.skin);
        playButton.setSize(buttonSize.x, buttonSize.y);
        playButton.setPosition(3*app.vpWidth/5, buttonSize.y);
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                numPieces = (int)numPiecesSlider.getValue();
                app.setScreen(app.torresHanoiPlayScreen);
            }
        });


        stage.addActor(exitButton);
        stage.addActor(playButton);
    }

}
