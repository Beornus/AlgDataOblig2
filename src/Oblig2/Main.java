package Oblig2;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Liste<String> liste = new DobbeltLenketListe<>();

        for(String e : liste){
            System.out.println(e + " ");
        }
    }
}
