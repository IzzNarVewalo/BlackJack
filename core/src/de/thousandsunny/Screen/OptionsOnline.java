package de.thousandsunny.Screen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.thousandsunny.Game.GameOnline;
import de.thousandsunny.Game.GameScreen;
import de.thousandsunny.Sounds.Volume;


public class OptionsOnline extends ApplicationAdapter {
    //Variablen deklarieren
    public static Stage pause;
    private static CheckBox muteSFX, muteBackground;
    private static Slider sfxVolume, backgroundVolume;

    public static void load() {
        Gdx.input.setInputProcessor(pause);
        //skin setzen
        pause = new Stage(new ScreenViewport());

        //Tabelle zeichnen
        Table table = new Table(Options.skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.align(Align.center | Align.top);
        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Options.grau))));
        table.padTop(Gdx.graphics.getHeight() / 4);

        //Slider initialisieren
        sfxVolume = new Slider(0.00f, 0.40f, 0.02f, false, Options.skin);
        sfxVolume.setValue(Volume.SFX_VOLUME);
        backgroundVolume = new Slider(0.00f, 0.40f, 0.02f, false, Options.skin);
        backgroundVolume.setValue(Volume.BACKGROUND_VOLUME);

        //Checkboxen initialisieren und aktivieren
        muteSFX = new CheckBox(" SFX", Options.skin);
        muteBackground = new CheckBox(" Hintergrundmusik", Options.skin);
        muteSFX.setChecked(true);
        muteBackground.setChecked(true);

        //Buttons initialisieren
        TextButton back = new TextButton("zum Spiel", Options.skin);
        TextButton quitButton = new TextButton("Beenden", Options.skin);

        //Slider Funktionen zuteilen
        sfxVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Volume.SFX_VOLUME = sfxVolume.getValue();
                if (Volume.sfx)
                Volume.chip.play(Volume.SFX_VOLUME);
            }
        });
        backgroundVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Volume.BACKGROUND_VOLUME = backgroundVolume.getValue();
                Volume.endBackground();
                Volume.randomLoop();
            }
        });

        //Checkboxen Funktionen zuteilen
        muteSFX.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!muteSFX.isChecked()) {
                    Volume.muteSFX();
                } else {
                    Volume.unmuteSFX();
                }
            }
        });
        muteBackground.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!muteBackground.isChecked()) {
                    Volume.muteBackground();
                } else {
                    Volume.unmuteBackground();
                }
            }
        });

        //Buttons Funktionen zuteilen
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameOnline.setStatus(GameOnline.Status.LAUFEND);
            }
        });

        //Titel, Buttons, Slider und Checkboxen der tabelle zuordnen
        table.add(new Label("Pause", Options.skin)).padBottom(30).colspan(2).row();
        table.add(muteSFX).left();
        table.add(sfxVolume).size(200, 5).colspan(1).padBottom(10).row();
        table.add(muteBackground).left();
        table.add(backgroundVolume).size(200, 5).padBottom(20).row();
        table.add(back).size(200, 50);
        table.add(quitButton).size(200, 50);
        pause.addActor(table);
    }
}