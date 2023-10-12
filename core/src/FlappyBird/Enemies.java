package FlappyBird;

import com.badlogic.gdx.graphics.Texture;

public class Enemies extends Objects {
    private static Texture enemy=new Texture("Robot/pxArt.png");

    public Enemies(float x) {
        super(enemy,enemy.getWidth()/12, enemy.getHeight()/11, x, 0);
    }

    public void enemyUpdate(float dt){
        position.add(-dt,0);
    }

    public static void dispose(){
        enemy.dispose();
    }

}
