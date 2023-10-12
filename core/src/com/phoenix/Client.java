package com.phoenix;

import FlappyBird.GameScreen;
import FlappyBird.StartScreen;
import SpaceInvader.MenuScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Client implements Screen
{
	private final MultipleScreen multi;
	private Stage stage;
	private TextButton button1;
	private TextButton button2;

	private Label label,label2,label3,label4;
	private Texture Back;

	private SpriteBatch batch;
	//private VideoPlayer videoPlayer;
	Animation<TextureRegion> animation,animation2;
	float elapsed;
	private Skin mySkin;

	public Client(MultipleScreen x)
	{
		multi = x;
	}

	@Override
	public void show()
	{
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		mySkin = new Skin(Gdx.files.internal("Skin/glassyui/glassy-ui.json"));

		batch = new SpriteBatch();
		animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Preview/flappy.gif").read());
		animation2 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Preview/Space Invader.gif").read());
		//videoPlayer = VideoPlayerCreator.createVideoPlayer();

		Back = new Texture("Client/Icon.png");

		int row_height = Gdx.graphics.getHeight() / 12;
		int col_width = Gdx.graphics.getWidth() / 12;


		button1 = new TextButton("Space Invader",mySkin,"small");
		button1.setSize(col_width*4,row_height);
		button1.setPosition(col_width,Gdx.graphics.getHeight()-600);

		button2 = new TextButton("Flappy Robot",mySkin,"small");
		button2.setSize(col_width*4,row_height);
		button2.setPosition(Gdx.graphics.getWidth()-col_width*5,Gdx.graphics.getHeight()-600);


		//Labels
		label = new Label("Made By :",mySkin);
		label.setPosition(0,Gdx.graphics.getHeight() * 0.13f);
		label.setColor(Color.BLACK);

		label2 = new Label("Team Phoenix",mySkin);
		label2.setPosition(0,Gdx.graphics.getHeight() * 0.09f);
		label2.setColor(Color.BLACK);

		label3 = new Label("Team 1 : Kareem, Menna Ammar, Habiba, Mariam, Momen",mySkin);
		label3.setPosition(0,Gdx.graphics.getHeight() * 0.05f);
		label3.setColor(Color.BLACK);

		label4 = new Label("Team 2 : Hassan, Malek, Menna Abd-Almageed, Yomna, Mohamed",mySkin);
		label4.setPosition(0,Gdx.graphics.getHeight() * 0.01f);
		label4.setColor(Color.BLACK);
		button1.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				Gdx.graphics.setForegroundFPS(144);
				//Gdx.graphics.setResizable(true);
				multi.changeScreen( new MenuScreen(multi));
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				button1.getLabel().setColor(Color.RED);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				button1.getLabel().setColor(Color.WHITE);
			}
		});

		button2.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				Gdx.graphics.setForegroundFPS(60);
				multi.changeScreen( new StartScreen(multi));
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				button2.getLabel().setColor(Color.RED);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				button2.getLabel().setColor(Color.WHITE);
			}
		});


		stage.addActor(button1);
		stage.addActor(button2);
		stage.addActor(label);
		stage.addActor(label2);
		stage.addActor(label3);
		stage.addActor(label4);
	}

	@Override
	public void render(float delta)
	{
		/*if (!videoPlayer.isPlaying()) { // As soon as the video is finished, we start the file again using the same player.
			try {
				videoPlayer.play(Gdx.files.internal("Preview/flappy.webm"));
			} catch (FileNotFoundException e) {
				Gdx.app.error("gdx-video", "Oh no!");
			}
		}*/

		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



		batch.begin();
		//batch.draw(Back,(Gdx.graphics.getWidth()/2) - (Back.getWidth()),Gdx.graphics.getHeight()/2 - (Back.getHeight()),Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(animation.getKeyFrame(elapsed), button2.getX(), button2.getY() + button2.getHeight() * 2,button2.getWidth(), button2.getHeight() * 4);
		batch.draw(animation2.getKeyFrame(elapsed), button1.getX(), button1.getY() + button1.getHeight() * 2,button1.getWidth(), button1.getHeight() * 4);
		//Texture frame = videoPlayer.getTexture();
		/*if (frame != null)
			batch.draw(frame,Gdx.graphics.getWidth()-(Gdx.graphics.getWidth() / 12)*5 , 400, button2.getWidth(), 300);*/
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
		stage.dispose();
		mySkin.dispose();
		//videoPlayer.dispose();
		batch.dispose();
	}
}
