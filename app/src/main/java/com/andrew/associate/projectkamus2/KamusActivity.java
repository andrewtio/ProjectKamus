package com.andrew.associate.projectkamus2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class KamusActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    KamusAdapter kamusAdapter;
    KamusHelper kamusHelper;

    private boolean apakahInggris = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamus);

        recyclerView = findViewById(R.id.recyclerview);

        kamusHelper = new KamusHelper(this);
        kamusAdapter = new KamusAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(kamusAdapter);

        kamusHelper.open();

        // Ambil semua data kamus di database
        ArrayList<KamusModel> kamusModels = (ArrayList<KamusModel>) kamusHelper.pilihSemuaData(apakahInggris);
        kamusHelper.close();
        kamusAdapter.addItem(kamusModels);
    }
}
