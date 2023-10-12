package FlappyBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import com.phoenix.MultipleScreen;
import java.util.Random;


public class GameScreen extends StartScreen implements Screen {
    MultipleScreen game;
    public enum State
    {
        //STOPPED
        PAUSE,
        //RESUME
        RUN,

    }

    private Preferences prefs = Gdx.app.getPreferences("game score");
    public static State state = State.RUN;

    //graphics
    private SpriteBatch batch;
    private Player player;
    private Texture playerRunTexture,enemyTexture;

    //World parameters
    public static final float WorldWidth= Gdx.graphics.getWidth();
    public static final float WorldHeight=Gdx.graphics.getHeight();

    //font&score
    private GameFont scoreFont;
    private int  currentScore=0;
    private int highScore=prefs.getInteger("highScore",0);

    private Viewport viewport;
    private Camera camera;

    //enemy attributes
    private final Array<Enemies> enemies = new Array<>();
    private final int distance=150;
    private boolean clicked=false;
    private Random rand;

    //sounds
    private Sound jumpSound;
    private Music backMusic;

    public GameScreen(MultipleScreen screen)
    {
        super(screen);
        this.game =screen;
    }

    @Override
    public void show()
    {
        //camera and rendering things:
        batch=new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WorldWidth,WorldHeight,camera);
        //textures and objects in the game:

        playerRunTexture =new Texture("Robot/Run.png");
        enemyTexture=new Texture("Robot/pxArt.png");
        player=new Player(playerRunTexture, playerRunTexture.getWidth()*0.95f, playerRunTexture.getHeight(),40,3);
        //Score font
        String fontPath = "Robot/joystix.monospace-regular.ttf";
        scoreFont=new GameFont(fontPath,25, Color.WHITE,Color.BLACK,1);
        rand=new Random();

        //nothing
        jumpSound= Gdx.audio.newSound(Gdx.files.internal("Robot/Jump.ogg"));
        backMusic=Gdx.audio.newMusic(Gdx.files.internal("Robot/Dexters Laboratory.mp3"));
        backMusic.setLooping(true);
        backMusic.setVolume(0.5f);

    }


    @Override
    public void render(float delta)
    {

        switch (state)
        {
            case RUN:
                BackgroundMove++;
                BackgroundMove=BackgroundMove%WorldWidth;

                backMusic.play();
                player.update(delta);
                if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                    pause();
                    backMusic.pause();
                    jumpSound.pause();
                }
                if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)&&player.getPosition().y<=5){
                    player.jump();
                    jumpSound.play();
                }

                if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)&&clicked==false){
                    prefs.putInteger("highScore", 0);
                    prefs.flush();
                    clicked=true;
                }


                //Enemies methods
                updateEnemies();
                spawnEnemies();
                EnemyDil();
                addScore();
                batch.begin();
                StartScreen.drawBackground(batch);
                player.draw(batch);
                drawEnemies();
                drawScore();
                batch.end();
                if(collision()) {
                    game.changeScreen(new GameOver(game));
                    highScore(currentScore);
                }
                break;

            case PAUSE:

                if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
                {
                   resume();
                }
                camera.update();
                batch.begin();
                StartScreen.drawBackground(batch);
                player.draw(batch);
                drawEnemies();
                drawScore();
                batch.end();
                break;
        }
    }


    @Override
    public void resize(int width, int height)
    {
        //viewport = new FillViewport(width,height);
        viewport.update(width,height);
    }

    @Override
    public void pause()
    {
        this.state = State.PAUSE;
    }

    @Override
    public void resume()
    {
        this.state = State.RUN;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        //backgroundTexture.dispose();
        enemyTexture.dispose();
        playerRunTexture.dispose();
        scoreFont.dispose();
        jumpSound.dispose();
        backMusic.dispose();
        batch.dispose();
    }

    public int highScore(int currentScore){
        if (currentScore > highScore) {
            prefs.putInteger("highScore", currentScore);
            prefs.flush();
        }
        return prefs.getInteger("highScore",0);
    }

    public void newEnemies(){
        Enemies enemy = new Enemies( WorldWidth +rand.nextInt(500));
        enemies.add(enemy);
    }

    public void spawnEnemies(){
        if (enemies.size==0)
            newEnemies();
        else{
            Enemies lastEnemy= enemies.peek();
            if(lastEnemy.getPosition().x<WorldWidth-distance)
                newEnemies();
        }
    }

    public boolean collision(){
        for (int i = 0; i < enemies.size; i++) {
            if (player.intersects(enemies.get(i).getCoordinates()))
                return true;
        }
        return false;
    }
    public void EnemyDil(){
        Enemies firstEnemy= enemies.first();
        if (firstEnemy.getPosition().x<-WorldWidth)
            enemies.removeValue(firstEnemy,true);

    }

    public void drawScore(){
        scoreFont.draw(batch,"Score: "+currentScore,5,WorldHeight-scoreFont.getTextheight());
        scoreFont.draw(batch,"High Score: "+prefs.getInteger("highScore"),WorldWidth-scoreFont.getTextwidth()*2,WorldHeight-scoreFont.getTextheight());
    }
    public void addScore(){
        for (int i = 0; i < enemies.size; i++) {
            int temp =(int)enemies.get(i).getPosition().x;
            while (temp%4!=0)
                    temp++;

            if (40==temp)
                currentScore++;
        }
    }

    public void drawEnemies(){
        for (int i = 0; i < enemies.size; i++) {
            enemies.get(i).draw(batch);
        }
    }

    public void updateEnemies(){
        for (int i = 0; i < enemies.size; i++) {
            enemies.get(i).enemyUpdate(4);
        }
    }
}
