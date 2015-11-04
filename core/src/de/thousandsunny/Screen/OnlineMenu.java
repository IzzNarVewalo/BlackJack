package de.thousandsunny.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.thousandsunny.Game.GameOnline;
import de.thousandsunny.Game.GameScreen;

public class OnlineMenu implements Screen {
    private Stage stage;

    //Konstanten (Hoehe breite und Freiraum)  der Buttons setzen
    private static final int    BUTTON_WIDTH = 220,
                                BUTTON_HEIGHT = 45,
                                BUTTON_PADDING = (Gdx.graphics.getHeight() / 2 - BUTTON_HEIGHT * 4) / 3;

    @Override
    public void show() { //skin setzen
        stage = new Stage(new ScreenViewport());

        //Oberflaeche zeichnen und gruppieren
        Table table = new Table(Options.skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.align(Align.center | Align.top);
        table.padTop(Gdx.graphics.getHeight() / 4);

        //Buttons zeichnen
        TextButton play = new TextButton("Spiel starten", Options.skin);
        TextButton geldEinzahlen = new TextButton("Geld einzahlen", Options.skin);
        TextButton howto = new TextButton("Anleitung", Options.skin);
        TextButton quitButton = new TextButton("Spiel Beenden", Options.skin);

        //Buttons Funktionen Zuteilen
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen((new GameOnline()));
            }
        });
        howto.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Anleitung());
            }
        });
        geldEinzahlen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ExtraGuthaben());
            }
        });
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Formatierung
        table.add(play).size(BUTTON_WIDTH, BUTTON_HEIGHT).padBottom(BUTTON_PADDING).row();
        table.add(geldEinzahlen).size(BUTTON_WIDTH, BUTTON_HEIGHT).padBottom(BUTTON_PADDING).row();
        table.add(howto).size(BUTTON_WIDTH, BUTTON_HEIGHT).padBottom(BUTTON_PADDING).row();
        table.add(quitButton).size(BUTTON_WIDTH, BUTTON_HEIGHT);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
