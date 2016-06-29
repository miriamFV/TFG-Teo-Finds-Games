package com.teo_finds_games.miniJuegos.colCabraLobo;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ColCabraLoboAnimalActor extends Image {

    /*
    1 = wolf
    2 = goat
    3 = cabbage
    */
    private int type;
    private TextureRegion textureRegion;
    private ColCabraLoboAnimalData animalData;
    private int side; // left side = -1  and  right side = 1
    private Boolean inBoat;

    public ColCabraLoboAnimalActor(TextureRegion textureRegion, int type){
        super(textureRegion);
        this.textureRegion = textureRegion;
        this.type = type;
        side = -1;
        inBoat = false;
        animalData = new ColCabraLoboAnimalData(type);
        setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        setPosition(animalData.getStartPosition().x, animalData.getStartPosition().y);

        addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                moveActor();
                return false;
            }
        });
    }

    public void moveToTheOtherSide(){ //This method is called when you click Go button
        MoveByAction movement = new MoveByAction();
        movement.setDuration(1f);
        switch(side){
            case -1:
                movement.setAmountX(ColCabraLoboBoatActor.amount);
                break;
            case 1:
                movement.setAmountX(-ColCabraLoboBoatActor.amount);
                break;
        }
        addAction(movement);
        side = side * (-1);
        movement.restart();
    }

    public void moveActor(){
        if(!inBoat){
            if(!ColCabraLoboBoatActor.isFull && side == ColCabraLoboBoatActor.side){
                switch (side){
                    case -1: setPosition(ColCabraLoboBoatActor.leftPosition.x + (ColCabraLoboBoatActor.size.x-getWidth())/2, ColCabraLoboBoatActor.leftPosition.y);
                        break;
                    case 1: setPosition(ColCabraLoboBoatActor.rightPosition.x + (ColCabraLoboBoatActor.size.x-getWidth())/2, ColCabraLoboBoatActor.rightPosition.y);
                }
                ColCabraLoboBoatActor.isFull = true;
                inBoat = true;
            }else{
                //The boat is full or is not in the same side
            }
        }else{
            switch (ColCabraLoboBoatActor.side){
                case -1:
                    setPosition(animalData.getStartPosition().x, animalData.getStartPosition().y);
                    ColCabraLoboBoatActor.isFull = false;
                    break;
                case 1:
                    setPosition(animalData.getFinalPosition().x, animalData.getFinalPosition().y);
                    ColCabraLoboBoatActor.isFull = false;
                    break;
            }
            inBoat = false;
        }
    }

    public Boolean getInBoat() {
        return inBoat;

    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

}
