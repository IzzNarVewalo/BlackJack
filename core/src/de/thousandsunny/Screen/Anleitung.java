package de.thousandsunny.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Anleitung implements Screen {
    private Stage stage;
    private TextButton startBildschirm, seite1, seite2;
    private Texture regeln1, regeln2;
    private Image regelSeite1, regelSeite2;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        regeln1 = new Texture(Gdx.files.internal("regeln_1.jpg"));
        regeln2 = new Texture(Gdx.files.internal("regeln_2.jpg"));
        regelSeite1 = new Image(regeln1);
        regelSeite2 = new Image(regeln2);

        startBildschirm = new TextButton("zum Startbildschirm", Options.skin);
        startBildschirm.setBounds(5, 5, 300, 40);
        startBildschirm.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });

        seite2 = new TextButton("Seite 2", Options.skin);
        seite2.setBounds(869, 5, 150, 40);
        seite2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                zuSeite2();
            }
        });

        seite1 = new TextButton("Seite 1", Options.skin);
        seite1.setBounds(869, 5, 150, 40);
        seite1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                zuSeite1();
            }
        });

        stage.addActor(regelSeite1);
        stage.addActor(startBildschirm);
        stage.addActor(seite2);
    }

    private void zuSeite1(){
        seite1.remove();
        regelSeite2.remove();
        startBildschirm.remove();
        stage.addActor(regelSeite1);
        stage.addActor(seite2);
        stage.addActor(startBildschirm);
    }

    private void zuSeite2(){
        seite2.remove();
        regelSeite1.remove();
        startBildschirm.remove();
        stage.addActor(regelSeite2);
        stage.addActor(seite1);
        stage.addActor(startBildschirm);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
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
    public void dispose() {

    }
}
