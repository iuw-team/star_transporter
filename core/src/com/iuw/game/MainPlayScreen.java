package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

public class MainPlayScreen implements Screen {
    final  Process game;
    private Stage stage;
    Texture ship;
    private Rectangle ship_box;
    private long ship_sound_time;

    public MainPlayScreen(final Process game){
        this.game = game;
        ship_box = new Rectangle();
        ship_box.x =  300 - 75;
        ship_box.y = 600;
        ship_box.width = 150;
        ship_box.height = 150;
        ship = new Texture("ship.png"); // 150x150



    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
//
//        if(Gdx.input.isTouched()){
//			Vector3 touchpos = new Vector3();
//			float posX = Gdx.input.getX();
//			float posY = Gdx.input.getY();
//			touchpos.set(posX, posY, 0);
//			camera.unproject(touchpos); // correct camera's system coordinates to touchpos
//			bucket.x = touchpos.x - 150 / 2;
//			bucket.y = touchpos.y - 150 / 2;
//		}
        game.batch.begin();
        game.batch.draw(ship, ship_box.x, ship_box.y);
        game.batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)||
				Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if(TimeUtils.nanoTime() - ship_sound_time > 5e9) {
				switch (MathUtils.random(1, 3)) {
					case 1:
						game.ship_sound_1.play();
						break;
					case 2:
						game.ship_sound_2.play();
						break;
					case 3:
						game.ship_sound_3.play();
						break;
				}
				ship_sound_time = TimeUtils.nanoTime();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) ship_box.x -= 200 * Gdx.graphics.getDeltaTime();
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ship_box.x += 200 * Gdx.graphics.getDeltaTime();
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) ship_box.y -= 200 * Gdx.graphics.getDeltaTime();
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) ship_box.y += 200 * Gdx.graphics.getDeltaTime();
		}
		if(ship_box.x < 0) ship_box.x = 0;
		if(ship_box.x > 600 - 150) ship_box.x = 600 - 150;
		if(ship_box.y > 800 - 150) ship_box.y = 800 - 150;
		if(ship_box.y < 0) ship_box.y = 0;
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
    public void dispose() {
    ship.dispose();
    }
}
