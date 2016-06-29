package com.teo_finds_games.miniJuegos.llave;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;


public class LlavePlayScreen implements Screen {

    private final Application app;
    private Stage stage;
    private TextButton backButton;
    public static final int SCALE = 4;
    public static final int NUMBER_OF_PIECES = 12;
    private static final Vector2 FRAME_SIZE = new Vector2(256,128);
    private Vector2 benchmark; //"benchmark" is the position of the frame where pieces are.

    //private TextureAtlas spriteSheet;
    private TextureRegion[] staticPiecesTextureRegionArray = new TextureRegion[NUMBER_OF_PIECES], dynamicPiecesTextureRegionArray = new TextureRegion[NUMBER_OF_PIECES];
    private static LlaveDynamicPiece[] dynamicPieceArray = new LlaveDynamicPiece[NUMBER_OF_PIECES];
    private static LlaveStaticPiece[] staticPieceArray = new LlaveStaticPiece[NUMBER_OF_PIECES];

    public LlavePlayScreen(Application app){
        this.app = app;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera));
        benchmark = new Vector2( (app.vpWidth-FRAME_SIZE.x*SCALE)/2, (app.vpHeight-FRAME_SIZE.y*SCALE)/2);
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        initButtons();
        initTextureRegions();
        initActors();
    }

    public void update(float delta){
        if(solutionFound()){
            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    app.setScreen(app.llaveCS);
                }
            })));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.7f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

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
        Gdx.input.setInputProcessor(null);
        stage.dispose();
        for(int num=0; num<NUMBER_OF_PIECES; num++){
            staticPiecesTextureRegionArray[num].getTexture().dispose();
            dynamicPiecesTextureRegionArray[num].getTexture().dispose();
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

    public void initTextureRegions(){
        //Textures of static pieces
        for(int num=0; num<NUMBER_OF_PIECES; num++){
            staticPiecesTextureRegionArray[num] = new TextureRegion(new Texture(Gdx.files.internal("images/llave/piezaGris"+(num+1)+".png")));
            dynamicPiecesTextureRegionArray[num] = new TextureRegion(new Texture(Gdx.files.internal("images/llave/pieza"+(num+1)+".png")));
        }

    }

    public void initActors(){
        //Static pieces
        staticPieceArray[0] = new LlaveStaticPiece(staticPiecesTextureRegionArray[0], 0, new Vector2(benchmark.x+0*SCALE, benchmark.y+44*SCALE), 0);
        stage.addActor(staticPieceArray[0]);
        staticPieceArray[1] = new LlaveStaticPiece(staticPiecesTextureRegionArray[1], 1, new Vector2(benchmark.x+30*SCALE, benchmark.y+54*SCALE), 0);
        stage.addActor(staticPieceArray[1]);
        staticPieceArray[2] = new LlaveStaticPiece(staticPiecesTextureRegionArray[2], 2, new Vector2(benchmark.x+60*SCALE, benchmark.y+54*SCALE), 0);
        stage.addActor(staticPieceArray[2]);
        staticPieceArray[3] = new LlaveStaticPiece(staticPiecesTextureRegionArray[3], 3, new Vector2(benchmark.x+60*SCALE, benchmark.y+24*SCALE), 0);
        stage.addActor(staticPieceArray[3]);
        staticPieceArray[4] = new LlaveStaticPiece(staticPiecesTextureRegionArray[4], 4, new Vector2(benchmark.x+100*SCALE, benchmark.y+64*SCALE), 0);
        stage.addActor(staticPieceArray[4]);
        staticPieceArray[5] = new LlaveStaticPiece(staticPiecesTextureRegionArray[5], 5, new Vector2(benchmark.x+90*SCALE, benchmark.y+24*SCALE), 0);
        stage.addActor(staticPieceArray[5]);
        staticPieceArray[6] = new LlaveStaticPiece(staticPiecesTextureRegionArray[6], 6, new Vector2(benchmark.x+110*SCALE, benchmark.y+44*SCALE), 0);
        stage.addActor(staticPieceArray[6]);
        staticPieceArray[7] = new LlaveStaticPiece(staticPiecesTextureRegionArray[7], 7, new Vector2(benchmark.x+140*SCALE, benchmark.y+54*SCALE), 0);
        stage.addActor(staticPieceArray[7]);
        staticPieceArray[8] = new LlaveStaticPiece(staticPiecesTextureRegionArray[8], 8, new Vector2(benchmark.x+160*SCALE, benchmark.y+94*SCALE), 0);
        stage.addActor(staticPieceArray[8]);
        staticPieceArray[9] = new LlaveStaticPiece(staticPiecesTextureRegionArray[9], 9, new Vector2(benchmark.x+160*SCALE, benchmark.y+4*SCALE), 0);
        stage.addActor(staticPieceArray[9]);
        staticPieceArray[10] = new LlaveStaticPiece(staticPiecesTextureRegionArray[10], 10, new Vector2(benchmark.x+200*SCALE, benchmark.y+44*SCALE), 0);
        stage.addActor(staticPieceArray[10]);
        staticPieceArray[11] = new LlaveStaticPiece(staticPiecesTextureRegionArray[11], 11, new Vector2(benchmark.x+200*SCALE, benchmark.y+4*SCALE), 0);
        stage.addActor(staticPieceArray[11]);


        //Dynamic pieces
        for(int index=0; index<NUMBER_OF_PIECES; index++){
            int posX = (int) (Math.random()*(app.vpWidth - 100*SCALE)+1);
            int posY = (int) (Math.random()*(app.vpHeight - 100*SCALE)+1);
            dynamicPieceArray[index] = new LlaveDynamicPiece(dynamicPiecesTextureRegionArray[index], index, new Vector2(posX,posY), 0);
            stage.addActor(dynamicPieceArray[index]);
        }




    }

    public static boolean areClose(int index){
        int allowedDistance = 50;
        return ((staticPieceArray[index].getRotation() == dynamicPieceArray[index].getRotation()) &&
                (staticPieceArray[index].getPosition().dst(dynamicPieceArray[index].getPosition()) < allowedDistance)) ;
    }

    public static void joinPieces(int index){
        dynamicPieceArray[index].setPosition(staticPieceArray[index].getPosition());
    }

    private boolean solutionFound(){
        boolean sol = true;
        for(int i=0; i<staticPieceArray.length; i++){
            if( (staticPieceArray[i].getX() != dynamicPieceArray[i].getX()) ||
                    (staticPieceArray[i].getY() != dynamicPieceArray[i].getY())){
                sol = false;
            }
        }
        return sol;
    }

}
