package de.thousandsunny.Game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.thousandsunny.Karten.Blatt;

public class KarteGeben extends Image {

    //methode die die Karten austeilt
    public KarteGeben(boolean spieler, int nummer, Sprite sprite) {
        super(sprite);
        setBounds(118 - (Blatt.getMenge() / 8), 395 + (Blatt.getMenge() / 5), 110, 170);
        setTouchable(Touchable.disabled);

        if (spieler) {
            MoveAndScaleToAction moveSpieler = new MoveAndScaleToAction();
            moveSpieler.setPosition(440 + (35 * nummer), 170);
            moveSpieler.setScale(0.82f);
            moveSpieler.setDuration(0.3f);
            KarteGeben.this.addAction(moveSpieler);
        } else {
            MoveAndScaleToAction moveDealer = new MoveAndScaleToAction();
            moveDealer.setPosition(440 + 35 * nummer, 570);
            moveDealer.setScale(0.82f);
            moveDealer.setDuration(0.3f);
            KarteGeben.this.addAction(moveDealer);
        }
    }

    //methode zum aufdecken der ersten Karte des Dealers
    public KarteGeben(boolean ersteKarte, Sprite sprite){
        super(sprite);
        if (ersteKarte)
        setBounds(440, 570, 90, 140);
        else
            setBounds(475, 570, 90, 140);
    }
}
