package com.andrew.associate.projectkamus2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.andrew.associate.projectkamus2.DatabaseContract.DataKamus.DES;
import static com.andrew.associate.projectkamus2.DatabaseContract.DataKamus.KATA;
import static com.andrew.associate.projectkamus2.DatabaseContract.TABEL_INDO_KE_INGGRIS;
import static com.andrew.associate.projectkamus2.DatabaseContract.TABEL_INGGRIS_KE_INDO;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String NAMA_DATABASE = "dbkamus";
    private static final int VERSI_DATABASE = 1;

    private static String BUAT_TABEL_INDO_KE_INGGRIS = "create table " + TABEL_INDO_KE_INGGRIS +
            " ("+ _ID + " integer primary key autoincrement, " +
            KATA + " text not null, " +
            DES + " text not null);";

    private static String BUAT_TABEL_INGGRIS_KE_INDO = "create table " + TABEL_INGGRIS_KE_INDO +
            " (" + _ID + " integer primary key autoincrement, " +
            KATA + " text not null, " +
            DES + " text not null);";

    DatabaseHelper(Context context){
        super(context, NAMA_DATABASE, null, VERSI_DATABASE);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BUAT_TABEL_INDO_KE_INGGRIS);
        db.execSQL(BUAT_TABEL_INGGRIS_KE_INDO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_INDO_KE_INGGRIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_INGGRIS_KE_INDO);
        onCreate(db);
    }
}
