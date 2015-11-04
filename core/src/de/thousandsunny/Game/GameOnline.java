package de.thousandsunny.Game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import de.thousandsunny.Karten.Blatt;
import de.thousandsunny.Karten.HandOnline;
import de.thousandsunny.Karten.Karte;
import de.thousandsunny.Screen.Login;
import de.thousandsunny.Screen.OnlineMenu;
import de.thousandsunny.Screen.Options;
import de.thousandsunny.Screen.OptionsOnline;
import de.thousandsunny.Sounds.Volume;

import java.util.HashMap;
import java.util.Map;

public class GameOnline implements Screen {
    private boolean starten = true, verdoppelt = false, ende = false, gewonnen, blackjack, gotGuthaben= false;
    protected int spielerCounter, dealerCounter;
    protected TextButton neuesSpiel, mainMenu, quitButton;
    protected static int guthaben= 40;
    protected static int einsatz = 0;
    private Texture tisch;
    private Stage stage, endBild;
    private Table end;
    private SpriteBatch batch;
    protected Blatt deckBlatt = new Blatt();
    protected TextButton verdoppeln, keineKarte, karte, pause;
    protected KarteGeben ersteDealerKarte;
    private static HandOnline spieler, dealer;
    public enum Status {START, LAUFEND, PAUSE, VORBEI, SONDERFALL}
    protected static Status status = Status.START;

    @Override
    public void show() {
        if (!gotGuthaben)
            getOnlineGuthaben();
        Gdx.input.setInputProcessor(stage);
        //Einsatz zurücksetzen
        einsatz = 0;
        tisch = new Texture(Gdx.files.internal("table.jpg"));
        Image hintergrund = new Image(tisch);
        batch = new SpriteBatch();
        stage = new Stage();
        spieler = new HandOnline();
        dealer = new HandOnline();

        endBild = new Stage();
        //Tabelle zeichnen
        end = new Table(Options.skin);
        end.setBounds(256, 192, 512, 384);
        end.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Options.grau))));

        //Buttons initialisieren
        neuesSpiel = new TextButton("neues Spiel", Options.skin);
        mainMenu = new TextButton("Onlinebildschirm", Options.skin);
        quitButton = new TextButton("Spiel beenden", Options.skin);

        //Buttons Funktionen zuteilen
        neuesSpiel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rundeNeu();
            }
        });
        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new OnlineMenu());
            }
        });
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        dealer.karteHinzufuegen(deckBlatt.zieheKarte());
        dealer.karteHinzufuegen(deckBlatt.zieheKarte());
        spieler.karteHinzufuegen(deckBlatt.zieheKarte());
        spieler.karteHinzufuegen(deckBlatt.zieheKarte());

        stage.addActor(hintergrund);
    }

    @Override
    public void render(float delta) {
        //clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (status) {
            case START:
                auswahl();
                break;
            case LAUFEND:
                spielen();
                break;
            case PAUSE:
                optionen();
                break;
            case VORBEI:
                endbildschirm(gewonnen);
                break;
            case SONDERFALL:
                sonderfall(blackjack);
                break;
        }

        if (guthaben < 5) {
            neuesSpiel.remove();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            status = Status.PAUSE;
    }

    public void getOnlineGuthaben(){
        Map<String, String> benutzername = new HashMap<>();
        benutzername.put("benutzername", Login.name);
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl("http://1000sunny.de/blackjack/getguthaben.php");
        request.setContent(HttpParametersUtils.convertHttpParameters(benutzername));

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String response = httpResponse.getResultAsString();
                guthaben = Integer.parseInt(response.substring(2));
                Gdx.app.log("guthaben", httpResponse.getResultAsString());
            }

            @Override
            public void failed(Throwable t) {
            }

            @Override
            public void cancelled() {
            }
        });
        gotGuthaben = true;
        show();
    }

    public static void setOnlineGuthaben(){
        Map<String, String> konto = new HashMap<>();
        konto.put("benutzername", Login.name);
        konto.put("guthaben", Integer.toString(guthaben));
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl("http://1000sunny.de/blackjack/setguthaben.php");
        request.setContent(HttpParametersUtils.convertHttpParameters(konto));

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
            }

            @Override
            public void failed(Throwable t) {
            }

            @Override
            public void cancelled() {
            }
        });
    }

    //zeichnet die Buttons um den Einsatz zu wählen
    private void auswahl(){
        Gdx.input.setInputProcessor(GeldOnline.stage);
        //tisch zeichnen
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        //Blatt zeichnen (kleiner je weniger karten im spiel sind)
        batch.begin();
        for (int i = 0; i < Blatt.getMenge(); i++) {
            batch.draw(Karte.kartenRueckseite, 118 - (i / 8), 495 + i / 5, 110, 170);
        }
        Options.font.draw(batch, "Einsatz     : ", 12, 200);
        Options.font.draw(batch, "Guthaben : " + guthaben, 9, 165);
        batch.end();
        GeldOnline.stage.act(Gdx.graphics.getDeltaTime());
        GeldOnline.stage.draw();
    }

    //das eigentliche Spiel
    private void spielen(){
        Gdx.input.setInputProcessor(stage);
        //tisch zeichnen
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        //Blatt zeichnen (kleiner je weniger karten im spiel sind)
        batch.begin();
        for (int i = 0; i < Blatt.getMenge(); i++) {
            batch.draw(Karte.kartenRueckseite, 118 - (i / 8), 495 + i / 5, 110, 170);
        }

        Options.font.draw(batch, "bisherige Punkte: " + HandOnline.kartenZaehlen(true), 12, 270);
        Options.font.draw(batch, "Einsatz     : " + einsatz, 12, 200);
        Options.font.draw(batch, "Guthaben : " + guthaben, 9, 165);
        batch.end();

        if (HandOnline.kartenZaehlen(true) > 21) {
            gewonnen = false;
            status = Status.VORBEI;
        }

        if (HandOnline.kartenZaehlen(false) > 21){
            gewonnen = true;
            status = Status.VORBEI;
        }

        if (spieler.getKarten().get(0).getBlackJackWert() + spieler.getKarten().get(1).getBlackJackWert() == 21){
            blackjack = true;
            status = Status.SONDERFALL;
        }

        if (verdoppelt || guthaben < einsatz)
            verdoppeln.remove();
        //if (spieler.getKarten().size() > 2)
        //    teilen.remove();


        if (starten)
            if (einsatz != 0) {
                austeilen();
            }
            else
                auswahl();
    }

    //ruft das Pausemenü auf
    private void optionen(){
        Gdx.input.setInputProcessor(OptionsOnline.pause);
        //tisch zeichnen
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        //Blatt zeichnen (kleiner je weniger karten im spiel sind)
        batch.begin();
        for (int i = 0; i < Blatt.getMenge(); i++) {
            batch.draw(Karte.kartenRueckseite, 118 - (i / 8), 495 + i / 5, 110, 170);
        }
        batch.end();
        OptionsOnline.pause.act(Gdx.graphics.getDeltaTime());
        OptionsOnline.pause.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            status = Status.LAUFEND;
    }

    //teilt nach wählen des Einsatzes die karten aus
    private void austeilen(){
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                //teilt für den Spieler die Karten aus
                if (spielerCounter < spieler.getKarten().size()) {
                    stage.addActor(new KarteGeben(true, spielerCounter, spieler.getKarten().get(spielerCounter).zuBild()));
                    if (Volume.sfx)
                        Volume.austeilen.play(Volume.SFX_VOLUME);
                    spielerCounter++;
                    //teilt für den Dealer die Karten aus
                } else if (dealerCounter < dealer.getKarten().size()) {
                    //die erste Karte wird beim Dealer verdeckt
                    if (dealerCounter == 0)
                        stage.addActor(ersteDealerKarte = new KarteGeben(false, dealerCounter, Karte.kartenRueckseite));
                    else
                        stage.addActor(new KarteGeben(false, dealerCounter, dealer.getKarten().get(dealerCounter).zuBild()));
                    if (Volume.sfx)
                        Volume.austeilen.play(Volume.SFX_VOLUME);
                    dealerCounter++;
                }
            }
        }, 0.3f, 0.3f, spieler.getKarten().size() + dealer.getKarten().size() - 1);
        starten = false;
        buttons();
    }

    //zeichnet die 5 Buttons am unteren Rand um das Spiel zu steuern
    private void buttons(){
        //Buttons Namen und größe zuweisen
        verdoppeln = new TextButton("verdoppeln", Options.skin);
        keineKarte = new TextButton("keine Karte", Options.skin);
        karte = new TextButton("Karte", Options.skin);
        //teilen = new TextButton("splitten", Options.skin);
        pause = new TextButton("Pause", Options.skin);
        int WIDTH = 200, HEIGHT = 55;

        //Buttons größe und Position zuweisen
        verdoppeln.setBounds(4, 40, WIDTH, HEIGHT);
        keineKarte.setBounds(208, 40, WIDTH, HEIGHT);
        karte.setBounds(412, 40, WIDTH, HEIGHT);
        //teilen.setBounds(616, 40, WIDTH, HEIGHT);
        pause.setBounds(820, 40, WIDTH, HEIGHT);

        //Buttons zeichnen
        if (!verdoppelt && guthaben >= einsatz)
            stage.addActor(verdoppeln);
        stage.addActor(keineKarte);
        stage.addActor(karte);
        //if (spieler.getKarten().get(1).gleichesZeichen(spieler.getKarten().get(0)))
        //stage.addActor(teilen);
        stage.addActor(pause);

        //Buttons funktionen zuweisen
        verdoppeln.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                guthaben -= einsatz;
                setOnlineGuthaben();
                einsatz = einsatz * 2;
                spieler.karteHinzufuegen(deckBlatt.zieheKarte());
                stage.addActor(new KarteGeben(true, spieler.getKarten().size() -1, spieler.getKarten().get(spieler.getKarten().size() -1).zuBild()));
                if (Volume.sfx)
                    Volume.austeilen.play(Volume.SFX_VOLUME);
                verdoppelt = true;
                if (HandOnline.kartenZaehlen(true) > 21) {
                    gewonnen = false;
                    status = Status.VORBEI;
                }
                else
                    rundeVorbei();
            }
        });
        keineKarte.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rundeVorbei();
            }
        });
        karte.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                spieler.karteHinzufuegen(deckBlatt.zieheKarte());
                stage.addActor(new KarteGeben(true, spieler.getKarten().size() - 1, spieler.getKarten().get(spieler.getKarten().size() - 1).zuBild()));
                if (Volume.sfx)
                    Volume.austeilen.play(Volume.SFX_VOLUME);
                verdoppelt = true;
            }
        });
        //teilen.addListener(new ClickListener() {
        //    @Override
        //    public void clicked(InputEvent event, float x, float y) {
        //        spieler.karteHinzufuegen(deckBlatt.zieheKarte());
        //        stage.addActor(new KarteGeben(true, spieler.getKarten().size() - 1, spieler.getKarten().get(spieler.getKarten().size() - 1).zuBild()));
        //        if (Volume.sfx)
        //            Volume.austeilen.play(Volume.SFX_VOLUME);
        //        verdoppelt = true;
        //    }
        //});
        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                status = Status.PAUSE;
            }
        });
    }

    //checkt die buttons für den Einsatz
    private void checkeEinsatz(){
        if (guthaben >= 5)
            GeldOnline.table.addActor(GeldOnline.chip5);
        if (guthaben >= 10)
            GeldOnline.table.addActor(GeldOnline.chip10);
        if (guthaben >= 25)
            GeldOnline.table.addActor(GeldOnline.chip25);

        if (guthaben < 25)
            GeldOnline.chip25.remove();
        if (guthaben < 10)
            GeldOnline.chip10.remove();
        if (guthaben < 5)
            GeldOnline.chip5.remove();
    }

    //Fenster das bei unentschieden/Blackjack aufgerufen wird
    private void sonderfall(boolean blackjack){
        Gdx.input.setInputProcessor(endBild);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.begin();
        for (int i = 0; i < Blatt.getMenge(); i++) {
            batch.draw(Karte.kartenRueckseite, 118 - (i / 8), 495 + i / 5, 110, 170);
        }
        batch.end();
        if (!ende)
            schlussSonderfall(blackjack);
        endBild.addActor(end);
        endBild.act(Gdx.graphics.getDeltaTime());
        endBild.draw();
    }

    //Fenster das beim ende der Runde aufgerufen wird
    private void endbildschirm(boolean gewonnen){
        Gdx.input.setInputProcessor(endBild);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.begin();
        for (int i = 0; i < Blatt.getMenge(); i++) {
            batch.draw(Karte.kartenRueckseite, 118 - (i / 8), 495 + i / 5, 110, 170);
        }
        batch.end();
        if (!ende)
            schluss(gewonnen);
        endBild.addActor(end);
        endBild.act(Gdx.graphics.getDeltaTime());
        endBild.draw();
    }

    //Endbild zeichnen
    private void schluss(boolean gewonnen){
        ende = true;
        //Titel und Buttons der tabelle zuordnen
        if (gewonnen){
            guthaben += 2 * einsatz;
            setOnlineGuthaben();
            end.add(new Label("Gewonnen!", Options.skin)).padBottom(30).row();
            if (HandOnline.kartenZaehlen(false) > 21)
                end.add("Der Dealer hat " + (HandOnline.kartenZaehlen(false) - 21) + " Punkte zu viel!").row();
            else
                end.add("Du hast die besseren Karten! ").row();
        }
        else {
            end.add(new Label("Verloren", Options.skin)).padBottom(30).row();
            if (HandOnline.kartenZaehlen(true) > 21)
                end.add("Du hast " + (HandOnline.kartenZaehlen(true) - 21) + " Punkte zu viel!").row();
            else
                end.add("Der Dealer hat die besseren Karten ").row();
        }
        end.add(neuesSpiel).size(250, 50).padBottom(20).row();
        end.add(mainMenu).size(250, 50).padBottom(20).row();
        end.add(quitButton).size(250, 50);
    }

    //Endbild zeichnen
    private void schlussSonderfall(boolean blackjack){
        ende = true;
        //Titel und Buttons der tabelle zuordnen
        if (blackjack){
            if (spieler.getKarten().get(0).getBlackJackWert() + spieler.getKarten().get(1).getBlackJackWert() == 21) {
                guthaben += 2.5 * einsatz;
                setOnlineGuthaben();
                end.add(new Label("Black Jack!", Options.skin)).padBottom(30).row();
                end.add("Du hast ein Black Jack!").row();
            }
            else if(dealer.getKarten().get(0).getBlackJackWert() + dealer.getKarten().get(1).getBlackJackWert() == 21)
            {
                end.add(new Label("Black Jack", Options.skin)).padBottom(30).row();
                end.add("Der dealer hat einen Black Jack").row();
            }
        }
        else {
            guthaben += einsatz;
            setOnlineGuthaben();
            end.add(new Label("Unentschieden", Options.skin)).padBottom(30).row();
            end.add("Ihr habt beide " + (HandOnline.kartenZaehlen(true)) + " Punkte!").row();
        }
        end.add(neuesSpiel).size(250, 50).padBottom(20).row();
        end.add(mainMenu).size(250, 50).padBottom(20).row();
        end.add(quitButton).size(250, 50);
    }

    //deckt die erste Karte des Dealers auf
    private void rundeVorbei(){
        ersteDealerKarte.remove();
        stage.addActor(new KarteGeben(true, dealer.getKarten().get(0).zuBild()));
        stage.addActor(new KarteGeben(false, dealer.getKarten().get(1).zuBild()));
        if (Volume.sfx)
            Volume.austeilen.play(Volume.SFX_VOLUME);
        buttonsEntfernen();
        if (dealer.getKarten().get(0).getBlackJackWert() + dealer.getKarten().get(1).getBlackJackWert() == 21){
            blackjack = true;
            status = Status.VORBEI;
        }
        while (HandOnline.kartenZaehlen(false) < 17) {
            dealer.karteHinzufuegen(deckBlatt.zieheKarte());
            stage.addActor(new KarteGeben(false, dealer.getKarten().size() - 1, dealer.getKarten().get((dealer.getKarten().size() - 1)).zuBild()));
            if (Volume.sfx)
                Volume.austeilen.play(Volume.SFX_VOLUME);
        }

        if (HandOnline.kartenZaehlen(true) == HandOnline.kartenZaehlen(false)) {
            blackjack = false;
            status = Status.SONDERFALL;
            schlussSonderfall(blackjack);
        }

        gewonnen = !(HandOnline.kartenZaehlen(false) < 22 && HandOnline.kartenZaehlen(false) > HandOnline.kartenZaehlen(true));
        status = Status.VORBEI;

    }

    //setzt das Spiel für den Beginn einer neuen runde zurück
    private void rundeNeu(){
        reset();
        show();
    }

    //setzt alles auf anfang zurück
    private void reset(){
        checkeEinsatz();
        spielerCounter = 0;
        dealerCounter = 0;
        einsatz = 0;
        buttonsEntfernen();
        ende = false;
        starten = true;
        verdoppelt = false;
        status = Status.START;
    }
    //entfernt die Buttons am unteren Rand
    private void buttonsEntfernen(){
        //teilen.remove();
        keineKarte.remove();
        karte.remove();
        verdoppeln.remove();
        pause.remove();
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
        status = Status.START;
        dispose();
    }

    @Override
    public void dispose() {
        setOnlineGuthaben();
        batch.dispose();
        stage.dispose();
        endBild.dispose();
        tisch.dispose();
    }

    //gibt das Guthaben des Spielers zurück
    public static int getGuthaben() {
        return guthaben;
    }

    //setzt das Guthaben des Spielers
    public static void setGuthaben(int geld) {
        guthaben = geld;
    }

    //setzt den Status des Spiels
    public static void setStatus(Status state) {
        status = state;
    }

    //setzt den Einsatz des Spielers
    public static void setEinsatz(int geld) {
        einsatz = geld;
    }

    //gibt die Karten des Spielers zurück
    public static HandOnline getSpieler() {
        return spieler;
    }

    //gibt die Karten des Dealers zurück
    public static HandOnline getDealer() {
        return dealer;
    }
}
