package com.andrew.associate.projectkamus2;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {

        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(MainActivity.this);
            appPreference = new AppPreference(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Boolean firstRun = appPreference.getFirstRun();

            if (firstRun) {

                ArrayList<KamusModel> kamusModels = preLoadRaw();

                kamusHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / kamusModels.size();

                for (KamusModel model : kamusModels) {
                    kamusHelper.insert(model);
                    progress += progressDiff;
                    publishProgress((int) progress);
                }

                kamusHelper.close();

                appPreference.setFirstRun(false);

                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result){
            Intent i = new Intent(MainActivity.this, KamusActivity.class);
            startActivity(i);
            finish();
        }

        public ArrayList<KamusModel> preLoadRaw(){
            ArrayList<KamusModel> kamusModels = new ArrayList<>();
            String line = null;
            BufferedReader reader;
            try{
                Resources res = getResources();
                InputStream raw_dict = res.openRawResource(R.raw.english_indonesia);

                reader = new BufferedReader(new InputStreamReader(raw_dict));
                int count = 0;
                do{
                    line = reader.readLine();
                    String[] splitstr = line.split("\t");

                    KamusModel kamusModel;

                    kamusModel = new KamusModel(splitstr[0], splitstr[1]);
                    kamusModels.add(kamusModel);
                    count++;
                }while (line != null);
            } catch (Exception e){
                e.printStackTrace();
            }
            return kamusModels;
        }
    }
}