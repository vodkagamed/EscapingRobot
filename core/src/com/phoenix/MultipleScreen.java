package com.phoenix;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;


public class MultipleScreen extends Game
{

    @Override
    public void create()
    {
        changeScreen(new Client(this));
    }

    public void changeScreen(Screen newScreen){
        Screen oldScreen = getScreen();
        //Dispose the old screen to release resources
        if(oldScreen != null)
            oldScreen.dispose();

        setScreen(newScreen);
    }

    @Override
    public void render () {
        //Clear the Screen
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render the current Screen
        super.render();
    }

    @Override
    public void dispose () {
        //Dispose Assets which you used in the whole Game
    }

}
