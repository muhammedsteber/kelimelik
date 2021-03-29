package com.msteber.kelimegezmeceyardim;

import java.util.ArrayList;

public class kelimelerSinifi {

    public kelimelerSinifi(){

    }
    public ArrayList<Integer> harfler = new ArrayList<Integer>();
    public ArrayList<Integer> intexler = new ArrayList<Integer>();

    public void setHarfler(){
        for(int i = 0; i < 29; i++){
           this.harfler.add(0);
        }
    }


    public void arttir(char chr){
        switch (chr){
            case 'a': harfler.set(0,harfler.get(0)+1); intexler.add(0);
                break;
            case 'b': harfler.set(1,harfler.get(1)+1); intexler.add(1);
                break;
            case 'c': harfler.set(2,harfler.get(2)+1); intexler.add(2);
                break;
            case 'ç': harfler.set(3,harfler.get(3)+1); intexler.add(3);
                break;
            case 'd': harfler.set(4,harfler.get(4)+1); intexler.add(4);
                break;
            case 'e': harfler.set(5,harfler.get(5)+1); intexler.add(5);
                break;
            case 'f': harfler.set(6,harfler.get(6)+1); intexler.add(6);
                break;
            case 'g': harfler.set(7,harfler.get(7)+1); intexler.add(7);
                break;
            case 'ğ': harfler.set(8,harfler.get(8)+1); intexler.add(8);
                break;
            case 'h': harfler.set(9,harfler.get(9)+1); intexler.add(9);
                break;
            case 'ı': harfler.set(10,harfler.get(10)+1); intexler.add(10);
                break;
            case 'i': harfler.set(11,harfler.get(11)+1); intexler.add(11);
                break;
            case 'j': harfler.set(12,harfler.get(12)+1); intexler.add(12);
                break;
            case 'k': harfler.set(13,harfler.get(13)+1); intexler.add(13);
                break;
            case 'l': harfler.set(14,harfler.get(14)+1); intexler.add(14);
                break;
            case 'm': harfler.set(15,harfler.get(15)+1); intexler.add(15);
                break;
            case 'n': harfler.set(16,harfler.get(16)+1); intexler.add(16);
                break;
            case 'o': harfler.set(17,harfler.get(17)+1); intexler.add(17);
                break;
            case 'ö': harfler.set(18,harfler.get(18)+1); intexler.add(18);
                break;
            case 'p': harfler.set(19,harfler.get(19)+1); intexler.add(19);
                break;
            case 'r': harfler.set(20,harfler.get(20)+1); intexler.add(20);
                break;
            case 's': harfler.set(21,harfler.get(21)+1); intexler.add(21);
                break;
            case 'ş': harfler.set(22,harfler.get(22)+1); intexler.add(22);
                break;
            case 't': harfler.set(23,harfler.get(23)+1); intexler.add(23);
                break;
            case 'u': harfler.set(24,harfler.get(24)+1); intexler.add(24);
                break;
            case 'ü': harfler.set(25,harfler.get(25)+1); intexler.add(25);
                break;
            case 'v': harfler.set(26,harfler.get(26)+1); intexler.add(26);
                break;
            case 'y': harfler.set(27,harfler.get(27)+1); intexler.add(27);
                break;
            case 'z': harfler.set(28,harfler.get(28)+1); intexler.add(28);
                break;
        }
    }

}
