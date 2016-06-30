package com.teo_finds_games.miniJuegos.unblockIt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.teo_finds_games.Application;


public class UnblockItPlayScreen implements Screen {

    private final Application app;
    private Stage stage;

    public static final int SCALE = 3;

    private TextureRegion boardTR, longBlockTR, shortBlockTR, redBlockTR;
    private UnblockItBlockActor longBlockActor1, longBlockActor2, longBlockActor3, longBlockActor4, shortBlockActor1, shortBlockActor2, shortBlockActor3, redBlockActor;

    public static int[][] matrix = new int[6][6]; //matrix of 0 y 1, 0 free position, 1 full position.
    public static Vector2 boardPosition;

    private TextButton backButton;

    private static boolean solutionFound;

    public UnblockItPlayScreen(final Application app){
        this.app = app;

    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(app.vpWidth, app.vpHeight, app.camera)){
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
        solutionFound = false;

        initMatrix();
        initTextures();
        boardPosition = new Vector2((app.vpWidth-boardTR.getRegionWidth()*SCALE)/2, (app.vpHeight-boardTR.getRegionHeight()*SCALE)/2);
        initActors();
        initButtons();
    }

    public void update(float delta){
        if(solutionFound){
            stage.addAction(Actions.sequence(Actions.delay(0.5f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    MoveToAction movement = new MoveToAction();
                    movement.setPosition(redBlockActor.getX() + redBlockActor.getWidth(), redBlockActor.getY());
                    movement.setDuration(1.25f);
                    redBlockActor.addAction(movement);
                }
            }), Actions.delay(1.5f),  Actions.run(new Runnable() {
                @Override
                public void run() {
                    app.unblockItCS.setNumCoins(6);
                    app.setScreen(app.unblockItCS);
                }
            })));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.4f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        app.batch.begin();
        app.batch.draw(boardTR, boardPosition.x, boardPosition.y, boardTR.getRegionWidth()*SCALE, boardTR.getRegionHeight()*SCALE);
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
        Gdx.input.setInputProcessor(null);
        stage.dispose();
        boardTR.getTexture().dispose();
        longBlockTR.getTexture().dispose();
        shortBlockTR.getTexture().dispose();
        redBlockTR.getTexture().dispose();
    }

    public void initMatrix(){
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix.length; j++){
                matrix[i][j] = 0;
            }
        }
    }

    public void initTextures(){
        /*
        boardTR = spriteSheet.findRegion("board");
        longBlockTR = spriteSheet.findRegion("longBlock");
        shortBlockTR = spriteSheet.findRegion("shortBlock");
        redBlockTR = spriteSheet.findRegion("redBlock");
        */
        boardTR = new TextureRegion(new Texture(Gdx.files.internal("images/unblockit/board.png")));
        longBlockTR = new TextureRegion(new Texture(Gdx.files.internal("images/unblockit/longBlock.png")));
        shortBlockTR = new TextureRegion(new Texture(Gdx.files.internal("images/unblockit/shortBlock.png")));
        redBlockTR = new TextureRegion(new Texture(Gdx.files.internal("images/unblockit/redBlock.png")));
    }

    public void initActors(){
        /*
        la matriz es:
        | (0,0) | (0,1) | (0,2) | (0,3) | (0,4) | (0,5) |
        ------------------------------------------------
        | (1,0) | (1,1) | (1,2) | (1,3) | (1,4) | (1,5) |
        ------------------------------------------------
        | (2,0) | (2,1) | (2,2) | (2,3) | (2,4) | (2,5) |
        ------------------------------------------------
        | (3,0) | (3,1) | (3,2) | (3,3) | (3,4) | (3,5) |
        ------------------------------------------------
        | (4,0) | (4,1) | (4,2) | (4,3) | (4,4) | (4,5) |
        ------------------------------------------------
        | (5,0) | (5,1) | (5,2) | (5,3) | (5,4) | (5,5) |
         */

        longBlockActor1 = new UnblockItBlockActor(longBlockTR, false, 3, 0, 3, 90);
        stage.addActor(longBlockActor1);
        longBlockActor2 = new UnblockItBlockActor(longBlockTR, false, 5, 2, 3, 0);
        stage.addActor(longBlockActor2);
        longBlockActor3 = new UnblockItBlockActor(longBlockTR, false, 3, 3, 3, 90);
        stage.addActor(longBlockActor3);
        longBlockActor4 = new UnblockItBlockActor(longBlockTR, false, 2, 5, 3, 90);
        stage.addActor(longBlockActor4);

        shortBlockActor1 = new UnblockItBlockActor(shortBlockTR, false, 0, 0, 2, 0);
        stage.addActor(shortBlockActor1);
        shortBlockActor2 = new UnblockItBlockActor(shortBlockTR, false, 5, 0, 2, 90);
        stage.addActor(shortBlockActor2);
        shortBlockActor3 = new UnblockItBlockActor(shortBlockTR, false, 4, 4, 2, 0);
        stage.addActor(shortBlockActor3);

        redBlockActor = new UnblockItBlockActor(redBlockTR, true, 2, 1, 2, 0);
        stage.addActor(redBlockActor);

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

    public static boolean getSolutionFound() {
        return solutionFound;
    }

    public static void setSolutionFound(boolean solutionFound) {
        UnblockItPlayScreen.solutionFound = solutionFound;
    }

}
