package xyz.miroslaw.gamification_android.deckManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.menu.MenuActivity;

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

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        } else {
            getFragmentManager().popBackStack();
        }

    }

}
