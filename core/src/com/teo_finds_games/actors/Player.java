package com.teo_finds_games.actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.teo_finds_games.Application;

public class Player extends Sprite {

    //Player animation
    private Animation playerStandAnimation, playerUpAnimation, playerDownAnimation, playerLeftAnimation, playerRightAnimation;
    private TextureRegion playerFrame;


    public Vector2 velocity = new Vector2();
    private float increment;
    private TiledMapTileLayer collisionLayer;
    private String blockedKey = "blocked", personKey = "person";


    public Player(Sprite sprite, TiledMapTileLayer collisionLayer){
        super(sprite);
        this.collisionLayer = collisionLayer;
        initAnimation();
    }


    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }


    public void update(float delta){
        // save old position
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false, collisionPerson = false;

        // move on x --------------------------------------------------------
        setX(getX() + velocity.x * delta);

        // calculate the increment for step in #collidesLeft() and #collidesRight()
        increment = collisionLayer.getTileWidth();
        increment = getWidth() < increment ? getWidth() / 2 : increment / 2;

        if(velocity.x < 0) { // going left
            collisionX = collidesLeft();
            collisionPerson = collidesPerson();
        }else if(velocity.x > 0) { // going right
            collisionX = collidesRight();
            collisionPerson = collidesPerson();
        }
        // react to x collision
        if(collisionX) {
            setX(oldX);
            velocity.x = 0;
            if(collisionPerson){
                //isTalking = true;
            }
        }

        // move on y --------------------------------------------
        setY(getY() + velocity.y * delta);

        // calculate the increment for step in #collidesBottom() and #collidesTop()
        increment = collisionLayer.getTileHeight();
        increment = getHeight() < increment ? getHeight() / 2 : increment / 2;

        if(velocity.y < 0){// going down
            collisionY = collidesBottom();
            collisionPerson = collidesPerson();
        }else if(velocity.y > 0) { // going up
            collisionY = collidesTop();
            collisionPerson = collidesPerson();
        }
        // react to y collision
        if(collisionY) {
            setY(oldY);
            velocity.y = 0;
            if(collisionPerson){
            }
        }
    }


    public boolean isCellBlocked(float x, float y){
        TiledMapTileLayer.Cell cell = collisionLayer.getCell( (int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
    }


    public boolean collidesRight() {
        for(float step = 0; step <= getHeight(); step += increment)
            if(isCellBlocked(getX() + getWidth(), getY() + step))
                return true;
        return false;
    }

    public boolean collidesLeft() {
        for(float step = 0; step <= getHeight(); step += increment)
            if(isCellBlocked(getX(), getY() + step))
                return true;
        return false;
    }

    public boolean collidesTop() {
        for(float step = 0; step <= getWidth(); step += increment)
            if(isCellBlocked(getX() + step, getY() + getHeight()))
                return true;
        return false;

    }

    public boolean collidesBottom() {
        for(float step = 0; step <= getWidth(); step += increment)
            if(isCellBlocked(getX() + step, getY()))
                return true;
        return false;
    }


    public boolean isCellPerson(float x, float y){
        TiledMapTileLayer.Cell cell = collisionLayer.getCell( (int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(personKey);
    }


    public boolean collidesPerson(){
        boolean sol = false;
        for(float step = 0; step <= getHeight(); step += increment){
            if(isCellPerson(getX() + getWidth(), getY() + step)){
                sol = true;
            } else if(isCellPerson(getX(), getY() + step)){
                sol = true;
            }
        }
        for(float step = 0; step <= getWidth(); step += increment){
            if(isCellPerson(getX() + step, getY() + getHeight())){
                sol = true;
            }else if(isCellPerson(getX() + step, getY())){
                sol = true;
            }
        }
        return sol;
    }


    public void initAnimation(){
        /*
        playerStandAnimation = new Animation(0.2f, Application.getSprites(Application.spriteSheet.findRegion("stand"), 4, 1));
        playerUpAnimation = new Animation(0.2f, Application.getSprites(Application.spriteSheet.findRegion("playerUp"), 4, 1));
        playerRightAnimation = new Animation(0.2f, Application.getSprites(Application.spriteSheet.findRegion("playerRight"), 4, 1));
        playerDownAnimation = new Animation(0.2f, Application.getSprites(Application.spriteSheet.findRegion("playerDown"), 4, 1));
        playerLeftAnimation = new Animation(0.2f, Application.getSprites(Application.spriteSheet.findRegion("playerLeft"), 4, 1));
        */
        playerStandAnimation = new Animation(0.2f, Application.getSprites("images/pantallaprincipal/stand.png", 4, 1));
        playerUpAnimation = new Animation(0.2f, Application.getSprites("images/pantallaprincipal/playerUp.png",4,1));
        playerRightAnimation = new Animation(0.2f, Application.getSprites("images/pantallaprincipal/playerRight.png",4,1));
        playerDownAnimation = new Animation(0.2f, Application.getSprites("images/pantallaprincipal/playerDown.png",4,1));
        playerLeftAnimation = new Animation(0.2f, Application.getSprites("images/pantallaprincipal/playerLeft.png",4,1));
    }


    //Getters animation
    public TextureRegion getPlayerFrame() {return playerFrame;}

    public void setPlayerFrame(TextureRegion playerFrame) {
        this.playerFrame = playerFrame;
    }

    public Animation getPlayerStandAnimation() {return playerStandAnimation;}

    public Animation getPlayerRightAnimation() {return playerRightAnimation;}

    public Animation getPlayerLeftAnimation() {return playerLeftAnimation;}

    public Animation getPlayerDownAnimation() {return playerDownAnimation;}

    public Animation getPlayerUpAnimation() {return playerUpAnimation;}


    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }


}
