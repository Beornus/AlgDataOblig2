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
        //this();                                                                             //Kallar standardkonstruktören med originalvärden som vi kan bygga vidare på.
        Objects.requireNonNull(a, "Tabellen a är null!");                            //Får ikke skrevet ut denne meldingen enda.
        for(T current : a){                             //Gör en forEach-lökke för att gå igenom alla element i a.
            if(current == null) continue;               //Om det aktuella värdet i vår lökke är null så hoppar den over med continue; (Tips från medstudent. Aldrig använt continue förr)

            Node<T> nyNode = new Node<>(current);       //Skapar en ny node med värdet i current.

            if(hode == null){                           //Om hode är null, dvs att listan är tom, så går vi in här.
                nyNode.forrige = null;                  //Sätter forrigepekeren till vår nya node till null.
                hode = nyNode;                          //Sätter hode till vår nya node.
                antall++;
            }
            else{
                nyNode.forrige = hale;                  //Sätter vår nya nodes forrigepekare till att vara vår hale.
                hale.neste = nyNode;                    //Sätter vår hale sin nestepekare till att vara vår nya node.
                antall++;
            }
            hale = nyNode;                              //Sätter halen till att vara vår nya node som nu ligger längst bak.
            nyNode.neste = null;                        //Sätter vår nya node sin nestepekare till att vara null.
        }
    }

    public Liste<T> subliste(int fra,int til){
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
                    ("til(" + til + ") > antall(" + antall + ")");

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
            siste.forrige = null;
            hode = siste;                                                  //Sätter hode och hale til att vara vårt värde vi fick in.
        }
        else{
            siste.forrige = hale;                                          //Annars sätter vi sistes forrigepekare till halen.
            hale.neste = siste;                                            //Sätter halen sin nestepekare till vår sistevariabel.
        }
        hale = siste;                                                      //Sätter halepekaren till siste.
        antall++;                                                          //Plussar på antall
        endringer++;                                                       //Plussar på endringer
        return true;                                                       //Returnerar lyckad inläggning.
    }

    @Override
    public void leggInn(int indeks,T verdi){
        //Metoden får in en indeks och en verdi som parameter.
        //Den ska sätta in det nya värdet på indeksen i parametern och då kommer alla andra värden efter den indexen att flyttas ett hack framåt.
        //Bara indexer från 0 till antall är giltiga.
        //Passa på fyra tillfällen:
        //1. Listan är tom.
        //2. Verdi ska läggas först i listan.
        //3. Verdi ska läggas bakerst i listan.
        //4. Verdi ska läggas mellan två andra värden i listan.
        //Kom ihåg att sätta alla forrige och nestepekare rätt. Hode ska ha null på forrige och hale ska ha null på neste.
        //Kolla genom checklistan i uppgiften innan du är klar.

        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi){
        if (indeksTil(verdi) != -1){ //Om indexen till indeksTil(verdi) inte är -1 så returneras true. Annars false.
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public T hent(int indeks){
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi){
        if(verdi == null){                                  //Om verdi är null så returneras -1.
            return -1;
        }
        Node<T> current = hode;                             //Sätter en currentpekare till hode.

        for(int i=0; i < antall; i++){                      //Går igenom listan med en forlökke.
            if(current.verdi.equals(verdi)){                //Om värdet till current är samma som verdi i parametern så returneras i.
                return i;
            }
            else{                                           //Annars så sätter vi current till att vara current.neste.
                current = current.neste;
            }
        }
        return -1;                                          //Hit kommer vi om verdi inte finns i listan, då returneras -1.
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
            /*Node<T> current = hale;                                         //Ca samma metod som ovan, bara att jag startar med current på halen och går bakover med forrige.
            skrivBak.append(current.verdi);
            current = current.forrige;*/

            skrivBak.append(hale.verdi);
            Node<T> current = hale.forrige;

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
