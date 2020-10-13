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
        Objects.requireNonNull(a, "Tabellen a är null!");
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
            /*Node aktuell=hode;                                             //Sätter en variabel på första hodenoden som vi ska räkna framåt.
            //antall=0;                                                          //Sätter räknaren på 0. (Kan nog ta bort denna då vi har 0 i konstruktören.)
            while(aktuell!=null){                                              //Så länge aktuell inte är null så går vi igenom lökken.
                antall++;                                                          //Ökar på räknaren för antall.
                aktuell=aktuell.neste;                                             //Sätter aktuell till att vara nästa värde i listan.
            }*/
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
        Objects.requireNonNull(verdi, "Värdet är null");        //Kollar om värdet är null.
        indeksKontroll(indeks, true);                            //Kollar att indexen är lovlig.

        if(indeks == 0){                                                //Om index är 0 så ska vi lägga in värdet längst fram i listan.
            Node<T> forste = new Node<>(verdi);                         //Skapar en ny node med verdi
            if(antall != 0){                                            //Om antall ikke er 0 går vi in här.
                forste.neste = hode;                                    //förstas nestepekare pekar på hode.
                hode = forste;                                          //Sätter hode till att vara första.
                forste.forrige = null;                                  //Sätter forrigepekaren till hode att vara null.
            }
            else{                                                       //Om listan är tom så har vi nu bara ett element så det blir både hode och hale.
                hale = hode = forste;
                forste.neste = null;                                    //om listan bara har den ena elementet blir nästapekaren null.
                forste.forrige = null;
            }
        }
        else if(indeks == antall){                                      //Om indeks är samma som antal så ska det nya värdet in bakerst i listan.
            Node<T> siste = new Node<>(verdi);                          //Skapar ny Node med verdi.
            hale = hale.neste = siste;                                  //Sätter hale till att vara hale.neste som ska vara verdi.
            siste.neste = null;                                         //Sätter nestepekaren till att vara null.
        }
        else{
            Node<T> mellan = new Node<>(verdi);                         //Skapar ny node med verdi.
            Node<T> current = hode;                                     //Startar med att sätta en currentpekare på hode.
            Node<T> temp;                                               //Sätter en temporärpekare.
            for(int i = 1; i < indeks; i++){                            //Startar på 1 då vi har gått igenom tillfället med först i listan. Löper igenom fram till indeks.
                current = current.neste;
            }
            temp = current.neste;                                       //Lagrar infon i current.neste i temp.
            temp.forrige = current;                                     //Sätter temp.forrige till att vara current.

            current.neste = mellan;                                     //Här ordnar jag så att alla pekarna stämmer mellan temp och mellan.
            mellan.forrige = current;
            mellan.neste = temp;
            temp.forrige = mellan;
        }
        endringer++;                                                    //Plussar på endringer då jag lagt in ett nytt värde.
        antall++;                                                       //Plussar på antall då vi har fått in ett nytt värde.
    }

    @Override
    public boolean inneholder(T verdi){
        if (indeksTil(verdi) != -1){   //Om indexen till indeksTil(verdi) inte är -1 så returneras true. Annars false.
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
        endringer++;                                                        //Plussar på endringer.

        return gammaltVarde;                                                //Returnerar det gamla värdet till den hittade noden.

    }

    @Override
    public boolean fjern(T verdi){
        if(tom()) return false;                             //Om listan är tom så returneras false.
        Node<T> current = hode;                             //Sätter en current.
        for(int i= 0; i< antall; i++){                      //Löper igenom hela listan för att kolla om värdet finns i listan.
            if(current.verdi.equals(verdi)){                //Om värdet på current matchar vårt värde i parametern går vi in här.
                if(antall == 1){                            //Om antall är 1 så sätter vi hode och hale till null.
                    hode = hale = null;
                }
                else if(antall == 2){                       //Om antall är 2 så är värdet antingen hode eller hale.
                    if(i == 0){
                        hode = hode.neste;
                        hode.forrige = null;
                    }
                    else{
                        hale = hale.forrige;
                        hale.neste = null;
                    }
                }
                else if(i == 0){                            //Om antall är över 2 så kollar jag om värdet är hodet.
                    hode = hode.neste;
                    hode.forrige = null;
                }
                else if(i == antall-1){                     //Annars kollar jag om det är halen.
                    hale = hale.forrige;
                    hale.neste = null;
                }
                else{                                       //Annars så är det mellan två noder och jag ordnar med pekarna.
                    current.forrige.neste = current.neste;
                    current.neste.forrige = current.forrige;
                }
                endringer++;                                //Plussar på endringer
                antall--;                                   //Tar minus på antall
                return true;                                //Returnerar true
            }
            current = current.neste;                        //Om i ikke är värdet så flyttar jag current till att vara current.neste.
        }
        return false;                                       //Om vi kommer hit så är inte värdet i listan och vi returnerar false.
    }

    @Override
    public T fjern(int indeks){
        indeksKontroll(indeks, false);                  //Kollar index.
        T taBort;                                               //Variabeln där vi ska spara värdet vi tar bort.
        if(tom()){                                              //Om listan är tom så returnerar vi null.
            return null;
        }
        else if (antall == 1){                                  //Om antall är 1 så är det bara ett värde. Sätter taBort till att vara hode och sätter sen hode och hale att vara null.
            taBort = hode.verdi;
            hode = hale = null;
        }
        else if(antall == 2){                                   //Om antall är 2 så har vi två alternativ. Antingen är indeks hode eller hale.
            if(indeks == 0){
                taBort = hode.verdi;
                hode = hode.neste;
                hode.forrige = null;
            }
            else{
                taBort = hale.verdi;
                hale = hale.forrige;
                hale.neste = null;
            }
        }
        else{                                                   //Om inte antalet är 0, 1 eller 2 så går vi in här.
            if(indeks == 0){                                    //Om indeks är först i listan
                taBort = hode.verdi;
                hode = hode.neste;
                hode.forrige = null;
            }
            else if(indeks == antall-1){                        //Om indeks är sist i listan
                taBort = hale.verdi;
                hale = hale.forrige;
                hale.neste = null;
            }
            else{                                               //Om indeks ligger mellan två andra värden i listan så tar vi bort mellan dem och ändrar pekarna.
                Node<T> temp;
                Node<T> current = hode;
                for(int i = 1; i < indeks; i++){
                    current = current.neste;
                }
                taBort = current.neste.verdi;
                temp = current.neste;
                current.neste = temp.neste;
                temp.neste.forrige = current;
            }
        }
        endringer++;                                            //Plussar på endringar.
        antall--;                                               //Tar minus på antall då vi har tagit bort från listan.
        return taBort;
    }

    @Override
    public void nullstill(){
        this.hode = null;
        this.hale = null;
        antall = 0;
        endringer++;
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

            /*skrivBak.append(hale.verdi);
            Node<T> current = hale.forrige;*/

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
        //Uppgift 8b
        //Denna ska returnera en instans av iteratorklassen.
        return new DobbeltLenketListeIterator(); //Returnerar en ny DobbeltLenketListeIterator.
    }

    public Iterator<T> iterator(int indeks){
        //Uppgift 8d

        //Denna ska kolla att indeksen är lovlig.
        //Ska sen använda konstruktören i 8c för att returnera en instans av iteratorklassen.

        indeksKontroll(indeks, true); //Kollar index.
        return new DobbeltLenketListeIterator(indeks); //Returnerar ny DobbeltLenketListeIterator med indeks.
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
            //Uppgift 8c

            //Denna ska sätta pekaren denne till den noden som är på den sagda indexen.
            //Resten ska vara som i den färdigkodade konstruktören.

            denne = finnNode(indeks); //Setter denne till att peka på den noden vi har på valt index.
            fjernOK = false; //Samma som konstruktör ovan.
            iteratorendringer = endringer; //Samma som konstruktör ovan.
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            //Uppgift 8a

            //Denna metoden ska kolla om iteratorendringer är lik endringer.
            //Om inte kastas en ConcurrentModificationException.
            //Sen en NoSuchElementException om hasNext() ikke är true.
            //Sen sätts fjernOk() till true. Verdien till denne returneras och flyttas till nästa node.

            if(iteratorendringer != endringer) throw new ConcurrentModificationException("De är inte lika"); //Kollar om iteratorendrringer är lik endringer.
            if(!hasNext()) throw new NoSuchElementException("Det finns ingen nästa."); //Om hasNext() är false så kastas ett undantag.
            fjernOK = true; //Sätter fjernOk() till att vara true.
            T denneVerdi = denne.verdi; //Lagrar värdet till denne.
            denne = denne.neste; //Sätter denne till att vara denne.neste.
            return denneVerdi; //Returnerar denneVerdi.
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
