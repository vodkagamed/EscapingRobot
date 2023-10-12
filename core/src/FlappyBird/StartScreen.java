package FlappyBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.phoenix.Client;
import com.phoenix.MultipleScreen;

public class StartScreen implements Screen
{
    protected MultipleScreen sc;

    protected SpriteBatch batch;
    public static Label startLabel, menuLabel, exitLabel;
    protected  Stage stage;
    protected Label.LabelStyle labelStyle;
    protected GameFont govFont;
    public static Texture backgroundTexture;
    public static float BackgroundMove = 0;
    public static final float WorldWidth= Gdx.graphics.getWidth();
    public static final float WorldHeight=Gdx.graphics.getHeight();
    public StartScreen(MultipleScreen sc)
    {
        this.sc = sc;
    }

    @Override
    public void show()
    {
        batch=new SpriteBatch();
        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        backgroundTexture = new Texture("Robot/background2.jpg");

        govFont=new GameFont("Robot/joystix.monospace-regular.ttf",25,Color.WHITE,Color.BLACK,1);
        labelStyle=new Label.LabelStyle();
        labelStyle.font=govFont.getFont();


        startLabel = new Label("Start",labelStyle);
        startLabel.setSize(startLabel.getWidth()*1.5f, startLabel.getHeight()*1.25f);
        startLabel.setFontScale(1.5f,1.5f);
        startLabel.setPosition(Gdx.graphics.getWidth()/2f- startLabel.getWidth()/2,Gdx.graphics.getHeight()/4.5f);

        menuLabel=new Label("Menu",labelStyle);
        menuLabel.setSize(menuLabel.getWidth()*1.5f,menuLabel.getPrefHeight()*1.25f);
        menuLabel.setFontScale(1.5f,1.5f);
        menuLabel.setPosition(startLabel.getX() + (startLabel.getWidth() / 2) - (menuLabel.getWidth() / 2), startLabel.getY()-menuLabel.getHeight()-(Gdx.graphics.getHeight()*0.03f));

        exitLabel=new Label("Exit",labelStyle);
        exitLabel.setSize(exitLabel.getWidth()*1.5f,exitLabel.getPrefHeight()*1.25f);
        exitLabel.setFontScale(1.5f,1.5f);
        exitLabel.setPosition(menuLabel.getX() + (menuLabel.getWidth() / 2) - (exitLabel.getWidth() / 2),(menuLabel.getY())-exitLabel.getHeight()-(Gdx.graphics.getHeight()*0.03f));

        startLabel.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                sc.changeScreen( new GameScreen(sc));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                startLabel.setColor(Color.BLUE);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                startLabel.setColor(Color.WHITE);
            }

        });

        menuLabel.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                sc.changeScreen( new Client(sc));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                menuLabel.setColor(Color.BLUE);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                menuLabel.setColor(Color.WHITE);
            }

        }

        );

        exitLabel.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitLabel.setColor(Color.BLUE);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitLabel.setColor(Color.WHITE);
            }

        }

        );

        stage.addActor(startLabel);
        stage.addActor(exitLabel);
        stage.addActor(menuLabel);
    }


    public static void drawBackground(SpriteBatch batch)
    {
        batch.draw(backgroundTexture,-BackgroundMove,0,WorldWidth,WorldHeight);
        batch.draw(backgroundTexture,-BackgroundMove+WorldWidth,0,WorldWidth,WorldHeight);
    }

    @Override
    public void render(float delta)
    {
        BackgroundMove++;
        BackgroundMove=BackgroundMove%WorldWidth;

        batch.begin();

        drawBackground(batch);

        batch.end();

        stage.act();
        stage.draw();
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
        stage.dispose();
    }
}
