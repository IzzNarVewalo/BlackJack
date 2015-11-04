package de.thousandsunny.Karten;

import java.util.Random;

public class Blatt {
    private boolean[] blatt = new boolean[312];
    private static int menge = 312;
    private Random zufall = new Random();

    public Blatt(){
        erstelleBlatt();
    }

    private void erstelleBlatt(){
        for (int i = 0; i < 312; i++)
        {
            blatt[i] = true;
        }
        menge = 312;
    }

    public Karte zieheKarte()
    {
        if (menge <= 15){
            return null;
        }
        int zufallsZahl = zufall.nextInt(menge) + 1;
        int present = 0;
        for (int i = 0; i < 312; i++){
            if (!blatt[i])
            {
                continue;
            }
            present++;
            if (present == zufallsZahl){
                Karte karte = new Karte(i + 1);
                blatt[i] = false;
                menge--;
                return karte;
            }
        }
        return null;
    }

    public Hand verteilen(int handMenge){
        if (handMenge > menge){
            return null;
        }
        Hand hand = new Hand();
        for (int i = 0; i < handMenge; i++)
            zieheKarte();

        return hand;
    }

    public static int getMenge(){
        return menge;
    }

    public void erneuerBlatt(){
        erstelleBlatt();
    }
}
