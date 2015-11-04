package de.thousandsunny.Game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.thousandsunny.Karten.Karte;
import de.thousandsunny.Screen.Options;
import de.thousandsunny.Sounds.Volume;import java.lang.Override;

public class GeldEinsetzen extends ApplicationAdapter{
    public static Stage stage;
    public static ImageButton
            chip5 = new ImageButton(new SpriteDrawable(new Sprite(Karte.chip5))),
            chip10 = new ImageButton(new SpriteDrawable(new Sprite(Karte.chip10))),
            chip25 = new ImageButton(new SpriteDrawable(new Sprite(Karte.chip25)));
    public static Table table;
    private static final int SIZE = 100, PADDING = 64;
    private Label pleite = new Label("Du hast kein Geld mehr!", Options.skin);
    private TextButton neuesSpiel = new TextButton("zum Startbildschirm", Options.skin);

    public static void einsatz() {
        //Layer erstellen
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //Tabelle zeichnen
        table = new Table();
        table.setBounds((Gdx.graphics.getWidth() - (3 * SIZE + 2 * PADDING)) / 2,
                (Gdx.graphics.getHeight() - PADDING) / 2,
                3 * SIZE + 2 * PADDING,
                2 * PADDING);
        table.align(Align.center);
        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Options.grau))));

        //ImageButtons erstellen

        //ImageButtons einsetzen
        chip5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.setEinsatz(5);
                GameScreen.setGuthaben(GameScreen.getGuthaben() - 5);
                if (Volume.sfx)
                    Volume.chip.play(Volume.SFX_VOLUME);
                GameScreen.setStatus(GameScreen.Status.LAUFEND);
            }
        });

        chip10.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.setEinsatz(10);
                GameScreen.setGuthaben(GameScreen.getGuthaben() - 10);
                if (Volume.sfx)
                    Volume.chip.play(Volume.SFX_VOLUME);
                GameScreen.setStatus(GameScreen.Status.LAUFEND);
            }
        });

        chip25.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.setEinsatz(25);
                GameScreen.setGuthaben(GameScreen.getGuthaben() - 25);
                if (Volume.sfx)
                    Volume.chip.play(Volume.SFX_VOLUME);
                GameScreen.setStatus(GameScreen.Status.LAUFEND);
            }
        });

        if (GameScreen.getGuthaben() > 5)
        table.add(chip5).size(SIZE, SIZE);
        if (GameScreen.getGuthaben() > 10)
        table.add(chip10).size(SIZE, SIZE).padLeft(PADDING);
        if (GameScreen.getGuthaben() > 25)
        table.add(chip25).size(SIZE, SIZE).padLeft(PADDING);

        if (GameScreen.getGuthaben() < 5)
            chip5.remove();
        if (GameScreen.getGuthaben() < 10)
            chip10.remove();
        if (GameScreen.getGuthaben() < 25)
            chip25.remove();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }
}
