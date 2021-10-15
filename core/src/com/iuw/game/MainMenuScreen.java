package com.iuw.game;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Screen;
        import com.badlogic.gdx.graphics.OrthographicCamera;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.scenes.scene2d.InputEvent;
        import com.badlogic.gdx.scenes.scene2d.InputListener;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.ui.Button;
        import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;
        import com.badlogic.gdx.utils.ScreenUtils;
        import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    final Process game;
    private Stage stage;
    private Texture img;
    private OrthographicCamera camera;

    public MainMenuScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);

        img = new Texture("main_theme.jpg");
        stage = new Stage(new ScreenViewport());
        Button play_but = new Button(Process.gameSkin), set_but = new Button(Process.gameSkin), exit_but = new Button(Process.gameSkin); // 300x70 sizes
        play_but.setPosition(50,600);
        set_but.setPosition(50,500);
        exit_but.setPosition(50,400);
        play_but.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ConfigScreen(game));

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        set_but.addListener(new InputListener(){

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                //game.setScreen(new ConfigScreen(game));
                game.setScreen(new SetScreen(game));
                dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        exit_but.addListener(new InputListener(){

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                //game.setScreen(new ConfigScreen(game));
                dispose();

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(play_but); stage.addActor(set_but); stage.addActor(exit_but);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);


        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(img, 0, 0);
        game.batch.end();
        stage.act();
        stage.draw();
       // game.button3.getClickListener().touchUp();
//        if (Gdx.input.isTouched()) {
//            game.setScreen(new ConfigScreen(game));
//            dispose();
//        }
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
stage.dispose();
img.dispose();
    }
}
