package com.s1451552.grabble;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Word Bag fragment for BackpackActivity
 * Will store created words and their point values
 * Created by Vytautas on 08/11/2016.
 */

public class WordBagFragment extends Fragment {

    private ArrayList<String> mWords;

    public WordBagFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WordBagFragment newInstance(ArrayList<String> words) {
        WordBagFragment fragment = new WordBagFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("words", words);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wordbag, container, false);
        mWords = getArguments().getStringArrayList("words");

        //TODO!!!! copy from letterbag fragment
        return rootView;
    }
}
