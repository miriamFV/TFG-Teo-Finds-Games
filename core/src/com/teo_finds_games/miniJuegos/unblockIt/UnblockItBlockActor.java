package com.teo_finds_games.miniJuegos.unblockIt;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.teo_finds_games.Application;

public class UnblockItBlockActor extends Image {

    private TextureRegion textureRegion;
    private boolean pieceRed;
    private int row, column;
    private int length;
    private float rotation;
    private int space = 40 * UnblockItPlayScreen.SCALE;

    public UnblockItBlockActor(TextureRegion textureRegion, boolean pieceRed, int row, int column, int length, float rotation) {
        //position is the row and columns of our matrix
        super(textureRegion);
        this.pieceRed = pieceRed;
        this.row = row;
        this.column = column;
        this.length = length;
        this.rotation = rotation;

        setSize(textureRegion.getRegionWidth() * UnblockItPlayScreen.SCALE, textureRegion.getRegionHeight() * UnblockItPlayScreen.SCALE);
        setRotation(rotation);

        updateMatrix();
        updatePosition(row, column);

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                moveBlock(getDirection(Gdx.input.getX(), Application.vpHeight-Gdx.input.getY()));
                if(haveWon()){
                    UnblockItPlayScreen.setSolutionFound(true);
                }
                return false;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }


    public void updateMatrix(){
        if(getRotation()!=0){
            for(int n=0; n<length; n++){
                if((row-n>=0 && row-n<=UnblockItPlayScreen.matrix.length-1) && (column>=0 && column<=UnblockItPlayScreen.matrix.length-1)){
                    UnblockItPlayScreen.matrix[row-n][column] = 1;
                }
            }
        }else if(getRotation()==0){
            for(int n=0; n<length; n++){
                if((row>=0 && row<=UnblockItPlayScreen.matrix.length-1) && (column+n>=0 && column+n <= UnblockItPlayScreen.matrix.length-1)){
                    UnblockItPlayScreen.matrix[row][column+n] = 1;
                }
            }
        }
    }


    public void updatePosition(int row, int column){
        //Pieces are inside the board, so they depend on board's position
        if(rotation!=0){
            setPosition((UnblockItPlayScreen.boardPosition.x+6)+(column+1)*space, (UnblockItPlayScreen.boardPosition.y+6)+((UnblockItPlayScreen.matrix.length-1)-row)*space);
        }else if(rotation==0){
            setPosition((UnblockItPlayScreen.boardPosition.x+6)+column*space, (UnblockItPlayScreen.boardPosition.y+6)+((UnblockItPlayScreen.matrix.length-1)-row)*space);
        }
    }


    public int getDirection(float x, float y){
        //length*space = length of the piece in pixels
        int direction = 0;
        if(rotation!=0){
            if(y-getY() <= length*space/2){
                direction = 1;
            }else if((y-getY() > length*space/2)){
                direction = -1;
            }
        }else if(rotation == 0){
            if(x-getX() <= length*space/2){
                direction = -1;
            }else if(x-getX() > length*space/2){
                direction = 1;
            }
        }
        return direction;
    }


    public void moveBlock(int direction){
        //-1 movement to the left or above (depending on whether the piece is horizontal or vertical)
        // 1 move to the right or down (depending on whether the piece is horizontal or vertical)
        switch (direction){
            case 1:
                if(getRotation()!=0){
                    if((row+1<=(UnblockItPlayScreen.matrix.length-1))&&(UnblockItPlayScreen.matrix[row+1][column]==0)){
                        UnblockItPlayScreen.matrix[row-(length-1)][column] = 0;
                        row = row+1;
                        updatePosition(row, column);
                        UnblockItPlayScreen.matrix[row][column] = 1;
                    }
                }else if(getRotation()==0){
                    if((column+length<=UnblockItPlayScreen.matrix.length-1)&&(UnblockItPlayScreen.matrix[row][column+length]==0)){
                        UnblockItPlayScreen.matrix[row][column] = 0;
                        column = column+1;
                        updatePosition(row, column);
                        UnblockItPlayScreen.matrix[row][column+(length-1)] = 1;
                    }
                }
                break;
            case -1:
                if(getRotation()!=0){
                    if((row-length>=0)&&(UnblockItPlayScreen.matrix[row-length][column]==0)){
                        UnblockItPlayScreen.matrix[row][column] = 0;
                        row = row-1;
                        updatePosition(row, column);
                        UnblockItPlayScreen.matrix[row-(length-1)][column] = 1;
                    }
                }else if(getRotation()==0){
                    if((column-1>=0)&&(UnblockItPlayScreen.matrix[row][column-1]==0)){
                        UnblockItPlayScreen.matrix[row][column+(length-1)] = 0;
                        column = column-1;
                        updatePosition(row, column);
                        UnblockItPlayScreen.matrix[row][column] = 1;
                    }
                }
                break;
        }
    }

    private boolean haveWon(){
        return pieceRed && (row == 2 && column == 4);
    }


}
