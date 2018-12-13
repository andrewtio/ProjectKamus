package com.andrew.associate.projectkamus2;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABEL_INDO_KE_INGGRIS = "tabelIndoKeInggris";
    static String TABEL_INGGRIS_KE_INDO = "tabelInggrisKeIndo";

    static final class DataKamus implements BaseColumns {
        // Data kata
        static String KATA = "kata";
        // Data Deskripsi
        static String DES = "deskripsi";
    }

}
