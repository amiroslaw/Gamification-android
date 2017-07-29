package xyz.miroslaw.gamification_android.createDeck;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import xyz.miroslaw.gamification_android.R;

public class CreateDeckActivity extends AppCompatActivity {
    private final String DEBUGTAG = "myDebug "+getClass().getSimpleName();

    private FragmentManager manager;
    CreateCardFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deck);

        manager = this.getSupportFragmentManager();
        fragment =  (CreateCardFragment) manager.findFragmentById(R.id.fragment_createCard);
        if (fragment == null) {
            fragment = CreateCardFragment.newInstance();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_createCard, fragment, fragment.getTag());
        transaction.commit();

    }

}
