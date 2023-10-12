package SpaceInvader;

import FlappyBird.GameFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.MultipleScreen;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;


public class MainScreen extends MenuScreen implements Screen
{

    public enum State
    {
        PAUSE,
        RUN,
    }

    int temp; //used to store backgrounds index

    private State state = State.RUN;
    MultipleScreen multi;

    boolean once = false;
    //Score
    GameFont ScoreFont;
    private Preferences prefs = Gdx.app.getPreferences("Space Invader");
    int scorePlayerOne = 0, scorePlayerTwo = 0;

    //Controlling the num of enemies which appear in the screen
    int MaxEnemyNum = 3, EnemyCounter = 1;

    //used in random positioning the enemy
    Random random = new Random();


    //Screen
    private Camera camera;
    private Viewport viewport;


    //pause menu
    Label labelStart,labelExit;
    Skin mySkin;
    Stage stage;
    boolean pauseOnce = false;

    //Graphics
    private SpriteBatch batch;
    private Texture explosion;
    private TextureAtlas textureAtlas;
    //private TextureRegion[] backgrounds;
    private  TextureRegion playerShipTexture, playerShield, enemyShipTexture, enemyShield, playerLaser, enemyLaser;

    //Timing
    //private float[] backgroundOffsets = {0,0,0,0};
    //private float backgroundmaxSpeed;
    private float timeBetweenEnemySpawn = 3f, enemySpawnTimer = 0;

    //world parameters
    private final int World_width = Gdx.graphics.getWidth();
    private final int World_height = Gdx.graphics.getHeight();


    //Game Objects
    private PlayerShip playership,playership2;
    private LinkedList<EnemyShip> enemyshipsList;

    private LinkedList<Lasers> playerLaserList,playerLaserList2, enemyLaserList;
    private LinkedList<Explosion> explosionList;


    //Sounds
    private Sound laserSound,ShieldDownSound,ShieldUpSound;


    //For modes (easy,norma,hard)
    private int playerShieldAmount, enemySpeed, enemyShieldAmount, enemyLaserSpeed;
    private float enemyTimeShot;

    public MainScreen(MultipleScreen multi)
    {
        super(multi);
        this.multi = multi;
    }

    @Override
    public void show()
    {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        mySkin = new Skin(Gdx.files.internal("Skin/glassyui/glassy-ui.json"));

        //To initialize the modes variables
        Modes();

        //Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        //a camera for 2d perspective
        camera = new OrthographicCamera();
        viewport = new StretchViewport(World_width,World_height,camera);

        //Explosion texture
        explosion = new Texture("Space Invader/Explosion.png");

        //set up the texture atlas
        textureAtlas = new TextureAtlas("Space Invader/Atlas/Images2.atlas");

        //set up the BackGrounds
//        backgrounds = new TextureRegion[4];
//        backgrounds[0] = textureAtlas.findRegion("Starscape00");
//        backgrounds[1] = textureAtlas.findRegion("Starscape01");
//        backgrounds[2] = textureAtlas.findRegion("Starscape02");
//        backgrounds[3] = textureAtlas.findRegion("Starscape03");
//
//        backgroundmaxSpeed = (float)(World_height)/4;


        //Initialize texture regions
        enemyShipTexture = textureAtlas.findRegion("Enemy");
        enemyShield = textureAtlas.findRegion("shield1");
        enemyLaser = textureAtlas.findRegion("shoot 1-01");
        enemyShield.flip(false,true); //flipping the shield upside down

        playerShipTexture = textureAtlas.findRegion("Player");
        playerShield = textureAtlas.findRegion("shield2");
        playerLaser = textureAtlas.findRegion("laserBlue05");

        //set up game objects
        if(MenuScreen.multiOrNot)
        {
            playership = new PlayerShip(400,playerShieldAmount,90,90,(int)(World_width - (World_width * 0.6667f)),World_height/4,
                    4,23,450,0.6f,playerShipTexture,playerShield,playerLaser);

            playership2 = new PlayerShip(400,playerShieldAmount,90,90,(int)(World_width - (World_width * 0.3333f)),World_height/4,
                    4,23,450,0.6f,playerShipTexture,playerShield,playerLaser);
            playerLaserList2 = new LinkedList<>();
        }
        else
        {
            playership = new PlayerShip(400,playerShieldAmount,90,90,World_width/2,World_height/4,
                    4,23,450,0.6f,playerShipTexture,playerShield,playerLaser);
        }

        enemyshipsList = new LinkedList<>();

        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();
        explosionList = new LinkedList<>();

        batch = new SpriteBatch();

        //Font
        String fontPath = "Robot/joystix.monospace-regular.ttf";
        if(MenuScreen.multiOrNot)
        {
            ScoreFont = new GameFont(fontPath, 15, com.badlogic.gdx.graphics.Color.WHITE, Color.BLACK, 1);
        }
        else {
            ScoreFont = new GameFont(fontPath, 25, com.badlogic.gdx.graphics.Color.WHITE, Color.BLACK, 1);
        }


        //Sounds
        ShieldUpSound = Gdx.audio.newSound(Gdx.files.internal("Space Invader/Audio/sfx_shieldUp.ogg"));
        ShieldDownSound = Gdx.audio.newSound(Gdx.files.internal("Space Invader/Audio/sfx_shieldDown.ogg"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("Space Invader/Audio/sfx_laser1.ogg"));
    }

    @Override
    public void render(float delta)
    {
        switch (state)
        {
            case RUN:

                if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                {
                    pauseOnce = true;
                    stage.clear();
                    state = State.PAUSE;
                }

                if (playership.lives > 0 || (MenuScreen.multiOrNot && playership2.lives > 0))
                {
                    batch.begin();

                    MenuScreen.renderBackground(delta,batch);

                    inputs(delta);
                    playership.update(delta);

                    if(MenuScreen.multiOrNot)
                    {
                        playership2.update(delta);
                    }

                    spawnEnemy(delta);

                    ListIterator<EnemyShip> enemyiterator = enemyshipsList.listIterator();
                    while (enemyiterator.hasNext())
                    {
                        EnemyShip enemyship = enemyiterator.next();
                        moveEnemies(enemyship, delta);
                        enemyship.update(delta);

                        //Enemy ships
                        enemyship.draw(batch);
                    }


                    //lasers
                    renderLasers(delta);

                    //player ships
                    if(playership.lives > 0)
                    {
                        playership.draw(batch);
                    }


                    if(MenuScreen.multiOrNot && playership2.lives > 0)
                    {
                        playership2.draw(batch);
                    }


                    //Collision
                    Collisions();

                    if(MenuScreen.multiOrNot)
                    {
                        Collisions2();
                    }

                    //explosions
                    renderExplosions(delta);

                    //draw Score
                    drawScore();

                    batch.end();
                }
                else
                {
                    multi.changeScreen(new EndScreen(multi));
                }
                break;


            case PAUSE:
                pause();
                if(pauseOnce)
                {
                    pauseMenu();
                }
                stage.act(delta);
                stage.draw();;
                break;
        }
    }

    public void drawScore()
    {
        //updating highScore
        if (scorePlayerOne > prefs.getInteger("highscore")) {
            prefs.putInteger("highscore", scorePlayerOne);
            prefs.flush();
        }

        ScoreFont.draw(batch,"Score: "+ scorePlayerOne,5,World_height-ScoreFont.getTextheight());
        ScoreFont.draw(batch,"Lives: "+playership.lives,(World_width/2) - (World_width * 0.15f),World_height-ScoreFont.getTextheight());
        ScoreFont.draw(batch,"High Score: "+prefs.getInteger("highscore"),World_width-ScoreFont.getTextwidth()*2,World_height-ScoreFont.getTextheight());

        if(MenuScreen.multiOrNot)
        {

            if (scorePlayerTwo > prefs.getInteger("highscore2"))
            {
                prefs.putInteger("highscore2", scorePlayerTwo);
                prefs.flush();
            }

            ScoreFont.draw(batch,"Score: "+ scorePlayerTwo,5,World_height-ScoreFont.getTextheight() - (World_height * 0.05f));
            ScoreFont.draw(batch,"Lives: "+playership2.lives,(World_width/2) - (World_width * 0.15f),World_height-ScoreFont.getTextheight() - (World_height * 0.05f));
            ScoreFont.draw(batch,"High Score: "+prefs.getInteger("highscore2"),World_width-ScoreFont.getTextwidth()*2,World_height-ScoreFont.getTextheight() - (World_height * 0.05f));
        }
    }

    private void spawnEnemy(float delta)
    {
        enemySpawnTimer+=delta;
        if(enemySpawnTimer > timeBetweenEnemySpawn && MaxEnemyNum >= EnemyCounter )
        {
            enemyshipsList.add(new EnemyShip(enemySpeed, enemyShieldAmount, 60, 60, random.nextFloat() *
                    (World_width - 10), random.nextFloat() * (World_height - 5), 6, 25,
                    enemyLaserSpeed, enemyTimeShot, enemyShipTexture, enemyShield, enemyLaser));


            enemySpawnTimer -= timeBetweenEnemySpawn;

            //spawning 3 enemy ships at max --> when ship dies the counter must be decremented by 1
            EnemyCounter++;
        }
    }


    private void renderExplosions(float delta)
    {
        ListIterator<Explosion> exploisionIter = explosionList.listIterator();
        while(exploisionIter.hasNext())
        {
            Explosion explosion1 = exploisionIter.next();
            explosion1.update(delta);
            if(explosion1.isFinished())
            {
                exploisionIter.remove();
            }
            else
            {
                explosion1.draw(batch);
            }
        }
    }



    //Scrolling background
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


    private void renderLasers(float delta)
    {
        ListIterator<EnemyShip> enemyiterator = enemyshipsList.listIterator();
        while(enemyiterator.hasNext())
        {
            EnemyShip enemyship = enemyiterator.next();
            if(enemyship.canFireLaser())
            {
                Lasers[] lasers = enemyship.fireLasers();
                for(Lasers laser : lasers)
                {
                    enemyLaserList.add(laser);
                }
            }
        }

        //draw lasers
        //remove old lasers
        ListIterator<Lasers> iterator = playerLaserList.listIterator(); //going through the list of playerLaser to add or remove ... etc
        while(iterator.hasNext())
        {
            Lasers laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y += laser.m_speed*delta; //Player Laser speed
            if(laser.boundingBox.y >World_height)
            {
                iterator.remove();
            }
        }


        //Draw Lasers for player 2
        if(MenuScreen.multiOrNot)
        {
            ListIterator<Lasers> iterator2 = playerLaserList2.listIterator(); //going through the list of playerLaser to add or remove ... etc
            while (iterator2.hasNext()) {
                Lasers laser = iterator2.next();
                laser.draw(batch);
                laser.boundingBox.y += laser.m_speed * delta; //Player Laser speed
                if (laser.boundingBox.y > World_height) {
                    iterator2.remove();
                }
            }
        }

        //Draw Enemy Laser
        iterator = enemyLaserList.listIterator(); //going through the list of enemylaser to add or remove ... etc
        while(iterator.hasNext())
        {
            Lasers laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y -= laser.m_speed*delta; //enemy Laser speed
            if(laser.boundingBox.y + laser.boundingBox.height < 0)
            {
                iterator.remove();
            }
        }

    }


    private void Collisions()
    {
        //for each player laser, check whether it intersects an enemy ship
        ListIterator<Lasers> iterator = playerLaserList.listIterator(); //going through the list of playerLaser to add or remove ... etc
        while(iterator.hasNext())
        {
            Lasers laser = iterator.next();
            ListIterator<EnemyShip> temp = enemyshipsList.listIterator();

            while(temp.hasNext())
            {
                EnemyShip enemyship = temp.next();
                if(enemyship.intersects(laser.boundingBox))
                {
                    //removing when hitting the enemy
                    if(enemyship.hit(laser))
                    {
                        temp.remove();
                        explosionList.add(new Explosion(explosion,new Rectangle(enemyship.boundingBox),0.7f));
                        EnemyCounter--;
                        scorePlayerOne++;
                    }
                    iterator.remove();
                    break;
                }
            }
        }


        //for each enemy laser, check whether it intersects the player ship
        iterator = enemyLaserList.listIterator(); //going through the list of enemyLaser to add or remove ... etc
        while(iterator.hasNext())
        {
            Lasers laser = iterator.next();
            if(playership.intersects(laser.boundingBox) && playership.lives > 0)
            {
                if(playership.Shield == 1)
                {
                    long id = ShieldDownSound.play(1.0f);
                    ShieldDownSound.setLooping(id,false);
                }
                //removing when hitting the player
                if(playership.hit(laser))
                {
                    explosionList.add(new Explosion(explosion,new Rectangle(playership.boundingBox),1.4f));
                    playership.lives--;
                    playership.Shield = playerShieldAmount;
                    once = true;
                }

                iterator.remove();
            }
        }


        if(playership.Shield == MenuScreen.playerShieldAmount && once)
        {
            long id = ShieldUpSound.play(1.0f);
            ShieldUpSound.setLooping(id,false);
            once = false;
        }
    }


    private void Collisions2()
    {
        //for each player laser, check whether it intersects an enemy ship
        ListIterator<Lasers> iterator = playerLaserList2.listIterator(); //going through the list of playerLaser to add or remove ... etc
        while(iterator.hasNext())
        {
            Lasers laser = iterator.next();
            ListIterator<EnemyShip> temp = enemyshipsList.listIterator();

            while(temp.hasNext())
            {
                EnemyShip enemyship = temp.next();
                if(enemyship.intersects(laser.boundingBox))
                {
                    //removing when hitting the enemy
                    if(enemyship.hit(laser))
                    {
                        temp.remove();
                        explosionList.add(new Explosion(explosion,new Rectangle(enemyship.boundingBox),0.7f));
                        EnemyCounter--;
                        scorePlayerTwo++;
                    }
                    iterator.remove();
                    break;
                }
            }
        }


        //for each enemy laser, check whether it intersects the player ship
        iterator = enemyLaserList.listIterator(); //going through the list of playerLaser to add or remove ... etc
        while(iterator.hasNext())
        {
            Lasers laser = iterator.next();
            if(playership2.intersects(laser.boundingBox) && playership2.lives > 0)
            {
                if(playership2.Shield == 1)
                {
                    long id = ShieldDownSound.play(1.0f);
                    ShieldDownSound.setLooping(id,false);
                }
                //removing when hitting the player
                if(playership2.hit(laser))
                {
                    explosionList.add(new Explosion(explosion,new Rectangle(playership2.boundingBox),1.4f));
                    playership2.lives--;
                    playership2.Shield = playerShieldAmount;
                    once = true;
                }

                iterator.remove();
            }
        }


        if(playership2.Shield == MenuScreen.playerShieldAmount && once)
        {
            long id = ShieldUpSound.play(1.0f);
            ShieldUpSound.setLooping(id,false);
            once = false;
        }
    }


    private void moveEnemies(EnemyShip enemyship,float delta)
    {
        float leftlimit, rightlimit, uplimit, downlimit;
        leftlimit = -enemyship.boundingBox.x;
        downlimit = (float)(World_height - (World_height*0.35f)) -enemyship.boundingBox.y - enemyship.boundingBox.height;

        rightlimit = World_width - enemyship.boundingBox.x - enemyship.boundingBox.width;
        uplimit =  World_height - enemyship.boundingBox.y - enemyship.boundingBox.height;

        float xMove = enemyship.getDirection().x * enemyship.m_speed * delta;
        float yMove = enemyship.getDirection().y * enemyship.m_speed * delta;

        if(xMove > 0 )
        {
            xMove = Math.min(xMove,rightlimit);
        }
        else
        {
            xMove = Math.max(xMove,leftlimit);
        }


        if(yMove > 0 )
        {
            yMove = Math.min(yMove,uplimit);
        }
        else
        {
            yMove = Math.max(yMove,downlimit);
        }

        enemyship.translate(xMove,yMove);
    }


    //movements and world limit @Menna Ammar ..... Updated by Kareem (second player added)
    private void inputs(float delta)
    {
        //keyboard input for player 1
        float leftlimit, rightlimit, uplimit, downlimit;
        leftlimit = -playership.boundingBox.x;
        downlimit = -playership.boundingBox.y;

        rightlimit= World_width - playership.boundingBox.x - playership.boundingBox.width;
        uplimit= (World_height/2) - playership.boundingBox.y - playership.boundingBox.height;

        //Player 1 shooting
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && playership.lives > 0)
        {
            if(playership.canFireLaser())
            {
                long id = laserSound.play(1.0f);
                laserSound.setLooping(id,false);

                Lasers[] lasers = playership.fireLasers();
                for(Lasers laser : lasers)
                {
                    playerLaserList.add(laser);
                }
            }

        }



        //moving forward
        if(Gdx.input.isKeyPressed(Input.Keys.D) && rightlimit > 0)
        {
            /*float xChange = playership.m_speed*delta;
            xChange = Math.min(xChange,rightlimit);
            playership.translate(xChange,0.0f);*/
            playership.translate(Math.min(playership.m_speed*delta,rightlimit),0f);
        }

        //moving upward
        if(Gdx.input.isKeyPressed(Input.Keys.W) && uplimit > 0)
        {
            playership.translate(0f,Math.min(playership.m_speed*delta,uplimit));
        }

        //moving left
        if(Gdx.input.isKeyPressed(Input.Keys.A) && leftlimit < 0)
        {
            /*float xChange = -playership.m_speed*delta;
            xChange = Math.max(xChange,leftlimit);
            playership.translate(xChange,0.0f);*/

            playership.translate(Math.max(-playership.m_speed*delta,leftlimit),0f);
        }

        //Moving downward
        if(Gdx.input.isKeyPressed(Input.Keys.S) && downlimit < 0)
        {
            playership.translate(0f,Math.max(-playership.m_speed*delta,downlimit));
        }


        //checks if it is multiplayer or not
        if(MenuScreen.multiOrNot)
        {
            //KeyBoard inputs for player 2
            float leftlimit2, rightlimit2, uplimit2, downlimit2;
            leftlimit2 = -playership2.boundingBox.x;
            downlimit2 = -playership2.boundingBox.y;

            rightlimit2 = World_width - playership2.boundingBox.x - playership2.boundingBox.width;
            uplimit2 = (World_height / 2) - playership2.boundingBox.y - playership2.boundingBox.height;


            //Player 2 Shooting
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && playership2.lives > 0)
            {
                if (playership2.canFireLaser()) {
                    long id = laserSound.play(1.0f);
                    laserSound.setLooping(id, false);

                    Lasers[] lasers = playership2.fireLasers();
                    for (Lasers laser : lasers) {
                        playerLaserList2.add(laser);
                    }
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightlimit2 > 0) {
                playership2.translate(Math.min(playership2.m_speed * delta, rightlimit2), 0f);
            }

            //moving upward
            if (Gdx.input.isKeyPressed(Input.Keys.UP) && uplimit2 > 0) {
                playership2.translate(0f, Math.min(playership2.m_speed * delta, uplimit2));
            }

            //moving left
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftlimit2 < 0) {
                playership2.translate(Math.max(-playership2.m_speed * delta, leftlimit2), 0f);
            }

            //Moving downward
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downlimit2 < 0) {
                playership2.translate(0f, Math.max(-playership2.m_speed * delta, downlimit2));
            }
        }
    }


    private void Modes()
    {
        playerShieldAmount = MenuScreen.playerShieldAmount;

        enemySpeed = MenuScreen.enemySpeed;
        enemyShieldAmount = MenuScreen.enemyShieldAmount;
        enemyLaserSpeed = MenuScreen.enemyLaserSpeed;
        enemyTimeShot = MenuScreen.enemyTimeShot;

        if(playerShieldAmount == 7)
        {
            MaxEnemyNum = 1;
        }
        else if(playerShieldAmount == 5)
        {
            MaxEnemyNum = 3;
        }
        else if(playerShieldAmount == 3)
        {
            MaxEnemyNum = 5;
        }
    }


    public void pauseMenu()
    {
        labelStart = new com.badlogic.gdx.scenes.scene2d.ui.Label("Resume",mySkin);
        labelStart.setSize(labelStart.getWidth()*3,labelStart.getHeight()*3);
        labelStart.setFontScale(3,3);
        labelStart.setPosition((Gdx.graphics.getWidth()/2) - labelStart.getWidth()/2, Gdx.graphics.getHeight()/2);

        labelExit = new Label("Exit",mySkin);
        labelExit.setSize(labelExit.getWidth() * 3,labelExit.getHeight() * 3);
        labelExit.setPosition(labelStart.getX() + (labelExit.getWidth() * 0.6f),labelStart.getY() - (labelStart.getY() * 0.35f));
        labelExit.setFontScale(3,3);

        labelStart.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                state = State.RUN;
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


        labelExit.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                multi.changeScreen(new MenuScreen(multi));
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

        stage.addActor(labelStart);
        stage.addActor(labelExit);
        pauseOnce = false;
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width,height,true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause()
    {
        batch.begin();

        renderBackground(0,batch);

        ListIterator<EnemyShip> enemyiterator = enemyshipsList.listIterator();
        while (enemyiterator.hasNext())
        {
            EnemyShip enemyship = enemyiterator.next();
            //Enemy ships
            enemyship.draw(batch);
        }


        //lasers
        renderLasers(0);

        //player ships
        playership.draw(batch);

        if(MenuScreen.multiOrNot)
        {
            playership2.draw(batch);
        }


        //explosions
        renderExplosions(0);

        //draw Score
        drawScore();

        batch.end();
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
        textureAtlas.dispose();
        explosion.dispose();
        mySkin.dispose();
        laserSound.dispose();
        ShieldDownSound.dispose();
        ShieldUpSound.dispose();
        stage.dispose();
    }
}