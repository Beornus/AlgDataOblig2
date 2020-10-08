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

    private Node<T> finnNode(int indeks)
    {
        //if(hode == null) return hode;                     //Om hode är null så returnerar vi hode;
        if(indeks < antall/2){                              //Om index är mindre än antal noder delat på 2 så söker vi efter noden från hodet.
            Node<T> current = hode;
            for (int i = 0; i < indeks; i++) {              //Löper igenom tills vi kommer fram till index.
                current = current.neste;
            }
            return current;                                 //Returnerar indexen.
        }
        else{
            Node<T> current = hale;                         //Sätter en current på hode.
            for(int i =antall-1; i>indeks; i--){            //Startar forlökken på sista värdet och går bakåt för att hitta index.
                current = current.forrige;
            }
            return current;
        }
    }

    public DobbeltLenketListe(T[] a) {
        this();                                                                               //Kallar standardkonstruktören med originalvärden som vi kan bygga vidare på.
        Objects.requireNonNull(a, "Tabellen a är null!");                            //Får ikke skrevet ut denne meldingen enda.

        if(a.length < 0) throw new NullPointerException("Tabellen a är null!");

        int i = 0; for (; i < a.length && a[i] == null; i++);                                 //Går förbi alla värden som är null i en lökke, för att komma fram till det första värdet som inte är null.

        if (i < a.length){                                                                    //Om i är mindre än längden på a.
            Node<T> temporar = hode = new Node<>(a[i], null, null);              //Detta blir den första noden i vår lista, men pekare mot null både framåt och bakåt.
            antall++;                                                                         //Plussar på antalet med 1 då vi har en ny node.

            for (i++; i < a.length; i++)                                                      //Plussar på i med 1 så vi går vidare till nästa värde i vår tabell och löper sen genom resten av värdena.
            {
                if (a[i] != null)                                                             //Om värdet av i inte är null så går vi in här.
                {
                    temporar = temporar.neste = new Node<>(a[i], null, null);   //Skapar en ny node som kommer in efter den första.
                    antall++;                                                                //Plussar på antalet.
                }
            }
            hale = temporar;                                                                 //Sätter värdet på hale till det temporära värdet.
        }
    }

    public Liste<T> subliste(int fra,int til){
        //Skapa en metode som gör en egen lista i intervallet [fra:til>. Kolla om index fra och til är lovliga. Annars kasta undantag som i fraTilKontroll()
        fratilKontroll(antall, fra, til);                               //Går igenom fraTilKontroll för att kolla att index är lovliga.
        DobbeltLenketListe<T> nyListe = new DobbeltLenketListe<>();     //Skapar en ny dobbelt lenket liste att lagra det nya intervallet i.
        for(int i = fra; i < til; i++){                                 //Går igenom listan vi tog in från intervallet fra:til och använder leggInn-metoden för att lägga in värdena i den aktuella i i vår lista.
            nyListe.leggInn(finnNode(i).verdi);
        }
        nyListe.endringer = 0;                                          //Sätter endringer till 0 då vi har en ny lista.
        return nyListe;                                                 //Returnerar vår nya lista.
    }

    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                                       // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                                                  // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > tablengde(" + antall + ")");

        if (fra > til)                                                     // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    @Override
    public int antall(){
        if(!tom()){                                                        //Om listan inte är tom så går vi in och räknar antal noder.
            Node aktuell=hode;                                             //Sätter en variabel på första hodenoden som vi ska räkna framåt.
        antall=0;                                                          //Sätter räknaren på 0. (Kan nog ta bort denna då vi har 0 i konstruktören.)
        while(aktuell!=null){                                              //Så länge aktuell inte är null så går vi igenom lökken.
        antall++;                                                          //Ökar på räknaren för antall.
        aktuell=aktuell.neste;                                             //Sätter aktuell till att vara nästa värde i listan.
        }
        return antall;                                                     //Returnerar antal värden i listan.
        }
        else{                                                              //Om listan är tom så returneras 0.
            return 0;
        }
    }

    @Override
    public boolean tom(){
        return hode == null;                                               //Effektiviserade denna koden.
    }

    @Override
    public boolean leggInn(T verdi){
        Objects.requireNonNull(verdi, "Värdet är null");          //Kollar om värdet som kommer in är null.
        Node<T> siste = new Node<>(verdi);                                 //Skapar en ny node med värdet vi får in.
        siste.neste = null;                                                //Sätter nestepekaren til siste att vara null.
        if(hode == null){                                                  //Om hode är null så är listan tom.
            hode = siste;                                                  //Sätter hode och hale til att vara vårt värde vi fick in.
            siste.forrige = null;
        }
        else{
            Node<T> current = hode;                                        //Annars sätter vi en current till hodenoden.
            while(current.neste != null){                                  //Löper igenom så länge listan inte är null.
                current = current.neste;                                   //Flyttar current ett steg framåt.
            }
            current.neste = siste;                                         //Sätter siste til att vara efter current som här är det sista värdet.
            siste.forrige = current;                                       //Sätter forrigepekeren på siste till current.
        }
        hale = siste;                                                      //Sätter halepekaren till siste.
        antall++;                                                          //Plussar på antall
        endringer++;                                                       //Plussar på endringer
        return true;                                                       //Returnerar lyckad inläggning.
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
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi){
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks,T nyverdi){
        Objects.requireNonNull(nyverdi, "Värdet är null");          //kollar så att det nya värdet inte är null.
        indeksKontroll(indeks, false);                               //Om indeks är false så är det ogiltigt antal.

        Node<T> hittadNode = finnNode(indeks);                              //Sätter en pekare mot noden som finnNode-metoden hittar med sitt index.
        T gammaltVarde = hittadNode.verdi;                                  //Sparar värdet på vår hittade node i en gammaltVarde-variabel.
        hittadNode.verdi = nyverdi;                                         // Sätter värdet på den hittade noden lik det nya värdet vi får in.

        return gammaltVarde;                                                //Returnerar det gamla värdet till den hittade noden.

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
        StringBuilder skriv = new StringBuilder();                          //Skapar en StringBuilder som jag kan appenda information på.
        skriv.append('[');                                                  //Startar toStringen med en klammeparentes, oavsett om listan är tom eller inte.
        if(!tom()){                                                         //Om listan inte är tom så går vi in här.
            Node<T> current = hode;                                         //Sätter en current Node som pekar på hode.
            skriv.append(current.verdi);                                    //lägger till värdet för current i vår StringBuilder
            current = current.neste;                                        //Gör så att current hoppar fram ett steg i listan.

            while(current != null){                                         //Löper igenom hela listan med whilelökke.
                skriv.append(',').append(' ').append(current.verdi);        //lägger till karaktärer för att få rätt formatering plus värdet för current i vår StringBuilder
                current = current.neste;                                    //Hoppar ett steg.
            }
        }
        skriv.append(']');                                                  //Avslutar med en klammeparentes. Även tom lista.
        return skriv.toString();                                            //Returnerar toString.

    }

    public String omvendtString(){
        StringBuilder skrivBak = new StringBuilder();
        skrivBak.append('[');
        if (!tom()){
            Node<T> current = hale;                                         //Ca samma metod som ovan, bara att jag startar med current på halen och går bakover med forrige.
            skrivBak.append(current.verdi);
            current = current.forrige;

            while(current != null){
                skrivBak.append(',').append(' ').append(current.verdi);
                current = current.forrige;
            }
        }
        skrivBak.append(']');
        return skrivBak.toString();
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
