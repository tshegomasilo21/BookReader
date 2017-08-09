package com.touchsides.bookreaderapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tshego on 2017/08/08.
 */

public class FrequentWordContract {

    public interface Presenter {

        void getMostFrequentWord(ArrayList<String> words);
        void getMostFrequent7CharWord(ArrayList<String> words);
        void getWords();
        void getScrabbleHighestScoringWords(ArrayList<String> words);

    }
    public interface View {
        void onMostFrequentWord(String word, int numOccurred);
        void onMostFrequent7CharWord(String word, int numOccured);
        void onWordsFound(ArrayList<String> words);
        void onScrabbleHighestScoringWords(String word, int numScored);
        void onError(String message);
    }
}
