package com.teo_finds_games.miniJuegos.llave;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.teo_finds_games.Application;

public class LlaveDynamicPiece extends Image {

    TextureRegion textureRegion;
    private int index;
    private Vector2 position;
    private float rotation;

    public LlaveDynamicPiece(TextureRegion textureRegion, int index, Vector2 position, float rotation){
        super(textureRegion);
        this.textureRegion = textureRegion;
        this.index = index;
        this.position = position;
        this.rotation = rotation;

        setPosition(position.x, position.y);
        setSize(textureRegion.getRegionWidth()* LlavePlayScreen.SCALE, textureRegion.getRegionHeight()* LlavePlayScreen.SCALE);

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                setPosition(Gdx.input.getX() - getWidth() / 4, Application.vpHeight - (Gdx.input.getY() + getHeight() / 4));
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                updatePosition();
                tryJoin();
            }
        });
    }

    private void tryJoin(){
        if( LlavePlayScreen.areClose(index) ){
            LlavePlayScreen.joinPieces(index);

        }
    }

    public void updatePosition(){
        setPosition(new Vector2(getX(), getY()));
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        super.setPosition(position.x, position.y);
        this.position = position;
    }

}
