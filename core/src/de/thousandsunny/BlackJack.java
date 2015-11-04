package de.thousandsunny;

import com.badlogic.gdx.Game;
import de.thousandsunny.Screen.Logo;

public class BlackJack extends Game {
	//Hoehe und Breite des Fensters deklarieren und initialisieren
	public static final int WIDTH = 1024, HEIGHT = 768;

	@Override
	public void create () {
		//Splashes laden
		setScreen(new Logo());
	}
}

