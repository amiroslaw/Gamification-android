package xyz.miroslaw.gamification_android.cardEditor;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.viewUtils.Communicator;

public class CardEditorActivity extends AppCompatActivity implements Communicator {
    private FragmentManager manager;
    CardEditorFragment cardEditorFragment = new CardEditorFragment();
    CreateCardFragment createCardFragment = new CreateCardFragment();
    boolean isActiveListView = true;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_editior);

        cardEditorFragment.setCommunicator(this);
        createCardFragment.setCommunicator(this);
//        manager = this.getSupportFragmentManager();
//        fragment = (CardEditorFragment) manager.findFragmentById(R.id.fragment_cardEditor);
//        if (fragment == null) {
//            fragment = CardEditorFragment.newInstance();
//        }
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.fragment_cardEditor, fragment, fragment.getTag());
//        transaction.commit();
        transaction = this.getSupportFragmentManager().beginTransaction();
        if (savedInstanceState == null) {
            transaction.replace(R.id.fragment_cardEditor, cardEditorFragment);
        } else {
            transaction.replace(R.id.fragment_cardEditor, createCardFragment);
        }
        transaction.commit();
    }

    @Override
    public void changeFragment(Bundle bundle) {
        transaction = this.getSupportFragmentManager().beginTransaction();
        if (bundle == null) {
            cardEditorFragment = CardEditorFragment.newInstance();
            cardEditorFragment.setCommunicator(this);
            transaction.replace(R.id.fragment_cardEditor, cardEditorFragment);
            transaction.commit();
        } else {
            createCardFragment = CreateCardFragment.newInstance();
            createCardFragment.setCommunicator(this);
//            if (createCardFragment.getArguments() != null) {
//                createCardFragment.getArguments().clear();

//                Log.d("myTag", "changeFragment: args size" + createCardFragment.getArguments().size());
//            }
            createCardFragment.setArguments(bundle);
            transaction.replace(R.id.fragment_cardEditor, createCardFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}
