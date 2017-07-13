package xyz.miroslaw.gamification_android.deckList;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import xyz.miroslaw.gamification_android.R;

public class DeckListActivity extends AppCompatActivity {

    private FragmentManager manager;
    DeckListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_list);

        manager = this.getSupportFragmentManager();
        fragment = (DeckListFragment) manager.findFragmentById(R.id.fragment_deckList);
        if (fragment == null) {
            fragment = DeckListFragment.newInstance();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_deckList, fragment, fragment.getTag());
        transaction.commit();


    }

}
