package com.msteber.kelimegezmeceyardim.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DataBaseOperations {

    public ArrayList<String> ucHarfliSozcukCek(DataBaseLayer dataBaseLayer){
        ArrayList<String> ucHarfliSozcukler = new ArrayList<>();
        SQLiteDatabase db = dataBaseLayer.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM ucHarfliKelimeler",null);
        c.moveToFirst();

        while (c.moveToNext()){
            ucHarfliSozcukler.add(c.getString(c.getColumnIndex("kelime")));
        }

        c.close();
        db.close();

        return ucHarfliSozcukler;
    }

    public ArrayList<String> dortHarfliSozcukCek(DataBaseLayer dataBaseLayer){
        ArrayList<String> dortHarfliSozcukler = new ArrayList<>();
        SQLiteDatabase db = dataBaseLayer.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM dortHarfliKelimeler",null);
        c.moveToFirst();

        while (c.moveToNext()){
            dortHarfliSozcukler.add(c.getString(c.getColumnIndex("kelime")));
        }

        c.close();
        db.close();

        return dortHarfliSozcukler;
    }

    public ArrayList<String> besHarfliSozcukCek(DataBaseLayer dataBaseLayer){
        ArrayList<String> besHarfliSozcukler = new ArrayList<>();
        SQLiteDatabase db = dataBaseLayer.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM besHarfliKelimeler",null);
        c.moveToFirst();

        while (c.moveToNext()){
            besHarfliSozcukler.add(c.getString(c.getColumnIndex("kelime")));
        }

        c.close();
        db.close();

        return besHarfliSozcukler;
    }

    public ArrayList<String> altiHarfliSozcukCek(DataBaseLayer dataBaseLayer){
        ArrayList<String> altiHarfliSozcukler = new ArrayList<>();
        SQLiteDatabase db = dataBaseLayer.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM altiHarfliKelimeler",null);
        c.moveToFirst();

        while (c.moveToNext()){
            altiHarfliSozcukler.add(c.getString(c.getColumnIndex("kelime")));
        }

        c.close();
        db.close();

        return altiHarfliSozcukler;
    }

    public ArrayList<String> yediHarfliSozcukCek(DataBaseLayer dataBaseLayer){
        ArrayList<String> yediHarfliSozcukler = new ArrayList<>();
        SQLiteDatabase db = dataBaseLayer.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM yediHarfliKelimeler",null);
        c.moveToFirst();

        while (c.moveToNext()){
            yediHarfliSozcukler.add(c.getString(c.getColumnIndex("kelime")));
        }

        c.close();
        db.close();

        return yediHarfliSozcukler;
    }

    public ArrayList<String> sekizHarfliSozcukCek(DataBaseLayer dataBaseLayer){
        ArrayList<String> sekizHarfliSozcukler = new ArrayList<>();
        SQLiteDatabase db = dataBaseLayer.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM sekizHarfliKelimeler",null);
        c.moveToFirst();

        while (c.moveToNext()){
            sekizHarfliSozcukler.add(c.getString(c.getColumnIndex("kelime")));
        }

        c.close();
        db.close();

        return sekizHarfliSozcukler;
    }
}
