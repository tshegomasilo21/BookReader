package com.touchsides.bookreaderapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FrequentWordContract.View{

    FrequentWordPresenter presenter;
    FrequentWordContract.View mView;
    InputStream inputStream;
    Spinner filenameSpinner;
    Button btnAnalyze;
    TextView txtResults;
    String filename;
    String results;
    ArrayList<String> wordList = new ArrayList<>();

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = this;
        setFields();


    }

    public void setFields()
    {
        filenameSpinner = (Spinner) findViewById(R.id.selectBookSpinner);
        btnAnalyze = (Button) findViewById(R.id.btnAnalyze);
        txtResults = (TextView) findViewById(R.id.txtResults);
        results = "";

        List<String> list = new ArrayList<>();
        list.add(0,"Select Book");
        list.add("2600-0.txt");
        list.add("wrnpc10.txt");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, list);
        filenameSpinner.setAdapter(adapter);

        btnAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (filenameSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(),"Please select book",Toast.LENGTH_LONG).show();
                    return;
                }
                filename = filenameSpinner.getSelectedItem().toString();

                try {

                    inputStream = getResources().getAssets().open(filename);
                    presenter = new FrequentWordPresenter(mView,inputStream,getApplicationContext());
                    presenter.getWords();



                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    public void onMostFrequentWord(String word, int numOccurred) {

        results = "Most Frequent Word : "+ word + "\nNumbers occured : " + numOccurred +"\n\n";

    }

    @Override
    public void onMostFrequent7CharWord(String word, int numOccurred) {

        results = results + "Most Frequent 7 character Word : "+ word + "\nNumbers occured : " + numOccurred +"\n\n";

    }

    @Override
    public void onWordsFound(ArrayList<String> words) {



        presenter.getMostFrequentWord(words);
        presenter.getMostFrequent7CharWord(words);
        presenter.getScrabbleHighestScoringWords(words);

    }

    @Override
    public void onScrabbleHighestScoringWords(String word, int numScored) {


        results = results + "Scrabble Highest Scoring Word : "+ word + "\nScore : " + numScored +"\n";

        txtResults.setVisibility(View.VISIBLE);
        txtResults.setText(results);

    }

    @Override
    public void onError(String message) {

    }

}
