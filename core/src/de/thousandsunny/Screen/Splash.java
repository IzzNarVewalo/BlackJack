package de.thousandsunny.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.thousandsunny.Game.GeldEinsetzen;
import de.thousandsunny.Game.GeldOnline;
import de.thousandsunny.Sounds.Volume;import java.lang.Override;

public class Splash implements Screen {
    //Variablen deklarieren
    private Texture splash;
    private Stage stage;

    @Override
    public void show() {
        //Variablen Initialisieren
        splash = new Texture(Gdx.files.internal("splash.jpg"));
        Image splashImage = new Image(splash);
        stage = new Stage();
        Volume.load();
        Options.load();
        OptionsOnline.load();
        GeldEinsetzen.einsatz();
        GeldOnline.einsatz();
        stage.addActor(splashImage);
        // splash fade-in
        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f), Actions.delay(2), Actions.run(() -> ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu()))));
    }

    @Override
    public void render(float delta) {
        //Hintergrund = schwarz
        Gdx.gl.glClearColor(0, 0, 0, 1);
        //clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Actors updaten
        stage.act();
        //Actors zeichnen
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
        //RAM leeren
        splash.dispose();
        stage.dispose();
    }
}
