package com.msteber.kelimegezmeceyardim;

import android.app.Dialog;
import android.os.AsyncTask;

import com.msteber.kelimegezmeceyardim.database.DataBaseLayer;
import com.msteber.kelimegezmeceyardim.database.DataBaseOperations;

import java.util.ArrayList;

import static com.msteber.kelimegezmeceyardim.MainActivity.sekizHarfliKelimelerSonuc;
import static com.msteber.kelimegezmeceyardim.MainActivity.altiHarfliKelimelerSonuc;
import static com.msteber.kelimegezmeceyardim.MainActivity.sekizharfCheckbox;
import static com.msteber.kelimegezmeceyardim.MainActivity.yediHarfliKelimelerSonuc;
import static com.msteber.kelimegezmeceyardim.MainActivity.altiharfCheckbox;
import static com.msteber.kelimegezmeceyardim.MainActivity.besHarfliKelimelerSonuc;
import static com.msteber.kelimegezmeceyardim.MainActivity.besharfCheckbox;
import static com.msteber.kelimegezmeceyardim.MainActivity.dortHarfliKelimelerSonuc;
import static com.msteber.kelimegezmeceyardim.MainActivity.dortharfCheckbox;
import static com.msteber.kelimegezmeceyardim.MainActivity.editTextKelime;
import static com.msteber.kelimegezmeceyardim.MainActivity.serviceBaslatma;
import static com.msteber.kelimegezmeceyardim.MainActivity.ucHarfliKelimelerSonuc;
import static com.msteber.kelimegezmeceyardim.MainActivity.ucharfCheckbox;
import static com.msteber.kelimegezmeceyardim.MainActivity.yediharfCheckbox;

public class MyAsyncTaskClass extends AsyncTask<Void, Void, Void> {

    private DataBaseLayer dataBaseLayer;
    private DataBaseOperations dataBaseOperations;
    private ArrayList<String> ucHarfliKelimeler, dortHarfliKelimeler, besHarfliKelimeler ,altiHarfliKelimeler, yediHarfliKelimeler, sekizHarfliKelimeler;
    private ArrayList<String> ucHarfliKombinasyonlar, dortHarfliKombinasyonlar, besHarfliKombinasyonlar, altiHarfliKombinasyonlar, yediHarfliKombinasyonlar;
    private Dialog mDialog;


    private IDegisiklikBildir iDegisiklikBildir;
    public MyAsyncTaskClass(IDegisiklikBildir iDegisiklikBildir) {
        this.iDegisiklikBildir = iDegisiklikBildir;
    }

    @Override
    protected void onPreExecute() {
        serviceBaslatma = false;

        dataBaseLayer = new DataBaseLayer(iDegisiklikBildir.contextVer());
        dataBaseOperations = new DataBaseOperations();

        mDialog = new Dialog(iDegisiklikBildir.contextVer());
        mDialog.setContentView(R.layout.loading_layout);
        mDialog.setCancelable(false);
        mDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        checkBoxKontrolVeVeriIsleme();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        iDegisiklikBildir.goster();
        mDialog.dismiss();
        onCancelled();
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        serviceBaslatma = true;
        this.cancel(true);
        super.onCancelled();
    }

    //Edittext'e girilen kelimenin karakter say??s??na ve se??ilen popupmen?? item'lar??na g??re veritaban??ndan ilgili veriler ??ekilerek
    //bu kelimelerin popupmen??'deki se??ilen harf say??s??na g??re kombinasyonlar?? olu??turulup veritaban??ndan ??ekilen kelimelerle kar????la??t??ma yap??l??yor.
    //Sonu?? olarak e??le??en kelimeler 'sonuc' kelimesiyle sonland??r??lan arraylist'lere aktar??l??yor.
    //doInBackround() methodu i??inde kullan??l??yor. Bu sayede UI kilitlenmeden i??lem arka planda yap??larak tamamlan??yor.
    private void checkBoxKontrolVeVeriIsleme(){
        switch (editTextKelime.length()){
            case 3:

                    ucHarfliKelimeler = dataBaseOperations.ucHarfliSozcukCek(dataBaseLayer);
                    ucHarfliKelimelerSonuc = kelimeEslesmeKontrol(editTextKelime, ucHarfliKelimeler);

                break;
            case 4:

                    if(ucharfCheckbox){
                        ucHarfliKelimeler = dataBaseOperations.ucHarfliSozcukCek(dataBaseLayer);
                        ucHarfliKombinasyonlar = ucHarfKombinasyon(editTextKelime.toCharArray(),4);

                        for(int i = 0; i < ucHarfliKombinasyonlar.size(); i++){
                            if(ucHarfliKelimelerSonuc == null){
                                ucHarfliKelimelerSonuc = kelimeEslesmeKontrol(ucHarfliKombinasyonlar.get(i), ucHarfliKelimeler);
                            }else{
                                ucHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(ucHarfliKombinasyonlar.get(i), ucHarfliKelimeler));
                            }
                        }
                    }
                    if(dortharfCheckbox){
                        dortHarfliKelimeler = dataBaseOperations.dortHarfliSozcukCek(dataBaseLayer);
                        dortHarfliKelimelerSonuc = kelimeEslesmeKontrol(editTextKelime, dortHarfliKelimeler);
                    }

                break;
            case 5:

                    if(ucharfCheckbox){
                        ucHarfliKelimeler = dataBaseOperations.ucHarfliSozcukCek(dataBaseLayer);
                        ucHarfliKombinasyonlar = ucHarfKombinasyon(editTextKelime.toCharArray(),5);

                        for(int i = 0; i < ucHarfliKombinasyonlar.size(); i++){
                            if(ucHarfliKelimelerSonuc == null){
                                ucHarfliKelimelerSonuc = kelimeEslesmeKontrol(ucHarfliKombinasyonlar.get(i), ucHarfliKelimeler);
                            }else{
                                ucHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(ucHarfliKombinasyonlar.get(i), ucHarfliKelimeler));
                            }
                        }
                    }
                    if(dortharfCheckbox){
                        dortHarfliKelimeler = dataBaseOperations.dortHarfliSozcukCek(dataBaseLayer);
                        dortHarfliKombinasyonlar = dortHarfKombinasyon(editTextKelime.toCharArray(),5);

                        for(int i = 0; i < dortHarfliKombinasyonlar.size(); i++){
                            if(dortHarfliKelimelerSonuc == null){
                                dortHarfliKelimelerSonuc = kelimeEslesmeKontrol(dortHarfliKombinasyonlar.get(i), dortHarfliKelimeler);
                            }else{
                                dortHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(dortHarfliKombinasyonlar.get(i), dortHarfliKelimeler));
                            }
                        }
                    }
                    if(besharfCheckbox){
                        besHarfliKelimeler = dataBaseOperations.besHarfliSozcukCek(dataBaseLayer);
                        besHarfliKelimelerSonuc = kelimeEslesmeKontrol(editTextKelime, besHarfliKelimeler);
                    }

                break;
            case 6:

                    if(ucharfCheckbox){
                        ucHarfliKelimeler = dataBaseOperations.ucHarfliSozcukCek(dataBaseLayer);
                        ucHarfliKombinasyonlar = ucHarfKombinasyon(editTextKelime.toCharArray(),6);

                        for(int i = 0; i < ucHarfliKombinasyonlar.size(); i++){
                            if(ucHarfliKelimelerSonuc == null){
                                ucHarfliKelimelerSonuc = kelimeEslesmeKontrol(ucHarfliKombinasyonlar.get(i), ucHarfliKelimeler);
                            }else{
                                ucHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(ucHarfliKombinasyonlar.get(i), ucHarfliKelimeler));
                            }
                        }
                    }
                    if(dortharfCheckbox){
                        dortHarfliKelimeler = dataBaseOperations.dortHarfliSozcukCek(dataBaseLayer);
                        dortHarfliKombinasyonlar = dortHarfKombinasyon(editTextKelime.toCharArray(),6);

                        for(int i = 0; i < dortHarfliKombinasyonlar.size(); i++){
                            if(dortHarfliKelimelerSonuc == null){
                                dortHarfliKelimelerSonuc = kelimeEslesmeKontrol(dortHarfliKombinasyonlar.get(i), dortHarfliKelimeler);
                            }else{
                                dortHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(dortHarfliKombinasyonlar.get(i), dortHarfliKelimeler));
                            }
                        }
                    }
                    if(besharfCheckbox){
                        besHarfliKelimeler = dataBaseOperations.besHarfliSozcukCek(dataBaseLayer);
                        besHarfliKombinasyonlar = besHarfKombinasyon(editTextKelime.toCharArray(),6);

                        for(int i = 0; i < besHarfliKombinasyonlar.size(); i++){
                            if(besHarfliKelimelerSonuc == null){
                                besHarfliKelimelerSonuc = kelimeEslesmeKontrol(besHarfliKombinasyonlar.get(i), besHarfliKelimeler);
                            }else{
                                besHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(besHarfliKombinasyonlar.get(i), besHarfliKelimeler));
                            }
                        }
                    }
                    if(altiharfCheckbox){
                        altiHarfliKelimeler = dataBaseOperations.altiHarfliSozcukCek(dataBaseLayer);
                        altiHarfliKelimelerSonuc = kelimeEslesmeKontrol(editTextKelime, altiHarfliKelimeler);
                    }

                break;
            case 7:

                    if(ucharfCheckbox){
                        ucHarfliKelimeler = dataBaseOperations.ucHarfliSozcukCek(dataBaseLayer);
                        ucHarfliKombinasyonlar = ucHarfKombinasyon(editTextKelime.toCharArray(),7);

                        for(int i = 0; i < ucHarfliKombinasyonlar.size(); i++){
                            if(ucHarfliKelimelerSonuc == null){
                                ucHarfliKelimelerSonuc = kelimeEslesmeKontrol(ucHarfliKombinasyonlar.get(i), ucHarfliKelimeler);
                            }else{
                                ucHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(ucHarfliKombinasyonlar.get(i), ucHarfliKelimeler));
                            }
                        }
                    }
                    if(dortharfCheckbox){
                        dortHarfliKelimeler = dataBaseOperations.dortHarfliSozcukCek(dataBaseLayer);
                        dortHarfliKombinasyonlar = dortHarfKombinasyon(editTextKelime.toCharArray(),7);

                        for(int i = 0; i < dortHarfliKombinasyonlar.size(); i++){
                            if(dortHarfliKelimelerSonuc == null){
                                dortHarfliKelimelerSonuc = kelimeEslesmeKontrol(dortHarfliKombinasyonlar.get(i), dortHarfliKelimeler);
                            }else{
                                dortHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(dortHarfliKombinasyonlar.get(i), dortHarfliKelimeler));
                            }
                        }
                    }
                    if(besharfCheckbox){
                        besHarfliKelimeler = dataBaseOperations.besHarfliSozcukCek(dataBaseLayer);
                        besHarfliKombinasyonlar = besHarfKombinasyon(editTextKelime.toCharArray(),7);

                        for(int i = 0; i < besHarfliKombinasyonlar.size(); i++){
                            if(besHarfliKelimelerSonuc == null){
                                besHarfliKelimelerSonuc = kelimeEslesmeKontrol(besHarfliKombinasyonlar.get(i), besHarfliKelimeler);
                            }else{
                                besHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(besHarfliKombinasyonlar.get(i), besHarfliKelimeler));
                            }
                        }
                    }
                    if(altiharfCheckbox){
                        altiHarfliKelimeler = dataBaseOperations.altiHarfliSozcukCek(dataBaseLayer);
                        altiHarfliKombinasyonlar = altiHarfKombinasyon(editTextKelime.toCharArray(),7);

                        for(int i = 0; i < altiHarfliKombinasyonlar.size(); i++){
                            if(altiHarfliKelimelerSonuc == null){
                                altiHarfliKelimelerSonuc = kelimeEslesmeKontrol(altiHarfliKombinasyonlar.get(i), altiHarfliKelimeler);
                            }else{
                                altiHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(altiHarfliKombinasyonlar.get(i), altiHarfliKelimeler));
                            }
                        }
                    }

                    if(yediharfCheckbox){
                        yediHarfliKelimeler = dataBaseOperations.yediHarfliSozcukCek(dataBaseLayer);
                        yediHarfliKelimelerSonuc = kelimeEslesmeKontrol(editTextKelime, yediHarfliKelimeler);
                    }

                break;
            case 8:

                    if(ucharfCheckbox){
                        ucHarfliKelimeler = dataBaseOperations.ucHarfliSozcukCek(dataBaseLayer);
                        ucHarfliKombinasyonlar = ucHarfKombinasyon(editTextKelime.toCharArray(),8);

                        for(int i = 0; i < ucHarfliKombinasyonlar.size(); i++){
                            if(ucHarfliKelimelerSonuc == null){
                                ucHarfliKelimelerSonuc = kelimeEslesmeKontrol(ucHarfliKombinasyonlar.get(i), ucHarfliKelimeler);
                            }else{
                                ucHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(ucHarfliKombinasyonlar.get(i), ucHarfliKelimeler));
                            }
                        }
                    }
                    if(dortharfCheckbox){
                        dortHarfliKelimeler = dataBaseOperations.dortHarfliSozcukCek(dataBaseLayer);
                        dortHarfliKombinasyonlar = dortHarfKombinasyon(editTextKelime.toCharArray(),8);

                        for(int i = 0; i < dortHarfliKombinasyonlar.size(); i++){
                            if(dortHarfliKelimelerSonuc == null){
                                dortHarfliKelimelerSonuc = kelimeEslesmeKontrol(dortHarfliKombinasyonlar.get(i), dortHarfliKelimeler);
                            }else{
                                dortHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(dortHarfliKombinasyonlar.get(i), dortHarfliKelimeler));
                            }
                        }
                    }
                    if(besharfCheckbox){
                        besHarfliKelimeler = dataBaseOperations.besHarfliSozcukCek(dataBaseLayer);
                        besHarfliKombinasyonlar = besHarfKombinasyon(editTextKelime.toCharArray(),8);

                        for(int i = 0; i < besHarfliKombinasyonlar.size(); i++){
                            if(besHarfliKelimelerSonuc == null){
                                besHarfliKelimelerSonuc = kelimeEslesmeKontrol(besHarfliKombinasyonlar.get(i), besHarfliKelimeler);
                            }else{
                                besHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(besHarfliKombinasyonlar.get(i), besHarfliKelimeler));
                            }
                        }
                    }
                    if(altiharfCheckbox){
                        altiHarfliKelimeler = dataBaseOperations.altiHarfliSozcukCek(dataBaseLayer);
                        altiHarfliKombinasyonlar = altiHarfKombinasyon(editTextKelime.toCharArray(),8);

                        for(int i = 0; i < altiHarfliKombinasyonlar.size(); i++){
                            if(altiHarfliKelimelerSonuc == null){
                                altiHarfliKelimelerSonuc = kelimeEslesmeKontrol(altiHarfliKombinasyonlar.get(i), altiHarfliKelimeler);
                            }else{
                                altiHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(altiHarfliKombinasyonlar.get(i), altiHarfliKelimeler));
                            }
                        }
                    }
                    if(yediharfCheckbox){
                        yediHarfliKelimeler = dataBaseOperations.yediHarfliSozcukCek(dataBaseLayer);
                        yediHarfliKombinasyonlar = yediHarfKombinasyon(editTextKelime.toCharArray(),8);

                        for(int i = 0; i < yediHarfliKombinasyonlar.size(); i++){
                            if(yediHarfliKelimelerSonuc == null){
                                yediHarfliKelimelerSonuc = kelimeEslesmeKontrol(yediHarfliKombinasyonlar.get(i), yediHarfliKelimeler);
                            }else{
                                yediHarfliKelimelerSonuc.addAll(kelimeEslesmeKontrol(yediHarfliKombinasyonlar.get(i), yediHarfliKelimeler));
                            }
                        }
                    }

                    if(sekizharfCheckbox){
                        sekizHarfliKelimeler = dataBaseOperations.sekizHarfliSozcukCek(dataBaseLayer);
                        sekizHarfliKelimelerSonuc = kelimeEslesmeKontrol(editTextKelime, sekizHarfliKelimeler);
                    }

                break;
        }
    }

    //KOMB??NASYON METHODLARI
    //Edittext'e girilen kelimeyi olu??turulan harfler popupmen??'de se??ilen harf say??s??na g??re kombine edilip ilgili arraylist'e aktar??l??yor.
    //checkBoxKontrolVeVeriIsleme() methodu i??inde kullan??l??yor.
    private ArrayList<String> ucHarfKombinasyon(char[] gelenKelime, int harfSayisi){
        ArrayList<String> ucHarfKomList = new ArrayList<>();

        for(int a = 0 ; a < harfSayisi - 2 ; a++){
            for(int b = a+1 ; b < harfSayisi - 1 ; b++){
                for (int c = b+1 ; c < harfSayisi ; c++){
                    ucHarfKomList.add((String.valueOf(gelenKelime[a]) + String.valueOf(gelenKelime[b]) + String.valueOf(gelenKelime[c])));
                }
            }
        }
        ayniKelimeAyiklama(ucHarfKomList);
        return ucHarfKomList;
    }

    private ArrayList<String> dortHarfKombinasyon(char[] gelenKelime, int harfSayisi){
        ArrayList<String> dortHarfKomList = new ArrayList<>();

        for(int a = 0 ; a < harfSayisi - 3 ; a++){
            for(int b = a+1 ; b < harfSayisi - 2 ; b++){
                for (int c = b+1 ; c < harfSayisi - 1 ; c++){
                    for(int d = c+1 ; d < harfSayisi ; d++){
                        dortHarfKomList.add((String.valueOf(gelenKelime[a]) + String.valueOf(gelenKelime[b]) + String.valueOf(gelenKelime[c]) + String.valueOf(gelenKelime[d])));
                    }
                }
            }
        }
        ayniKelimeAyiklama(dortHarfKomList);
        return dortHarfKomList;
    }

    private ArrayList<String> besHarfKombinasyon(char[] gelenKelime, int harfSayisi){
        ArrayList<String> besHarfKomList = new ArrayList<>();

        for(int a = 0 ; a < harfSayisi - 4 ; a++){
            for(int b = a+1 ; b < harfSayisi - 3 ; b++){
                for (int c = b+1 ; c < harfSayisi - 2 ; c++){
                    for(int d = c+1 ; d < harfSayisi - 1 ; d++){
                        for(int e = d+1 ; e < harfSayisi ; e++){
                            besHarfKomList.add((String.valueOf(gelenKelime[a]) + String.valueOf(gelenKelime[b]) + String.valueOf(gelenKelime[c]) + String.valueOf(gelenKelime[d]) + String.valueOf(gelenKelime[e])));
                        }
                    }
                }
            }
        }
        ayniKelimeAyiklama(besHarfKomList);
        return besHarfKomList;
    }

    private ArrayList<String> altiHarfKombinasyon(char[] gelenKelime, int harfSayisi){
        ArrayList<String> altiHarfKomList = new ArrayList<>();

        for(int a = 0 ; a < harfSayisi - 5 ; a++){
            for(int b = a+1 ; b < harfSayisi - 4 ; b++){
                for (int c = b+1 ; c < harfSayisi - 3 ; c++){
                    for(int d = c+1 ; d < harfSayisi - 2 ; d++){
                        for(int e = d+1 ; e < harfSayisi - 1 ; e++){
                            for(int f = e+1 ; f < harfSayisi ; f++){
                                altiHarfKomList.add((String.valueOf(gelenKelime[a]) + String.valueOf(gelenKelime[b]) + String.valueOf(gelenKelime[c]) + String.valueOf(gelenKelime[d]) + String.valueOf(gelenKelime[e]) + String.valueOf(gelenKelime[f])));
                            }
                        }
                    }
                }
            }
        }
        ayniKelimeAyiklama(altiHarfKomList);
        return altiHarfKomList;
    }

    private ArrayList<String> yediHarfKombinasyon(char[] gelenKelime, int harfSayisi){
        ArrayList<String> yediHarfKomList = new ArrayList<>();

        for(int a = 0 ; a < harfSayisi - 6 ; a++){
            for(int b = a+1 ; b < harfSayisi - 5 ; b++){
                for (int c = b+1 ; c < harfSayisi - 4 ; c++){
                    for(int d = c+1 ; d < harfSayisi - 3 ; d++){
                        for(int e = d+1 ; e < harfSayisi - 2 ; e++){
                            for(int f = e+1 ; f < harfSayisi - 1 ; f++){
                                for(int g = f+1 ; g < harfSayisi ; g++){
                                    yediHarfKomList.add((String.valueOf(gelenKelime[a]) + String.valueOf(gelenKelime[b]) + String.valueOf(gelenKelime[c]) + String.valueOf(gelenKelime[d]) + String.valueOf(gelenKelime[e]) + String.valueOf(gelenKelime[f]) + String.valueOf(gelenKelime[g])));
                                }
                            }
                        }
                    }
                }
            }
        }
        ayniKelimeAyiklama(yediHarfKomList);
        return yediHarfKomList;
    }

    /*private void ayniKelimeAyiklama(ArrayList<String> ayiklanacakArrayList) {
        for (int i = 0; i < ayiklanacakArrayList.size(); i++) {
            for (int j = i + 1; j < ayiklanacakArrayList.size(); ) {
                if (ayiklanacakArrayList.get(i).equals(ayiklanacakArrayList.get(j))) {
                    ayiklanacakArrayList.remove(j);
                } else {
                    j++;
                }
            }
        }
    }*/

    private void ayniKelimeAyiklama(ArrayList<String> ayiklanacakArrayList) {
        for (int i = 0; i < ayiklanacakArrayList.size(); i++) {
            for (int j = i + 1; j < ayiklanacakArrayList.size(); ) {
                if (kelimeEslesmeGecisIzni(ayiklanacakArrayList.get(i),ayiklanacakArrayList.get(j))) {
                    ayiklanacakArrayList.remove(j);
                } else {
                    j++;
                }
            }
        }
    }


    //Bu methoda parametre olarak verilen kelimeyi veritaban??ndan ??ekilen ilgili kelimelerle kar????la??t??r??p sonucunda e??le??en kelimeleri arraylist'e ekliyor.
    //checkBoxKontrolVeVeriIsleme() methodu i??inde kullan??l??yor.
    private ArrayList<String> kelimeEslesmeKontrol(String girilenKelime, ArrayList<String> veriTabaniKelimeleri){
        ArrayList<String> eslesmeSonuc = new ArrayList<>();

        for(int i = 0; i < veriTabaniKelimeleri.size(); i++){

            if(kelimeEslesmeGecisIzni(girilenKelime,veriTabaniKelimeleri.get(i))){
                    eslesmeSonuc.add(veriTabaniKelimeleri.get(i));
            }
        }

        return eslesmeSonuc;
    }

    //Parametre olarak verilen kelimelerin hangi harften ka?? tane sahip oldu??una kelimelerS??n??f?? yard??m??yla bak??p bunun sonucunda harf say??lar??n??
    //kar????la??t??rarak kelimelerin e??le??ip e??le??mediklerini kontrol ediyor. Sonu??ta e??le??iyorlarsa true, e??le??miyorlarsa false de??er d??nd??rerek
    //kelimelerin kelimeEslesmeKontrol() methodu i??inde arrayliste kay??t olup olmama durumunu belirliyor.
    private boolean kelimeEslesmeGecisIzni(String kelime1, String kelime2){
        boolean gecisIzni = false;
        char[] icKelime1 = kelime1.toCharArray();
        char[] icKelime2 = kelime2.toCharArray();
        kelimelerSinifi kelimeObje1 = new kelimelerSinifi();
        kelimelerSinifi kelimeObje2 = new kelimelerSinifi();

        kelimeObje1.setHarfler();
        kelimeObje2.setHarfler();

        for (char c : icKelime1) {
            kelimeObje1.arttir(c);
        }

        for (char c : icKelime2) {
            kelimeObje2.arttir(c);
        }

        switch (kelime1.length()){
            case 3:
                if(kelimeObje1.harfler.get(kelimeObje1.intexler.get(0)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(0)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(1)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(1)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(2)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(2))){
                    gecisIzni = true;
                }
                break;
            case 4:
                if(kelimeObje1.harfler.get(kelimeObje1.intexler.get(0)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(0)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(1)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(1)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(2)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(2)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(3)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(3))){
                    gecisIzni = true;
                }
                break;
            case 5:
                if(kelimeObje1.harfler.get(kelimeObje1.intexler.get(0)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(0)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(1)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(1)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(2)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(2)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(3)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(3)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(4)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(4))){
                    gecisIzni = true;
                }
                break;
            case 6:
                if(kelimeObje1.harfler.get(kelimeObje1.intexler.get(0)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(0)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(1)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(1)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(2)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(2)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(3)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(3)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(4)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(4)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(5)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(5))){
                    gecisIzni = true;
                }
                break;
            case 7:
                if(kelimeObje1.harfler.get(kelimeObje1.intexler.get(0)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(0)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(1)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(1)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(2)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(2)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(3)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(3)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(4)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(4)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(5)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(5)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(6)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(6))){
                    gecisIzni = true;
                }
                break;
            case 8:
                if(kelimeObje1.harfler.get(kelimeObje1.intexler.get(0)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(0)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(1)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(1)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(2)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(2)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(3)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(3)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(4)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(4)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(5)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(5)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(6)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(6)) && kelimeObje1.harfler.get(kelimeObje1.intexler.get(7)) == kelimeObje2.harfler.get(kelimeObje1.intexler.get(7))){
                    gecisIzni = true;
                }
                break;
        }

        return gecisIzni;
    }

}


