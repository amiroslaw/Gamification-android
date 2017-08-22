package xyz.miroslaw.gamification_android.drawCard;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import xyz.miroslaw.gamification_android.R;

public class DrawCardActivity extends AppCompatActivity {
    private FragmentManager manager;
    DrawCardFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_card);
        manager = getSupportFragmentManager();
        fragment = (DrawCardFragment) manager.findFragmentById(R.id.fragment_drawCard);
        if (fragment == null) {
            fragment = DrawCardFragment.newInstance();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_drawCard, fragment, fragment.getTag());
        transaction.commit();

    }
}
