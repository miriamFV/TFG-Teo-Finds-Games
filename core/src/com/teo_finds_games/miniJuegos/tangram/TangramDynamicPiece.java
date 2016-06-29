package com.teo_finds_games.miniJuegos.tangram;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.teo_finds_games.Application;

public class TangramDynamicPiece extends Image {

    private TextureRegion texture;
    private int index;
    private int numVertexes;
    private Vector2 size;
    private Vector2 position;
    private Boolean hasBeenDragged = false;

    public TangramDynamicPiece(TextureRegion texture, int index, int numVertexes, Vector2 pos, float rotation){
        super(texture);

        this.texture = texture;
        this.index = index;
        this.numVertexes = numVertexes;
        size = new Vector2(texture.getRegionWidth()* TangramPlayScreen.SCALE, texture.getRegionHeight()*TangramPlayScreen.SCALE);
        this.position = new Vector2(TangramPlayScreen.benchmark.x+pos.x*TangramPlayScreen.SCALE, TangramPlayScreen.benchmark.y+pos.y*TangramPlayScreen.SCALE);

        setSize(size.x, size.y);

        setPosition(position.x, position.y);

        if(numVertexes == 3){
            setOrigin(size.x/4, size.y/4);
        }else{
            setOrigin(size.x/2, size.y/2);
        }

        setRotation(rotation);


        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hasBeenDragged = false;
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                hasBeenDragged = true;
                setPosition(Gdx.input.getX(), Application.vpHeight-Gdx.input.getY());
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!hasBeenDragged) { //If the piece has just been pressed and not dragged
                    if (getRotation() + 45 >= 360) {
                        setRotation((getRotation() + 45) - 360);
                    } else {
                        setRotation(getRotation() + 45);
                    }
                }
                tryJoin();
            }
        });

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, getRotation());
    }

    private void tryJoin(){
        if( TangramPlayScreen.areClose(index) ){
            TangramPlayScreen.joinPieces(index);
        }
    }

    public void setPosition(Vector2 position){
        setPosition(position.x, position.y);
    }

    public Vector2 getPosition(){
        return new Vector2(getX(), getY());
    }

    public int getNumVertexes(){
        return numVertexes;
    }
    
}
