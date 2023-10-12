package SpaceInvader;

import FlappyBird.GameFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.phoenix.Client;
import com.phoenix.MultipleScreen;

public class EndScreen extends MainScreen implements Screen
{
    MultipleScreen multi;

    private SpriteBatch batch;
    private Preferences prefs = Gdx.app.getPreferences("Space Invader");
    private GameFont ScoreFont;
    private Stage stage;

    //private TextureAtlas textureAtlas;
    //private float[] backgroundOffsets = {0,0,0,0};
    private TextureRegion[] backgrounds;
    //private float backgroundmaxSpeed;
    private final int World_width = Gdx.graphics.getWidth();
    private final int World_height = Gdx.graphics.getHeight();

    private Label labelRestart;
    private Label labelExit;

    private Label labelMenu;

    private Skin mySkin;

    private Music music;

    public EndScreen(MultipleScreen x)
    {
        super(x);
        multi = x;
    }

    @Override
    public void show()
    {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        mySkin = new Skin(Gdx.files.internal("Skin/glassyui/glassy-ui.json"));

//        textureAtlas = new TextureAtlas("Space Invader/Atlas/Images2.atlas");
//        backgrounds = new TextureRegion[4];
//        backgrounds[0] = textureAtlas.findRegion("Starscape00");
//        backgrounds[1] = textureAtlas.findRegion("Starscape01");
//        backgrounds[2] = textureAtlas.findRegion("Starscape02");
//        backgrounds[3] = textureAtlas.findRegion("Starscape03");
//        backgroundmaxSpeed = (float)(World_height)/4;

        batch = new SpriteBatch();

        labelRestart = new Label("Restart",mySkin);
        labelRestart.setSize(labelRestart.getWidth()*3, labelRestart.getHeight()*3);
        labelRestart.setFontScale(3,3);
        labelRestart.setPosition((Gdx.graphics.getWidth()/2) - labelRestart.getWidth()/2, Gdx.graphics.getHeight()/2);

        labelMenu = new Label("Menu",mySkin);
        labelMenu.setSize(labelMenu.getWidth()*3,labelMenu.getHeight()*3);
        labelMenu.setFontScale(3,3);
        labelMenu.setPosition(labelRestart.getX() + (labelRestart.getWidth() * 0.1f), labelRestart.getY() - (labelRestart.getY() * 0.25f));

        labelExit = new Label("Exit",mySkin);
        labelExit.setSize(labelExit.getWidth() * 3,labelExit.getHeight() * 3);
        labelExit.setPosition(labelMenu.getX() + (labelMenu.getWidth() * 0.2f),labelMenu.getY() - (labelMenu.getY() * 0.35f));
        labelExit.setFontScale(3,3);

        labelRestart.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                multi.changeScreen( new MainScreen(multi));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                labelRestart.setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                labelRestart.setColor(Color.WHITE);
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

        labelMenu.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                multi.changeScreen(new Client(multi));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                labelMenu.setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                labelMenu.setColor(Color.WHITE);
            }
        });

        String fontPath = "Robot/joystix.monospace-regular.ttf";
        ScoreFont=new GameFont(fontPath,25, com.badlogic.gdx.graphics.Color.WHITE, Color.BLACK,1);

        stage.addActor(labelRestart);
        stage.addActor(labelExit);
        stage.addActor(labelMenu);

        music = Gdx.audio.newMusic(Gdx.files.internal("Space Invader/Audio/EndScreen.wav"));
        music.setVolume(1.0f);
        music.setLooping(true);
        music.play();
    }


    @Override
    public void render(float delta)
    {
        batch.begin();

        MenuScreen.renderBackground(delta,batch);

        if(MenuScreen.multiOrNot)
        {
            int higher = Math.max(prefs.getInteger("highscore"),prefs.getInteger("highscore2"));
            ScoreFont.draw(batch,"High Score: "+ higher,(World_width/2)-(ScoreFont.getTextwidth()/2),World_height-ScoreFont.getTextheight());

            if(prefs.getInteger("highscore") > prefs.getInteger("highscore2"))
            {
                ScoreFont.draw(batch,"Player one got higher/n nenene",(World_width/2)-(ScoreFont.getTextwidth()/2),World_height-ScoreFont.getTextheight() - (World_height * 0.05f));
            }
            else
            {
                ScoreFont.draw(batch,"Player two got higher",(World_width/2)-(ScoreFont.getTextwidth()/2),World_height-ScoreFont.getTextheight() - (World_height * 0.05f));
            }
        }
        else
        {
            ScoreFont.draw(batch, "High Score: " + prefs.getInteger("highscore"), (World_width / 2) - (ScoreFont.getTextwidth() / 2), World_height - ScoreFont.getTextheight());
        }

        batch.end();

        stage.act(delta);
        stage.draw();
    }

//    private void renderBackground(float delta)
//    {
//        //the furthest background is slower
//        backgroundOffsets[0] += delta * backgroundmaxSpeed / 8;
//        //faster
//        backgroundOffsets[1] += delta * backgroundmaxSpeed / 4;
//        //faster
//        backgroundOffsets[2] += delta * backgroundmaxSpeed / 2;
//        //faster
//        backgroundOffsets[3] += delta * backgroundmaxSpeed;
//
//        for (int i = 0; i < backgroundOffsets.length; i++)
//        {
//            //resetting the offsets if it became bigger than the height of the screen
//            if(backgroundOffsets[i] > World_height)
//            {
//                backgroundOffsets[i] = 0;
//            }
//
//            batch.draw(backgrounds[i],0,-backgroundOffsets[i],World_width,World_height);
//            batch.draw(backgrounds[i],0,-backgroundOffsets[i]+World_height,World_width,World_height);
//        }
//    }

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
        mySkin.dispose();
        stage.dispose();
        music.dispose();
        //MenuScreen.textureAtlas.dispose();
    }
}
