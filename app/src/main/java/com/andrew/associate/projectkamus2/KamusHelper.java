package com.andrew.associate.projectkamus2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.andrew.associate.projectkamus2.DatabaseContract.DataKamus.DES;
import static com.andrew.associate.projectkamus2.DatabaseContract.DataKamus.KATA;
import static com.andrew.associate.projectkamus2.DatabaseContract.TABEL_INDO_KE_INGGRIS;
import static com.andrew.associate.projectkamus2.DatabaseContract.TABEL_INGGRIS_KE_INDO;

public class KamusHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public KamusHelper(Context context){
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<KamusModel> getDataDariNama(String cari, boolean apakahInggris){

        String result = "";
        KamusModel kamusModel;
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        Cursor cursor = pilihDariKata(cari, apakahInggris);

        cursor.moveToFirst();

        if(cursor.getCount()>0){
            do{
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                kamusModel.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DES)));
                arrayList.add(kamusModel);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public ArrayList<KamusModel> getSemuaData(boolean apakahInggris){

        Cursor cursor = pilihSemuaData(apakahInggris);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;

        if(cursor.getCount()>0){
            do{
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                kamusModel.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DES)));
                arrayList.add(kamusModel);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public Cursor pilihSemuaData(boolean apakahInggris){
        String NAMA_TABEL_DB = apakahInggris ? TABEL_INDO_KE_INGGRIS : TABEL_INGGRIS_KE_INDO;
        return database.rawQuery(String.format("SELECT * FROM %s ORDER BY %s ASC", NAMA_TABEL_DB, KATA),
                null);
    }

    public Cursor pilihDariKata(String query, boolean apakahInggris){
        String NAMA_TABEL_DB = apakahInggris ? TABEL_INGGRIS_KE_INDO : TABEL_INDO_KE_INGGRIS;
        return database.rawQuery("SELECT * FROM " + NAMA_TABEL_DB + " WHERE " + KATA + " LIKE '%'" +query.trim() + "%'", null);
    }

    public long insert(KamusModel kamusModel, boolean apakahInggris){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KATA, kamusModel.getKata());
        initialValues.put(DES, kamusModel.getDeskripsi());
        String NAMA_TABEL_DB = apakahInggris ? TABEL_INDO_KE_INGGRIS : TABEL_INGGRIS_KE_INDO;
        return database.insert(NAMA_TABEL_DB, null, initialValues);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(KamusModel kamusModel, boolean apakahInggris){
        String NAMA_TABEL_DB = apakahInggris ? TABEL_INDO_KE_INGGRIS : TABEL_INGGRIS_KE_INDO;

        String sql = "INSERT INTO "+NAMA_TABEL_DB+" ("+KATA+", "+DES
                +") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusModel.getKata());
        stmt.bindString(2, kamusModel.getDeskripsi());
        stmt.execute();
        stmt.clearBindings();
    }

    public int update(KamusModel kamusModel, boolean apakahInggris){
        String NAMA_TABEL_DB = apakahInggris ? TABEL_INDO_KE_INGGRIS : TABEL_INGGRIS_KE_INDO;

        ContentValues args = new ContentValues();
        args.put(KATA, kamusModel.getKata());
        args.put(DES, kamusModel.getDeskripsi());
        return database.update(NAMA_TABEL_DB, args, _ID + "= '" + kamusModel.getId() + "'", null);
    }

    public int delete (int id, boolean apakahInggris){
        String NAMA_TABEL_DB = apakahInggris ? TABEL_INDO_KE_INGGRIS : TABEL_INGGRIS_KE_INDO;

        return database.delete(NAMA_TABEL_DB, _ID + " = '" +id+"'", null);
    }
}
