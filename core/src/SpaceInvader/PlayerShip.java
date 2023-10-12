package SpaceInvader;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

class PlayerShip extends Ships{

    int lives; //added by @kareem

    public PlayerShip(float m_speed, int shield, float width, float height, float xCenter,
                      float yCenter, float laser_width, float laser_height, float laser_speed,
                      float time_Between_shots, TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion,
                      TextureRegion laserTextureRegion)
    {
        super(m_speed, shield, width, height, xCenter, yCenter, laser_width,
                laser_height, laser_speed, time_Between_shots, shipTextureRegion,
                shieldTextureRegion, laserTextureRegion);
        lives = 3;
    }



    @Override
    public Lasers[] fireLasers()
    {
        Lasers [] laser = new Lasers[2];
        laser[0] = new Lasers(boundingBox.x+(boundingBox.width*0.253f), boundingBox.y +(boundingBox.height*0.5f),
                laser_width,laser_height,laser_speed,laserTextureRegion);

        laser[1] = new Lasers(boundingBox.x+(boundingBox.width*0.747f), boundingBox.y +(boundingBox.height*0.5f),
                laser_width,laser_height,laser_speed,laserTextureRegion);

        time_since_LastShot = 0;

        return laser;
    }

}
