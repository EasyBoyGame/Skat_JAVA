package org.example.logic;


public class Karte {
    public Farbe farbe;
    public Kartenart kartenart;

    public Karte(Farbe farbe, Kartenart kartenart) {
        this.farbe = farbe;
        this.kartenart = kartenart;
    }

    public Farbe getFarbe(){
        return farbe;
    }

    public Kartenart getWert(){
        return kartenart;
    }

    @Override
    public String toString() {
        return farbe + " " + kartenart;
    }
}
