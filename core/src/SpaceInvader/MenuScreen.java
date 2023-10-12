package SpaceInvader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.phoenix.MultipleScreen;

public class MenuScreen implements Screen
{
    MultipleScreen multi;

    private static SpriteBatch batch;
    private Stage stage;
    public static TextureAtlas textureAtlas;
    public static float[] backgroundOffsets = {0,0,0,0};
    public static TextureRegion[] backgrounds;
    public static float backgroundmaxSpeed;
    public static final int World_width = Gdx.graphics.getWidth();
    public static final int World_height = Gdx.graphics.getHeight();

    private Label labelStart, labelExit, labelNormal,labelHard, labelEasy, labelMulti;

    private Skin mySkin;
    private Music music;


    //For Modes
    public static int playerShieldAmount, enemySpeed, enemyShieldAmount, enemyLaserSpeed;
    public static float enemyTimeShot;
    private boolean modeChecked;


    //MultiPlayer
    public static boolean multiOrNot;

    public MenuScreen(MultipleScreen x)
    {
        multi = x;
    }

    @Override
    public void show()
    {

        modeChecked = false;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //Skin for buttons and labels
        mySkin = new Skin(Gdx.files.internal("Skin/glassyui/glassy-ui.json"));

        //Uploading the backgrounds
        textureAtlas = new TextureAtlas("Space Invader/Atlas/Images2.atlas");
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");
        backgroundmaxSpeed = (float)(World_height)/4;

        //creating Spritebatch
        batch = new SpriteBatch();

        //Background Music
        music = Gdx.audio.newMusic(Gdx.files.internal("Space Invader/Audio/MenuScreen.wav"));
        music.setVolume(1.0f);
        music.setLooping(true);
        music.play();


        //Creating the labels and adding actions for them
        labelStart = new Label("SinglePlayer",mySkin);
        labelStart.setSize(labelStart.getWidth()*3,labelStart.getHeight()*3);
        labelStart.setPosition((int)(Gdx.graphics.getWidth()/2) - labelStart.getWidth()/2, (int)(Gdx.graphics.getHeight()/2));
        labelStart.setFontScale(3,3);

        labelMulti = new Label("MultiPlayer",mySkin);
        labelMulti.setSize(labelMulti.getWidth()*3,labelMulti.getHeight()*3);
        labelMulti.setPosition(labelStart.getX() + (labelStart.getWidth() / 2) - (labelMulti.getWidth() / 2), labelStart.getY() - labelMulti.getHeight() - (Gdx.graphics.getHeight() * 0.02f));
        labelMulti.setFontScale(3,3);

        labelExit = new Label("Exit",mySkin);
        labelExit.setSize(labelExit.getWidth() * 3,labelExit.getHeight() * 3);
        labelExit.setPosition(labelMulti.getX() + (labelMulti.getWidth() / 3), labelMulti.getY() - labelExit.getHeight() - (Gdx.graphics.getHeight() * 0.02f));
        labelExit.setFontScale(3,3);


        labelEasy = new Label("Easy",mySkin);
        labelEasy.setSize(labelEasy.getWidth()*1.9f,labelEasy.getHeight()*1.9f);
        labelEasy.setPosition(0,(int)(Gdx.graphics.getHeight() / 2));
        labelEasy.setFontScale(2,2);


        labelNormal = new Label("Normal",mySkin);
        labelNormal.setSize(labelNormal.getWidth()*1.9f,labelNormal.getHeight()*1.9f);
        labelNormal.setPosition(labelEasy.getX(),labelEasy.getY() - labelNormal.getHeight() - (Gdx.graphics.getHeight()*0.02f));
        labelNormal.setFontScale(2,2);


        labelHard = new Label("Hard",mySkin);
        labelHard.setSize(labelHard.getWidth()*1.9f,labelHard.getHeight()*1.9f);
        labelHard.setPosition(labelNormal.getX(),labelNormal.getY() - labelHard.getHeight() - (Gdx.graphics.getHeight()*0.02f));
        labelHard.setFontScale(2,2);

        labelStart.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                multiOrNot = false;
                if(modeChecked) {
                    multi.changeScreen(new MainScreen(multi));
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                labelStart.setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                labelStart.setColor(Color.WHITE);
            }
        });


        labelMulti.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                multiOrNot = true;
                if(modeChecked) {
                    multi.changeScreen(new MainScreen(multi));
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                labelMulti.setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                labelMulti.setColor(Color.WHITE);
            }
        });

        labelExit.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                labelExit.setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                labelExit.setColor(Color.WHITE);
            }
        });


        labelEasy.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                labelEasy.setColor(Color.GREEN);
                labelEasy.setTouchable(Touchable.disabled);
                if(!labelNormal.isTouchable())
                {
                    labelNormal.setColor(Color.WHITE);
                    labelNormal.setTouchable(Touchable.enabled);
                }

                if(!labelHard.isTouchable())
                {
                    labelHard.setColor(Color.WHITE);
                    labelHard.setTouchable(Touchable.enabled);
                }

                modeChecked = true;

                //Initialize the variables
                playerShieldAmount = 7;

                enemySpeed = 140;
                enemyShieldAmount = 1;
                enemyLaserSpeed = 240;
                enemyTimeShot = 1.5f;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
            {
                labelEasy.setColor(Color.GREEN);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
            {
                if(labelEasy.isTouchable())
                {
                    labelEasy.setColor(Color.WHITE);
                }
            }
        });


        labelNormal.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                labelNormal.setColor(Color.GREEN);
                labelNormal.setTouchable(Touchable.disabled);
                if(!labelEasy.isTouchable())
                {
                    labelEasy.setColor(Color.WHITE);
                    labelEasy.setTouchable(Touchable.enabled);
                }

                if(!labelHard.isTouchable())
                {
                    labelHard.setColor(Color.WHITE);
                    labelHard.setTouchable(Touchable.enabled);
                }

                modeChecked = true;

                //Initialize the variables
                playerShieldAmount = 5;

                enemySpeed = 190;
                enemyShieldAmount = 2;
                enemyLaserSpeed = 300;
                enemyTimeShot = 0.8f;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
            {
                labelNormal.setColor(Color.GREEN);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
            {
                if(labelNormal.isTouchable())
                {
                    labelNormal.setColor(Color.WHITE);
                }
            }
        });


        labelHard.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                labelHard.setColor(Color.GREEN);
                labelHard.setTouchable(Touchable.disabled);
                if(!labelEasy.isTouchable())
                {
                    labelEasy.setColor(Color.WHITE);
                    labelEasy.setTouchable(Touchable.enabled);
                }

                if(!labelNormal.isTouchable())
                {
                    labelNormal.setColor(Color.WHITE);
                    labelNormal.setTouchable(Touchable.enabled);
                }

                modeChecked = true;

                //initialize the variables
                playerShieldAmount = 3;

                enemySpeed = 300;
                enemyShieldAmount = 3;
                enemyLaserSpeed = 450;
                enemyTimeShot = 0.4f;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
            {
                labelHard.setColor(Color.GREEN);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
            {
                if(labelHard.isTouchable())
                {
                    labelHard.setColor(Color.WHITE);
                }
            }
        });


        stage.addActor(labelStart);
        stage.addActor(labelExit);
        stage.addActor(labelNormal);
        stage.addActor(labelHard);
        stage.addActor(labelEasy);
        stage.addActor(labelMulti);
    }


    @Override
    public void render(float delta)
    {
        batch.begin();

        renderBackground(delta,batch);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    public static void renderBackground(float delta,SpriteBatch batch)
    {
        //the furthest background is slower
        backgroundOffsets[0] += delta * backgroundmaxSpeed / 8;
        //faster
        backgroundOffsets[1] += delta * backgroundmaxSpeed / 4;
        //faster
        backgroundOffsets[2] += delta * backgroundmaxSpeed / 2;
        //faster
        backgroundOffsets[3] += delta * backgroundmaxSpeed;

        for (int i = 0; i < backgroundOffsets.length; i++)
        {
            //resetting the offsets if it became bigger than the height of the screen
            if(backgroundOffsets[i] > World_height)
            {
                backgroundOffsets[i] = 0;
            }

            batch.draw(backgrounds[i],0,-backgroundOffsets[i],World_width,World_height);
            batch.draw(backgrounds[i],0,-backgroundOffsets[i]+World_height,World_width,World_height);
        }
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

    }

    @Override
    public void dispose()
    {
        batch.dispose();
        music.dispose();
        mySkin.dispose();
        stage.dispose();
        //textureAtlas.dispose();
    }
}
