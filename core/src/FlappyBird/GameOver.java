package FlappyBird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.phoenix.Client;
import com.phoenix.MultipleScreen;

public class GameOver extends StartScreen
{
    private SpriteBatch batch;

    private  Label restartLabel;
    private MultipleScreen multi;
    public GameOver(MultipleScreen sc)
    {
        super(sc);
        multi = sc;
    }

    @Override
    public void show()
    {
        super.show();

        stage.clear();

        restartLabel = new Label("Restart",labelStyle);
        restartLabel.setSize(restartLabel.getWidth()*1.5f, restartLabel.getHeight()*1.25f);
        restartLabel.setFontScale(1.5f,1.5f);
        restartLabel.setPosition(Gdx.graphics.getWidth()/2f- restartLabel.getWidth()/2,Gdx.graphics.getHeight()/4.5f);

        restartLabel.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                sc.changeScreen( new GameScreen(sc));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                restartLabel.setColor(Color.BLUE);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                restartLabel.setColor(Color.WHITE);
            }

        });

        stage.addActor(restartLabel);
        stage.addActor(menuLabel);
        stage.addActor(exitLabel);
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);
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
    public void dispose()
    {
        stage.dispose();
    }

    @Override
    public void hide() {

    }
}
