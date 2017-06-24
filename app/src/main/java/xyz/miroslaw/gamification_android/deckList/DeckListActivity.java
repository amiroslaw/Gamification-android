package xyz.miroslaw.gamification_android.deckList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.miroslaw.gamification_android.R;

public class DeckListActivity extends AppCompatActivity {
    @BindView(R.id.rv_deckList)
    RecyclerView recyclerView;
//    private List<Deck> deckList = new ArrayList<>();
    private List<Deck> deckList = Arrays.asList(
            new Deck(1,"fjdi"),
            new Deck(2,"fjdi"),
            new Deck(3,"fjdi"),
        new Deck(1,"fjdi"),
        new Deck(2,"fjdi"),new Deck(1,"fjdi"),
        new Deck(2,"fjdi"),
        new Deck(3,"fjdi"),new Deck(1,"fjdi"),
        new Deck(2,"fjdi"),
        new Deck(3,"fjdi"),new Deck(1,"fjdi"),
        new Deck(2,"fjdi"),
        new Deck(3,"fjdi"),
        new Deck(3,"fjdi")
        );

    private DeckListAdapter deckListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_list);
        ButterKnife.bind(this);

        deckListAdapter = new DeckListAdapter(deckList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(deckListAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Deck deck = deckList.get(position);
                Toast.makeText(getApplicationContext(), deck.getDeckName() + " is selected!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    }

}
