package Oblig2;

////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        //Standardkonstruktör.
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {
        this();  //Kallar standardkonstruktören med originalvärden som vi kan bygga vidare på.
        Objects.requireNonNull(a, "Tabellen a är null!"); //Får ikke skrevet ut denne meldingen enda.

        if(a.length < 0) throw new NullPointerException("Tabellen a är null!");

        int i = 0; for (; i < a.length && a[i] == null; i++); //Går förbi alla värden som är null i en lökke, för att komma fram till det första värdet som inte är null.

        if (i < a.length){ //Om i är mindre än längden på a.
            Node<T> temporar = hode = new Node<>(a[i], null, null);  //Detta blir den första noden i vår lista, men pekare mot null både framåt och bakåt.
            antall++; //Plussar på antalet med 1 då vi har en ny node.

            for (i++; i < a.length; i++) //Plussar på i med 1 så vi går vidare till nästa värde i vår tabell och löper sen genom resten av värdena.
            {
                if (a[i] != null) //Om värdet av i inte är null så går vi in här.
                {
                    temporar = temporar.neste = new Node<>(a[i], null, null); //Skapar en ny node som kommer in efter den första.
                    antall++; //Plussar på antalet.
                }
            }
            hale = temporar; //Sätter värdet på hale till det temporära värdet.
        }
    }

    public Liste<T> subliste(int fra,int til){
        throw new UnsupportedOperationException();
    }

    @Override
    public int antall(){
        if(!tom()){ //Om listan inte är tom så går vi in och räknar antal noder.
            Node aktuell=hode; //Sätter en variabel på första hodenoden som vi ska räkna framåt.
        antall=0; //Sätter räknaren på 0. (Kan nog ta bort denna då vi har 0 i konstruktören.)
        while(aktuell!=null){ //Så länge aktuell inte är null så går vi igenom lökken.
        antall++; //Ökar på räknaren för antall.
        aktuell=aktuell.neste; //Sätter aktuell till att vara nästa värde i listan.
        }
        return antall; //Returnerar antal värden i listan.
        }
        else{ //Om listan är tom så returneras 0.
            return 0;
        }
    }

    @Override
    public boolean tom(){
        //return (hode == null)? true : false; //Övar på denna syntaxen
        if(hode==null){
            return true; //Om hode är null så är listan tom och ska då returnera true.
        }
        else{ // Om hode inte är null så är inte listan tom och returnerar false.
            return false;
        }
    }

    @Override
    public boolean leggInn(T verdi){
        throw new UnsupportedOperationException();
    }

    @Override
    public void leggInn(int indeks,T verdi){
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi){
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks){
        throw new UnsupportedOperationException();
    }

    @Override
    public int indeksTil(T verdi){
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks,T nyverdi){
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean fjern(T verdi){
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks){
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill(){
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString(){
        throw new UnsupportedOperationException();
    }

    public String omvendtString(){
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator(){
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks){
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe
