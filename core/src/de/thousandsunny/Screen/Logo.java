package de.thousandsunny.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.thousandsunny.Karten.Karte;import java.lang.Override;

public class Logo implements Screen {
    //Variablen deklarieren
    private Texture logo;
    private Stage stage;

    @Override
    public void show() {
        //Variablen Initialisieren
        logo = new Texture(Gdx.files.internal("logo.jpg"));
        Image splashLogo = new Image(logo);
        stage = new Stage();
        Karte.load();

        //Logo der stage anlegen
        stage.addActor(splashLogo);

        //logo fade-in
        splashLogo.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f), Actions.delay(2), Actions.run(() -> {
            //zum splash wechseln
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Splash());
        })));
    }

    @Override
    public void render(float delta) {
        //Hintergrund schwarz
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

    //ruft dispose (Methode zum RAM leeren) auf sobald der Screen nicht mehr angezeigt wird
    @Override
    public void hide() {
        dispose();
    }

    //RAM leeren
    @Override
    public void dispose() {
        logo.dispose();
        stage.dispose();
    }
}
