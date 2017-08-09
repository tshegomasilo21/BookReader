package com.touchsides.bookreaderapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Tshego on 2017/08/08.
 */

public class FrequentWordPresenter implements FrequentWordContract.Presenter {

    FrequentWordContract.View view;
    InputStream inputStream;
    ArrayList<String> wordList;
    Context ctx;
    public FrequentWordPresenter(FrequentWordContract.View view, InputStream inputStream,Context ctx) {

        this.view = view;
        this.inputStream = inputStream;
        wordList = new ArrayList<>();
        this.ctx = ctx;
    }

    @Override
    public void getMostFrequentWord(ArrayList<String> words) {

        String FrequentWord;
        int FrequentWordCount;

        HashMap<String, Integer> occurrence = new HashMap<String, Integer>();

        FrequentWord = words.get(0);
        FrequentWordCount = 1;

        for(String word : words)
        {
            if(occurrence.containsKey(word))
            {
                occurrence.put(word, occurrence.get(word)+1);

                if(occurrence.get(word)+1 > FrequentWordCount)
                {
                    FrequentWord = new String(word);
                    FrequentWordCount = occurrence.get(word)+1;
                }

            }
            else {
                occurrence.put(word,1);
            }
        }

        view.onMostFrequentWord(FrequentWord,FrequentWordCount);

        Log.v("TAG","MostFrequentWord : " + FrequentWord +"  number occurred : " +FrequentWordCount);


    }

    @Override
    public void getMostFrequent7CharWord(ArrayList<String> words) {


        String FrequentWord;
        int FrequentWordCount;
        ArrayList<String> sevenCharWordList = new ArrayList<>();

        HashMap<String, Integer> occurrence = new HashMap<String, Integer>();



        for(String word : words)
        {
            if(word.length() == 7)
            {
                sevenCharWordList.add(word);
            }

        }

        FrequentWord = sevenCharWordList.get(0);
        FrequentWordCount = 1;

        for(String sevenCharWord : sevenCharWordList)
        {
            if(occurrence.containsKey(sevenCharWord))
            {
                occurrence.put(sevenCharWord, occurrence.get(sevenCharWord)+1);

                if(occurrence.get(sevenCharWord)+1 > FrequentWordCount)
                {
                    FrequentWord = new String(sevenCharWord);
                    FrequentWordCount = occurrence.get(sevenCharWord)+1;
                }

            }
            else {
                occurrence.put(sevenCharWord,1);
            }
        }

        view.onMostFrequent7CharWord(FrequentWord,FrequentWordCount);

        Log.v("TAG","Most Frequent 7 CharWord : " + FrequentWord +"  number occurred : " +FrequentWordCount);

    }

    @Override
    public void getWords() {


        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            HashMap<String, Integer> occurrence = new HashMap<String, Integer>();

            String line;

            while((line = br.readLine()) != null)
            {
                //String [] tokens = line.split("\\s+");
                String [] tokens = line.split("[^a-zA-Z0-9']");

                for(String word : tokens)
                {
                    String newWord = word.trim();
                    if(newWord != null && !newWord.isEmpty())
                    {
                        wordList.add(newWord);
                    }

                }
            }

            br.close();
            view.onWordsFound(wordList);




        } catch (FileNotFoundException e) {
            e.printStackTrace();
            view.onError(""+e);
        } catch (IOException e) {
            e.printStackTrace();
            view.onError(""+e);
        }

    }

    @Override
    public void getScrabbleHighestScoringWords(ArrayList<String> words) {

        String HighestScoringWord = "";
        int score = 0;
        int highestScrore = 0;

        List<String> al = new ArrayList<>(words);
        Set<String> hs = new HashSet<>();

        hs.addAll(al);
        al.clear();
        al.addAll(hs);

        HashMap<String, Integer> scoringLetterValues = new HashMap<String, Integer>();

        scoringLetterValues.put("A",1);
        scoringLetterValues.put("B",3);
        scoringLetterValues.put("C",3);
        scoringLetterValues.put("D",2);
        scoringLetterValues.put("E",1);
        scoringLetterValues.put("F",4);
        scoringLetterValues.put("G",2);
        scoringLetterValues.put("H",4);
        scoringLetterValues.put("I",1);
        scoringLetterValues.put("J",8);
        scoringLetterValues.put("K",5);
        scoringLetterValues.put("L",1);
        scoringLetterValues.put("M",3);
        scoringLetterValues.put("N",1);
        scoringLetterValues.put("O",1);
        scoringLetterValues.put("P",3);
        scoringLetterValues.put("Q",10);
        scoringLetterValues.put("R",1);
        scoringLetterValues.put("S",1);
        scoringLetterValues.put("T",1);
        scoringLetterValues.put("U",1);
        scoringLetterValues.put("V",4);
        scoringLetterValues.put("W",4);
        scoringLetterValues.put("X",8);
        scoringLetterValues.put("Y",4);
        scoringLetterValues.put("Z",10);

        HighestScoringWord = al.get(0);

        for(int i = 0; i <= HighestScoringWord.length() - 1 ;i++)
        {
            String alphabet =  new String(String.valueOf(HighestScoringWord.toUpperCase().charAt(i)).trim());

                try{
                    highestScrore = highestScrore + scoringLetterValues.get(alphabet);
                }
                catch (Exception e)
                {
                    Log.e("TAG","error on "+ alphabet +" - " +e.toString());
                }
        }

       for(String word : al)
       {

            for(int i = 0; i <= word.length() - 1 ;i++)
            {
                String alphabet =  new String(String.valueOf(word.toUpperCase().charAt(i)).trim());
                if(!Character.isDigit(alphabet.charAt(0)))
                {
                    score = score + scoringLetterValues.get(alphabet);
                }

            }

            if(score > highestScrore)
            {
                highestScrore =  new Integer(score);
                HighestScoringWord = new String(word);
            }



        }

        view.onScrabbleHighestScoringWords(HighestScoringWord,highestScrore);

        Log.v("TAG","Scrabble Highest Scoring Word : " + HighestScoringWord +"  number occurred : " + highestScrore);

    }


}
