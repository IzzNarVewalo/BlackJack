package de.thousandsunny.Sounds;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import static com.badlogic.gdx.Gdx.*;

public class Volume{
    //Globale Variable der Soundeffekte und Hintergrundmusik deklarieren und initialisieren
    public static float SFX_VOLUME = 0.12f, BACKGROUND_VOLUME = 0.1f;
    public static boolean sfx = true, background = true;
    //Hintergrundmusik und SFX deklarieren
    private static Music loop1, loop2, loop3, loop4;
    public static Sound austeilen, chip;

    //Hintergrundmusik und SFX laden
    public static void load() {
        loop1 = audio.newMusic(files.internal("stereoloop1.ogg"));
        loop2 = audio.newMusic(files.internal("stereoloop2.ogg"));
        loop3 = audio.newMusic(files.internal("stereoloop3.ogg"));
        loop4 = audio.newMusic(files.internal("stereoloop4.ogg"));
        austeilen = audio.newSound(files.internal("CardFlip.wav"));
        chip = audio.newSound(files.internal("chip.wav"));

    }

    //loop aus random Sounddateien erstellen
    public static void randomLoop(){
        if (background) {
            //nach Ende der Sounddatei beginnt eine weitere
            loop1.setOnCompletionListener(music -> Volume.randomLoop());
            loop2.setOnCompletionListener(music -> Volume.randomLoop());
            loop3.setOnCompletionListener(music -> Volume.randomLoop());
            loop4.setOnCompletionListener(music -> Volume.randomLoop());

            //random Sounddatei abspielen
            int zufall = MathUtils.random(1, 4);
            switch (zufall) {
                case 1:
                    loop1.setVolume(BACKGROUND_VOLUME);
                    loop1.play();
                    break;
                case 2:
                    loop2.setVolume(BACKGROUND_VOLUME);
                    loop2.play();
                    break;
                case 3:
                    loop3.setVolume(BACKGROUND_VOLUME);
                    loop3.play();
                    break;
                case 4:
                    loop4.setVolume(BACKGROUND_VOLUME);
                    loop4.play();
                    break;
            }
        }
        else{
            muteBackground();
        }
    }

    //Hintergrundmusik aktivieren
    public static void unmuteBackground(){
        background = true;
        randomLoop();
    }

    //Hintergrundmusik deaktivieren
    public static void muteBackground() {
        background = false;
        endBackground();
    }

    //SFX aktivieren
    public static void unmuteSFX(){
    sfx = true;
        chip.play(SFX_VOLUME);
    }

    //SFX deaktivieren
    public static void muteSFX(){
     sfx = false;
    }

    //Hintergrundmusik stoppen
    public static void endBackground() {
        if (loop1.isPlaying()) {
            loop1.stop();
        }
        if (loop2.isPlaying()) {
            loop2.stop();
        }
        if (loop3.isPlaying()) {
            loop3.stop();
        }
        if (loop4.isPlaying()) {
            loop4.stop();
        }
    }
}

