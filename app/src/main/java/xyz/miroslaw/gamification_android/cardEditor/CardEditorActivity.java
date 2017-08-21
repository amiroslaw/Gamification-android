package xyz.miroslaw.gamification_android.cardEditor;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.viewUtils.Communicator;

public class CardEditorActivity extends AppCompatActivity implements Communicator {
    CardEditorFragment cardEditorFragment = new CardEditorFragment();
    CreateCardFragment createCardFragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_editior);

//      maybe later will be useful
//        createCardFragment.setCommunicator(this);
//        transaction.replace(R.id.fragment_cardEditor, createCardFragment);

        cardEditorFragment.setCommunicator(this);
        transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_cardEditor, cardEditorFragment);
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
            //      maybe later will be useful
//            if (createCardFragment.getArguments() != null) {
//                createCardFragment.getArguments().clear();
//                Log.d("myTag", "changeFragment: args size" + createCardFragment.getArguments().size());
//            }
            createCardFragment.setArguments(bundle);
            transaction.replace(R.id.fragment_cardEditor, createCardFragment);
            transaction.commit();
        }

    }
}
