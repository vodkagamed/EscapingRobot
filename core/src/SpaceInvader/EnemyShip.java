package SpaceInvader;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

class EnemyShip extends Ships{

    Vector2 direction;
    float timesinceLastMove = 0;
    float directionfreq = 0.75f;

    Random random = new Random();


    public EnemyShip(float m_speed, int shield, float width, float height, float xCenter,
                     float yCenter, float laser_width, float laser_height, float laser_speed,
                     float time_Between_shots, TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion,
                     TextureRegion laserTextureRegion)
    {
        super(m_speed, shield, width, height, xCenter, yCenter, laser_width,
                laser_height, laser_speed, time_Between_shots, shipTextureRegion,
                shieldTextureRegion, laserTextureRegion);

        direction = new Vector2(0,-1);
    }

    public Vector2 getDirection()
    {
        return direction;
    }

    //moving the enemy randomly
    private void randomdirection()
    {
        double temp = random.nextDouble() * (2 * Math.PI); //radians
        direction.x = (float)Math.sin(temp);
        direction.y = (float)Math.cos(temp);
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //checking the last time the enemy moved randomly
        timesinceLastMove += deltaTime;
        if(timesinceLastMove > directionfreq)
        {
            randomdirection();
            timesinceLastMove -= directionfreq;
        }
    }

    @Override
    public Lasers[] fireLasers()
    {
        Lasers [] laser = new Lasers[1];
        laser[0] = new Lasers(boundingBox.x+(boundingBox.width*0.5f),boundingBox.y -laser_height,
                laser_width,laser_height,laser_speed,laserTextureRegion);

        /*laser[1] = new Lasers(boundingBox.x+(boundingBox.width*0.82f),boundingBox.y -laser_height,
                laser_width,laser_height,laser_speed,laserTextureRegion);*/

        time_since_LastShot = 0;

        return laser;
    }

    @Override
    public void draw(Batch batch){
        batch.draw(shipTextureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        if (Shield > 0){
            batch.draw(shieldTextureRegion,boundingBox.x,boundingBox.y-boundingBox.height*0.18f,boundingBox.width,boundingBox.height);
        }
    }


}
