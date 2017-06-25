package xyz.miroslaw.gamification_android.deckManager;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import xyz.miroslaw.gamification_android.R;

public class DeckManagerActivity extends AppCompatActivity {
    private FragmentManager manager;
    DeckManagerFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_manager);

        manager = this.getSupportFragmentManager();
        fragment = (DeckManagerFragment) manager.findFragmentById(R.id.fragment_deckManager);
        if (fragment == null) {
            fragment = DeckManagerFragment.newInstance();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_deckManager, fragment, fragment.getTag());
        transaction.commit();

    }


}
