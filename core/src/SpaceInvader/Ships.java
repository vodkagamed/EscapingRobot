package SpaceInvader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Ships
{
    // attributes(characteristics)
    float m_speed;
    int Shield;

    //position
    Rectangle boundingBox;

    //laser info
    float laser_width,laser_height;
    float laser_speed;
    float time_Between_shots;
    float time_since_LastShot = 0;

    //graphics
    TextureRegion shipTextureRegion, shieldTextureRegion,laserTextureRegion;

    public Ships(float m_speed,int shield,
                  float width,float height,float xCenter,float yCenter,float laser_width,float laser_height,float laser_speed
                  ,float time_Between_shots, TextureRegion shipTextureRegion,
                  TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion)
    {
        this.m_speed = m_speed;
        this.Shield = shield;
        this.boundingBox = new Rectangle( xCenter - width/2,yCenter - height/2,width,height);
        this.laser_width = laser_width;
        this.laser_height = laser_height;
        this.laser_speed = laser_speed;
        this.time_Between_shots = time_Between_shots;
        this.shipTextureRegion = shipTextureRegion;
        this.shieldTextureRegion = shieldTextureRegion;
        this.laserTextureRegion = laserTextureRegion;
    }


    //updating how long it's been since the last laser was fired
    public void update(float deltaTime)
    {
          time_since_LastShot += deltaTime;
    }


    //checks if the ship able to fire or not by checking the time which passed since the last shot
    public boolean canFireLaser()
    {
        return (time_since_LastShot - time_Between_shots >= 0);
    }


    public abstract Lasers[] fireLasers();


    public boolean hit(Lasers laser)
    {
        if(Shield > 0)
        {
            Shield--;
            return false;
        }
        return true;
    }


    //checking if 2 Sprites (bullet and enemy for example) hits each other
    public boolean intersects(Rectangle otherRectangle)
    {
        return  boundingBox.overlaps(otherRectangle);
    }


    //responsible for moving
    public void translate(float x,float y)
    {
        boundingBox.setPosition(boundingBox.x + x,boundingBox.y+y);
    }

    public void draw(Batch batch){
        batch.draw(shipTextureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        if (Shield > 0){
            batch.draw(shieldTextureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        }
    }
}