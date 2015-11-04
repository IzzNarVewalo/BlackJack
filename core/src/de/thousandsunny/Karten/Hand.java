package de.thousandsunny.Karten;

import de.thousandsunny.Game.GameScreen;

import java.util.ArrayList;

public class Hand {
    public ArrayList<Karte> karten = new ArrayList<>();

    //leere Hand
    public Hand(){
    }

    //public Hand(int [] kartenWert){for (int i = 0; i < kartenWert.length; i++) {
    //        Karte karte = new Karte(kartenWert[i]);
    //        karten.add(karte);
    //    }
    //}
    //public Hand(Karte[] karten){
    //    for (int i = 0; i < karten.length; i++){
    //        Karte karte = new Karte(karten[i].getKartenWert());
    //        this.karten.add(karte);
    //    }
    //}
    //

    //fügt der Hand eine Karte mit Kartenwert zu
    public void karteHinzufuegen(Karte karte){
        karten.add(karte);
    }

    public boolean besitztKartenWert(int kartenWert){
        for (Karte karte : karten){
            if (karte.getKartenWert() == kartenWert){
                return true;
            }
        }
        return false;
    }

    public boolean besitztZeichenWert(int zeichenWert){
        boolean besitzt = false;
        for (Karte karte : karten)
            besitzt = (karte.getZeichenWert() == zeichenWert);
        return besitzt;
    }

    //zählt den BlackJackwert einer Hand
    public static int kartenZaehlen(boolean Spieler){
        int gesamtBlackJackWert = 0, spielerAsse = asseZaehlen(true), dealerAsse = asseZaehlen(false);
        if (Spieler) {
            for (int i = 0; i < GameScreen.getSpieler().getKarten().size(); i++) {
                gesamtBlackJackWert += GameScreen.getSpieler().getKarten().get(i).getBlackJackWert();
                if (gesamtBlackJackWert > 21 && spielerAsse > 0) {
                    gesamtBlackJackWert -= 10;
                    spielerAsse--;
                }
            }
        } else {
            for (int i = 0; i < GameScreen.getDealer().getKarten().size(); i++){
                gesamtBlackJackWert += GameScreen.getDealer().getKarten().get(i).getBlackJackWert();
                if (gesamtBlackJackWert > 21 && dealerAsse > 0){
                    gesamtBlackJackWert -= 10;
                    dealerAsse--;
                }
            }
        }
      return gesamtBlackJackWert;
    }

    //zählt die Asse des Spielers
    private static int asseZaehlen(boolean spieler){
        int counter = 0;
        if (spieler) {
            for (int i = 0; i < GameScreen.getSpieler().getKarten().size(); i++) {
                if (GameScreen.getSpieler().getKarten().get(i).getZeichenWert() == 14)
                    counter++;
            }
        } else {
            for ( int i = 0; i < GameScreen.getDealer().getKarten().size(); i++){
                if (GameScreen.getDealer().getKarten().get(i).getZeichenWert() == 14)
                        counter++;
            }
        }
        return counter;
    }

    //gibt die karten einer Hand zurück
    public ArrayList<Karte> getKarten() {
        return karten;
    }
}
