package SpaceInvader;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Explosion
{
    private Animation<TextureRegion> explosionAni;
    private float explosionTimer;

    private Rectangle boundingBox;
    Explosion(Texture texture,Rectangle boundingBox, float totalAnimationTime)
    {
        this.boundingBox = boundingBox;

        //Splitting explosion sheet
        TextureRegion[][] textureRegions = TextureRegion.split(texture,64,64);

        //Convert 2d array to 1d array (Picture is 4 x 4)
        TextureRegion[] textureRegions1 = new TextureRegion[16];
        int index = 0;
        for(int i =0; i <4; i++)
        {
            for(int j = 0; j <4; j++)
            {
                textureRegions1[index] = textureRegions[i][j];
                index++;
            }
        }

        explosionAni = new Animation<TextureRegion>(totalAnimationTime/16,textureRegions1);
        explosionTimer = 0;
    }


    public void update(float delta)
    {
        explosionTimer+=delta;
    }

    public void draw(SpriteBatch batch)
    {
        batch.draw(explosionAni.getKeyFrame(explosionTimer),boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);

    }

    public boolean isFinished()
    {
        return explosionAni.isAnimationFinished(explosionTimer);
    }
}
