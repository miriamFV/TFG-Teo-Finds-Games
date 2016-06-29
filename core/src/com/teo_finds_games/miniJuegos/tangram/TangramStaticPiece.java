package com.teo_finds_games.miniJuegos.tangram;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TangramStaticPiece extends Image {

    private TextureRegion texture;
    private int numVertexes;
    private Vector2 size;
    private Vector2 position;

    public TangramStaticPiece(TextureRegion texture, int index, int numVertexes, Vector2 pos, float rotation){
        super(texture);

        this.texture = texture;
        this.numVertexes = numVertexes;
        size = new Vector2(texture.getRegionWidth()* TangramPlayScreen.SCALE, texture.getRegionHeight()*TangramPlayScreen.SCALE);
        this.position = new Vector2(TangramPlayScreen.benchmark.x+pos.x*TangramPlayScreen.SCALE, TangramPlayScreen.benchmark.y+pos.y*TangramPlayScreen.SCALE);

        setSize(size.x, size.y);
        setPosition(position);

        if(numVertexes == 3){
            setOrigin(size.x/4, size.y/4);
        }else{
            setOrigin(size.x/2, size.y/2);
        }

        setRotation(rotation);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, getRotation());
    }

    public void setPosition(Vector2 position){
        setPosition(position.x, position.y);
    }

    public Vector2 getPosition(){
        return new Vector2(getX(), getY());
    }
    
}
