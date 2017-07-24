package xyz.miroslaw.gamification_android.createDeck;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import xyz.miroslaw.gamification_android.R;

public class CreateDeckActivity extends AppCompatActivity {
    private final String DEBUGTAG = "myDebug "+getClass().getSimpleName();

    private FragmentManager manager;
    CreateDeckNavigationFragment navigationFragment;
    CreateDeckFormFragment formFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deck);

        manager = this.getSupportFragmentManager();
        navigationFragment = (CreateDeckNavigationFragment) manager.findFragmentById(R.id.fragment_createDeckNavigation);
        formFragment =  (CreateDeckFormFragment) manager.findFragmentById(R.id.fragment_createDeckForm);
        getFragmentsInstances();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_createDeckNavigation, navigationFragment, navigationFragment.getTag());
        transaction.commit();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_createDeckForm, formFragment, formFragment.getTag());
        transaction.commit();


    }

    private void getFragmentsInstances() {
        if (formFragment == null) {
            formFragment = CreateDeckFormFragment.newInstance();
        }
        if (navigationFragment == null) {
            navigationFragment = CreateDeckNavigationFragment.newInstance();
        }
    }


}
