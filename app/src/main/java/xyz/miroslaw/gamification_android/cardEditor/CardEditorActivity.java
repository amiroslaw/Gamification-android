package xyz.miroslaw.gamification_android.cardEditor;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import xyz.miroslaw.gamification_android.R;

public class CardEditorActivity extends AppCompatActivity {
    private FragmentManager manager;
    CardEditorFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_editior);

        manager = this.getSupportFragmentManager();
        fragment = (CardEditorFragment) manager.findFragmentById(R.id.fragment_cardEditor);
        if (fragment == null) {
            fragment = CardEditorFragment.newInstance();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_cardEditor, fragment, fragment.getTag());
        transaction.commit();
    }
}
