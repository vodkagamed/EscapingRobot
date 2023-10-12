package FlappyBird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GameFont {
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;
    private GlyphLayout text = new GlyphLayout();
    public GameFont(String Path,int size,Color color,Color borderColor,int borderWidth) {
        this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Path));
        this.parameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size=size;
        this.parameter.color= color;
        this.parameter.borderColor=borderColor;
        this.parameter.borderWidth=borderWidth;
        this.font=fontGenerator.generateFont(parameter);
    }
    public void draw(SpriteBatch batch,String text,float x,float y){
        this.text.setText(font,text);
        font.draw(batch,text,x,y);
    }
    public BitmapFont getFont(){
        return this.font=fontGenerator.generateFont(parameter);
    }

    public void dispose(){
        font.dispose();
        fontGenerator.dispose();
    }
    public float getTextwidth(){
        return this.text.width;
    }
    public float getTextheight(){
        return this.text.height;
    }

}