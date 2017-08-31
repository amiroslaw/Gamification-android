package xyz.miroslaw.gamification_android.drawCard;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import xyz.miroslaw.gamification_android.R;

public class DrawCardActivity extends AppCompatActivity {
    private final String TAG = "myDebug " + getClass().getSimpleName();
    private static final String ACTIVE_FLAG = "active fragment flag", DECK_LIST_FRAGMENT = "deck list fragment", DRAW_CARD = "drawCard";

    private FragmentManager manager;
    FragmentTransaction transaction;
    DeckListFragment deckListFragment;
    DrawCardFragment drawCardFragment;
    boolean isDrawCardActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_card);
        manager = getSupportFragmentManager();
        deckListFragment = (DeckListFragment) manager.findFragmentById(R.id.fragment_deckManager);
        if (savedInstanceState != null)
            isDrawCardActive = savedInstanceState.getBoolean(ACTIVE_FLAG);
        Log.d(TAG, "onCreate: active " +isDrawCardActive);
        if (!isDrawCardActive) {
            initIfListFragmentIsNull();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.fl_drawCard, deckListFragment, DECK_LIST_FRAGMENT);
            transaction.commit();
        } else {
            drawCardFragment = (DrawCardFragment) manager.getFragment(savedInstanceState, DRAW_CARD);
            transaction = manager.beginTransaction();
            transaction.replace(R.id.fl_drawCard, drawCardFragment, DRAW_CARD);
            transaction.commit();
        }

    }

    private void initIfListFragmentIsNull() {
        if (deckListFragment == null) {
            deckListFragment = DeckListFragment.newInstance();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        drawCardFragment = (DrawCardFragment) manager.findFragmentByTag(DRAW_CARD);
        if (drawCardFragment != null && drawCardFragment.isVisible()) {
            isDrawCardActive = true;
            Log.d(TAG, "onSaveInstanceState: active " + isDrawCardActive);
            outState.putBoolean(ACTIVE_FLAG, isDrawCardActive);
            manager.putFragment(outState, DRAW_CARD, drawCardFragment);
        }
    }
}
