package com.teo_finds_games.shortQuestions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

public class NueveLadrillosPlayScreen implements Screen {

    protected final Application app;
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Label questionLabel, answerLabel;
    private String questionText;
    private TextField answerField;
    private TextButton checkButton;

    public NueveLadrillosPlayScreen(Application app, String questionText){
        this.app = app;
        this.questionText = questionText;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera));
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        initTextures();
        setBackground();
        initLabels();
        initTextFields();
        initButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.7f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        batch.begin();
        batch.end();
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

    public void initTextures(){
        backgroundTexture = new Texture(Gdx.files.internal("images/nueveladrillos/background.png"));
    }

    public void setBackground(){
        Image imageBackground = new Image(backgroundTexture);
        imageBackground.setSize(app.vpWidth, app.vpHeight);
        stage.addActor(imageBackground);
    }

    public void initLabels(){
        questionLabel = new Label(questionText, app.skin);
        questionLabel.setWrap(true);
        questionLabel.setSize(app.vpWidth - 300, app.vpHeight/2 - 50);
        questionLabel.setFontScale(2);
        questionLabel.setColor(Color.BLACK);
        questionLabel.setPosition((app.vpWidth - questionLabel.getWidth())/2 - 50, app.vpHeight/2 + 50);
        stage.addActor(questionLabel);

        answerLabel = new Label("Solucion: ", app.skin);
        answerLabel.setSize(150,75);
        answerLabel.setFontScale(2);
        answerLabel.setColor(Color.BLACK);
        answerLabel.setPosition(questionLabel.getX(), questionLabel.getY() - answerLabel.getHeight());
        stage.addActor(answerLabel);
    }

    public void initTextFields(){
        answerField = new TextField("", app.skin);
        answerField.setSize(150,75);
        answerField.setPosition( answerLabel.getX() + answerLabel.getWidth()+10, questionLabel.getY()- answerField.getHeight());
        stage.addActor(answerField);
    }

    public void initButtons(){
        checkButton = new TextButton("COMPROBAR", app.skin);
        checkButton.setSize(150,75);
        checkButton.setPosition( answerField.getX() + answerField.getWidth() + (app.vpWidth-answerField.getWidth() - 150)/3, answerField.getY());
        checkButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(rightSolution()){
                    stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            app.nueveLadrillosCS.setNumCoins(2);
                            app.setScreen(app.nueveLadrillosCS);
                        }
                    })));
                }else{
                    stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            app.setScreen(app.nueveLadrillosSS);
                        }
                    })));
                }
            }
        });
        stage.addActor(checkButton);
    }

    public boolean rightSolution(){
        return ((answerField.getText().equals("4") || answerField.getText().equals("cuatro")));
    }
}