package com.teo_finds_games.miniJuegos.colCabraLobo;


import com.badlogic.gdx.math.Vector2;
import com.teo_finds_games.Application;

public class ColCabraLoboAnimalData {

    private Vector2 startPosition, finalPosition;
    private final int MARGIN = 40;

    public ColCabraLoboAnimalData(int type){
        switch(type){
            case 1:
                startPosition = new Vector2(0 + MARGIN, (Application.vpHeight/3)*2);
                finalPosition = new Vector2((Application.vpWidth/6)*5 + MARGIN,(Application.vpHeight/3)*2);
                break;
            case 2:
                startPosition = new Vector2(0 + MARGIN, (Application.vpHeight/3)*1);
                finalPosition = new Vector2((Application.vpWidth/6)*5 + MARGIN,(Application.vpHeight/3)*1);
                break;
            case 3:
                startPosition = new Vector2(0 + MARGIN, (Application.vpHeight/3)*0 + MARGIN);
                finalPosition = new Vector2((Application.vpWidth/6)*5 + MARGIN,(Application.vpHeight/3)*0 + MARGIN);
                break;
        }
    }

    public Vector2 getStartPosition() {
        return startPosition;
    }

    public Vector2 getFinalPosition() {
        return finalPosition;
    }

}
