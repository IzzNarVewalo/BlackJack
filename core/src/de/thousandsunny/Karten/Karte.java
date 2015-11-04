package de.thousandsunny.Karten;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.lang.String;

public class Karte{
    private static TextureAtlas bilder;
    private final int kartenWert;
    private final int zeichenWert;
    private final int blackJackWert;

    public static Sprite kartenRueckseite, chip5, chip10, chip25;

    //teilt der Karte einen Zeichenwert und einen BlackJackwert zu
    public Karte(int kartenWert){
        this.kartenWert = kartenWert;
        zeichenWert = zuZeichenWert(kartenWert);
        blackJackWert = zuBlackJackWert(zeichenWert);
    }

    //Konstanten für die Bilder
    public static final String[] kartenBild;
    static {
        kartenBild = new String[]{
                "cardClubs2", "cardClubs3", "cardClubs4", "cardClubs5", "cardClubs6", "cardClubs7", "cardClubs8", "cardClubs9", "cardClubs10", "cardClubsJ", "cardClubsQ", "cardClubsK", "cardClubsA",
                "cardDiamonds2", "cardDiamonds3", "cardDiamonds4", "cardDiamonds5", "cardDiamonds6", "cardDiamonds7", "cardDiamonds8", "cardDiamonds9", "cardDiamonds10", "cardDiamondsJ", "cardDiamondsQ", "cardDiamondsK", "cardDiamondsA",
                "cardHearts2", "cardHearts3", "cardHearts4", "cardHearts5", "cardHearts6", "cardHearts7", "cardHearts8", "cardHearts9", "cardHearts10", "cardHeartsJ", "cardHeartsQ", "cardHeartsK", "cardHeartsA",
                "cardSpades2", "cardSpades3", "cardSpades4", "cardSpades5", "cardSpades6", "cardSpades7", "cardSpades8", "cardSpades9", "cardSpades10", "cardSpadesJ", "cardSpadesQ", "cardSpadesK", "cardSpadesA",
                "cardClubs2", "cardClubs3", "cardClubs4", "cardClubs5", "cardClubs6", "cardClubs7", "cardClubs8", "cardClubs9", "cardClubs10", "cardClubsJ", "cardClubsQ", "cardClubsK", "cardClubsA",
                "cardDiamonds2", "cardDiamonds3", "cardDiamonds4", "cardDiamonds5", "cardDiamonds6", "cardDiamonds7", "cardDiamonds8", "cardDiamonds9", "cardDiamonds10", "cardDiamondsJ", "cardDiamondsQ", "cardDiamondsK", "cardDiamondsA",
                "cardHearts2", "cardHearts3", "cardHearts4", "cardHearts5", "cardHearts6", "cardHearts7", "cardHearts8", "cardHearts9", "cardHearts10", "cardHeartsJ", "cardHeartsQ", "cardHeartsK", "cardHeartsA",
                "cardSpades2", "cardSpades3", "cardSpades4", "cardSpades5", "cardSpades6", "cardSpades7", "cardSpades8", "cardSpades9", "cardSpades10", "cardSpadesJ", "cardSpadesQ", "cardSpadesK", "cardSpadesA",
                "cardClubs2", "cardClubs3", "cardClubs4", "cardClubs5", "cardClubs6", "cardClubs7", "cardClubs8", "cardClubs9", "cardClubs10", "cardClubsJ", "cardClubsQ", "cardClubsK", "cardClubsA",
                "cardDiamonds2", "cardDiamonds3", "cardDiamonds4", "cardDiamonds5", "cardDiamonds6", "cardDiamonds7", "cardDiamonds8", "cardDiamonds9", "cardDiamonds10", "cardDiamondsJ", "cardDiamondsQ", "cardDiamondsK", "cardDiamondsA",
                "cardHearts2", "cardHearts3", "cardHearts4", "cardHearts5", "cardHearts6", "cardHearts7", "cardHearts8", "cardHearts9", "cardHearts10", "cardHeartsJ", "cardHeartsQ", "cardHeartsK", "cardHeartsA",
                "cardSpades2", "cardSpades3", "cardSpades4", "cardSpades5", "cardSpades6", "cardSpades7", "cardSpades8", "cardSpades9", "cardSpades10", "cardSpadesJ", "cardSpadesQ", "cardSpadesK", "cardSpadesA",
                "cardClubs2", "cardClubs3", "cardClubs4", "cardClubs5", "cardClubs6", "cardClubs7", "cardClubs8", "cardClubs9", "cardClubs10", "cardClubsJ", "cardClubsQ", "cardClubsK", "cardClubsA",
                "cardDiamonds2", "cardDiamonds3", "cardDiamonds4", "cardDiamonds5", "cardDiamonds6", "cardDiamonds7", "cardDiamonds8", "cardDiamonds9", "cardDiamonds10", "cardDiamondsJ", "cardDiamondsQ", "cardDiamondsK", "cardDiamondsA",
                "cardHearts2", "cardHearts3", "cardHearts4", "cardHearts5", "cardHearts6", "cardHearts7", "cardHearts8", "cardHearts9", "cardHearts10", "cardHeartsJ", "cardHeartsQ", "cardHeartsK", "cardHeartsA",
                "cardSpades2", "cardSpades3", "cardSpades4", "cardSpades5", "cardSpades6", "cardSpades7", "cardSpades8", "cardSpades9", "cardSpades10", "cardSpadesJ", "cardSpadesQ", "cardSpadesK", "cardSpadesA",
                "cardClubs2", "cardClubs3", "cardClubs4", "cardClubs5", "cardClubs6", "cardClubs7", "cardClubs8", "cardClubs9", "cardClubs10", "cardClubsJ", "cardClubsQ", "cardClubsK", "cardClubsA",
                "cardDiamonds2", "cardDiamonds3", "cardDiamonds4", "cardDiamonds5", "cardDiamonds6", "cardDiamonds7", "cardDiamonds8", "cardDiamonds9", "cardDiamonds10", "cardDiamondsJ", "cardDiamondsQ", "cardDiamondsK", "cardDiamondsA",
                "cardHearts2", "cardHearts3", "cardHearts4", "cardHearts5", "cardHearts6", "cardHearts7", "cardHearts8", "cardHearts9", "cardHearts10", "cardHeartsJ", "cardHeartsQ", "cardHeartsK", "cardHeartsA",
                "cardSpades2", "cardSpades3", "cardSpades4", "cardSpades5", "cardSpades6", "cardSpades7", "cardSpades8", "cardSpades9", "cardSpades10", "cardSpadesJ", "cardSpadesQ", "cardSpadesK", "cardSpadesA",
                "cardClubs2", "cardClubs3", "cardClubs4", "cardClubs5", "cardClubs6", "cardClubs7", "cardClubs8", "cardClubs9", "cardClubs10", "cardClubsJ", "cardClubsQ", "cardClubsK", "cardClubsA",
                "cardDiamonds2", "cardDiamonds3", "cardDiamonds4", "cardDiamonds5", "cardDiamonds6", "cardDiamonds7", "cardDiamonds8", "cardDiamonds9", "cardDiamonds10", "cardDiamondsJ", "cardDiamondsQ", "cardDiamondsK", "cardDiamondsA",
                "cardHearts2", "cardHearts3", "cardHearts4", "cardHearts5", "cardHearts6", "cardHearts7", "cardHearts8", "cardHearts9", "cardHearts10", "cardHeartsJ", "cardHeartsQ", "cardHeartsK", "cardHeartsA",
                "cardSpades2", "cardSpades3", "cardSpades4", "cardSpades5", "cardSpades6", "cardSpades7", "cardSpades8", "cardSpades9", "cardSpades10", "cardSpadesJ", "cardSpadesQ", "cardSpadesK", "cardSpadesA",
        };
    }

    //ladet das Spritesheet
    public static void load() {
        bilder = new TextureAtlas(Gdx.files.internal("Casino/casino.atlas"));
        kartenRueckseite = bilder.createSprite("cardBack_blue2");
        chip5 = bilder.createSprite("chipValue5");
        chip10 = bilder.createSprite("chipValue10");
        chip25 = bilder.createSprite("chipValue25");
    }

    //teilt der Karte ein Bild zu
    public Sprite zuBild(){
        return bilder.createSprite(kartenBild[getKartenWert() - 1]);
    }

    //weist der Karte einen Zeichenwert zu
    public static int zuZeichenWert(int kartenWert) {
        int zeichenWert = (kartenWert % 13) + 1;
        if (zeichenWert == 1)
            zeichenWert = 14;
        return zeichenWert;
    }

    //weist der Karte einen BlackJackwert zu
    public static int zuBlackJackWert(int zeichenWert) {
        int blackJackWert;
        if (zeichenWert <= 10)
             blackJackWert = zeichenWert;
        else if (zeichenWert <= 13)
            blackJackWert = 10;
        else blackJackWert = 11;
        return blackJackWert;
    }

    //vergleicht ob zwei Karten den gleichen Zeichenwert haben
    public boolean gleichesZeichen(Karte andere){
        Karte andereKarte = andere;
        return (getZeichenWert() == andereKarte.getZeichenWert());
    }

    //gibt den BlackJackwert einer Karte zurück
    public int getBlackJackWert() {
        return blackJackWert;
    }

    //gibt den Kartenwert einer Karte zurück
    public int getKartenWert() {
        return kartenWert;
    }

    //gibt den Zeichenwert einer Karte zurück
    public int getZeichenWert() {
        return zeichenWert;
    }
}
