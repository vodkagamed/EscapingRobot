package FlappyBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player extends Objects {
    private Vector2  velocity = new Vector2(0,0);
    private static final int Gravity = -30;

    public Player(Texture texture, float width, float height, float xPos, float yPos) {
        super(texture, width, height, xPos, yPos);
    }

     void update(float dt)
    {
        if(position.y>0)
            velocity.add(0,Gravity);    // gravity
      velocity.scl(dt);
        position.add(0.0f,velocity.y);
        velocity.scl(1/dt);          // anti gravity
        if(position.y<0){
            position.y=0;
        }

    }
     void jump(){
        velocity.y = 700;
    }


}

