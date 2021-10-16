package com.iuw.game;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Screen;
        import com.badlogic.gdx.graphics.OrthographicCamera;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.scenes.scene2d.InputEvent;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
        import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
        import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    final Process game;
    private final Stage stage;
    private final Texture img;
    private final OrthographicCamera camera;
    final float WIDTH_BUT = 300, HEIGHT_BUT = 70;
    public MainMenuScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);

        img = new Texture("main_theme.jpg");
        stage = new Stage(new ScreenViewport());
        //Button size 300x70
        TextButton play_but = new TextButton("Play", Process.gameSkin), set_but = new TextButton("Settings", Process.gameSkin), exit_but = new TextButton("Exit", Process.gameSkin); // 300x70 sizes
        play_but.setPosition(50f,600f); play_but.setSize(WIDTH_BUT, HEIGHT_BUT);
        set_but.setPosition(50f,500f); set_but.setSize(WIDTH_BUT, HEIGHT_BUT);
        exit_but.setPosition(50f,400f); exit_but.setSize(WIDTH_BUT, HEIGHT_BUT);
        play_but.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new ConfigScreen(game));
            }
        });
        set_but.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new SetScreen(game));
            }
        });
        exit_but.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.exit();
                game.batch.dispose();
                game.font.dispose();
                game.SpaceMusic.dispose();
            }
        });
        stage.addActor(play_but);
        stage.addActor(set_but);
        stage.addActor(exit_but);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(img, 0f, 0f);
        game.batch.end();
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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        img.dispose();
    }
}
